import grpc
from concurrent import futures
import job_service_pb2
import job_service_pb2_grpc
import asyncio
from scraper import scrape_jobs
from ollama_client import analyze_job_sponsorship
import json

# --- LEARNING NOTE ---
# In Python, 'async' and 'await' are used for asynchronous programming.
# This is similar to Java's CompletableFuture or Virtual Threads.
# It allows the program to handle other tasks while waiting for I/O (like web scraping).
# ---

class JobServiceServicer(job_service_pb2_grpc.JobServiceServicer):
    """
    This class implements the JobService gRPC interface defined in our proto file.
    """

    async def ScrapeAndAnalyze(self, request, context):
        print(f"Received request for country: {request.country}, keyword: {request.keyword}")
        
        # 1. Scrape jobs using our new logic
        scraped_data = await scrape_jobs(request.keyword, request.country)
        
        proto_jobs = []
        for item in scraped_data:
            # 2. Analyze each job with AI
            # We pass the summary or title to the AI to check sponsorship
            # (Learning Tip: In a real app, you'd process these in parallel)
            ai_result_str = await analyze_job_sponsorship(item['summary'])
            
            # Use a safe try-except for JSON parsing
            try:
                ai_data = json.loads(ai_result_str)
                sponsorship = ai_data.get("sponsorship", "Likely")
                reason = ai_data.get("reason", item['summary'])
            except:
                sponsorship = "Unknown"
                reason = item['summary']

            # 3. Map to Proto objects
            proto_jobs.append(job_service_pb2.Job(
                title=item['title'],
                company=item['company'],
                location=item['location'],
                sponsorship_status=sponsorship,
                summary=reason,
                url=item['url']
            ))
        
        return job_service_pb2.ScrapeResponse(jobs=proto_jobs)

async def serve():
    """Starts the gRPC server."""
    server = grpc.aio.server()
    job_service_pb2_grpc.add_JobServiceServicer_to_server(JobServiceServicer(), server)
    listen_addr = "[::]:50051"
    server.add_insecure_port(listen_addr)
    print(f"Starting Python gRPC server on {listen_addr}...")
    await server.start()
    await server.wait_for_termination()

if __name__ == "__main__":
    # We use asyncio.run to start our asynchronous server
    asyncio.run(serve())
