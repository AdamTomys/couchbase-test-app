package org.cachetester.json.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cachetester.couchbase.service.CacheInterface;
import org.cachetester.couchbase.service.CacheService;
import org.cachetester.json.dto.SampleFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.Callable;

public class CacheLoadTask implements Callable<Long> {

    private final CacheInterface cacheInterface;
    private final String jsonFilePath;
    private final int testingPeriod;
    private final int numberOfCacheRetrievals;

    public CacheLoadTask(String jsonFilePath, int testingPeriod, int numberOfCacheRetrievals) {
        this.jsonFilePath = jsonFilePath;
        this.testingPeriod = testingPeriod;
        this.numberOfCacheRetrievals = numberOfCacheRetrievals;
        this.cacheInterface = new CacheService();
    }

    @Override
    public Long call() throws Exception {
        long currentThreadExecutions = 0;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < testingPeriod) {
                try (BufferedReader fileInStream = new BufferedReader(new FileReader(jsonFilePath))) {
                    ObjectMapper om = new ObjectMapper();
                    SampleFile sampleFile = om.readValue(fileInStream, SampleFile.class);
                    cacheInterface.uploadJson(sampleFile);
                    for (int k = 0; k < numberOfCacheRetrievals; k++) {
                        cacheInterface.extractJson(sampleFile.getId(), sampleFile.getCollectionName());
                    }
                    currentThreadExecutions++;
                }
        }
        return currentThreadExecutions;
    }
}
