package com.mobility.service;

import com.mobility.proto.JobServiceGrpc;
import com.mobility.proto.ScrapeRequest;
import com.mobility.proto.ScrapeResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * --- LEARNING NOTE: gRPC Stubs ---
 * 1. ManagedChannel: This is the underlying connection to the Python server.
 * It's expensive to create, so we reuse it.
 * 2. BlockingStub: This allows us to call the remote method as if it were a
 * regular Java method. "Blocking" means it waits for the response
 * (Synchronous).
 * 3. FutureStub/Stub: These are for Asynchronous calls (Non-blocking).
 * ---
 */
@Service
public class JobServiceClient {

    private final String host;
    private final int port;
    private ManagedChannel channel;
    private JobServiceGrpc.JobServiceBlockingStub blockingStub;

    public JobServiceClient(@Value("${ai.engine.host}") String host,
            @Value("${ai.engine.port}") int port) {
        this.host = host;
        this.port = port;
    }

    @PostConstruct
    public void init() {
        // Create the connection (Channel)
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext() // No SSL for local development
                .build();

        // Create the "Stubs" we'll use to make calls
        this.blockingStub = JobServiceGrpc.newBlockingStub(channel);
        System.out.println("gRPC Client initialized on " + host + ":" + port);
    }

    public ScrapeResponse fetchJobs(String keyword, String country) {
        ScrapeRequest request = ScrapeRequest.newBuilder()
                .setKeyword(keyword)
                .setCountry(country)
                .build();

        System.out.println("Calling Python AI Engine for: " + keyword + " in " + country);
        return blockingStub.scrapeAndAnalyze(request);
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.shutdown();
        }
    }
}
