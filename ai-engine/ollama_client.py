import ollama
import asyncio

# --- LEARNING NOTE: AI Analysis with Ollama ---
# Ollama lets you run open-source models (like Llama 3) on your own machine.
# No API keys, no costs, and private.
# ---

async def analyze_job_sponsorship(job_description):
    """
    Uses a local LLM to decide if a job likely offers visa sponsorship.
    """
    prompt = f"""
    Analyze the following job description and determine if they offer visa sponsorship.
    Return only a JSON object like: {{"sponsorship": "Confirmed|Likely|None", "reason": "short explanation"}}
    
    Job Description:
    {job_description}
    """
    
    try:
        # This is the call to the local model
        # Note: You need to have Ollama installed and 'llama3' pulled (ollama pull llama3)
        response = await asyncio.to_thread(ollama.chat, model='llama3', messages=[
            {
                'role': 'user',
                'content': prompt,
            },
        ])
        
        # In a real setup, we would parse the JSON from 'response['message']['content']'
        print(f"AI Analysis Result: {response['message']['content']}")
        return response['message']['content']

    except Exception as e:
        print(f"Ollama not found or error occurred: {e}")
        return '{"sponsorship": "Unknown", "reason": "AI analysis offline"}'

if __name__ == "__main__":
    # Test with a snippet
    test_desc = "We are a Berlin-based company. We offer full relocation and visa sponsorship for international candidates."
    asyncio.run(analyze_job_sponsorship(test_desc))
