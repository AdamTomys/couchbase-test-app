package org.cachetester.json.service;

import org.cachetester.couchbase.service.MetricsInterface;
import org.cachetester.couchbase.service.MetricsService;
import org.cachetester.exceptions.CacheTestingException;
import org.cachetester.utils.PropertiesConstants;
import org.cachetester.utils.PropertiesUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class JsonService implements JsonInterface {
    private final MetricsInterface metricsInterface;
    private final ThreadPoolExecutor executorService;
    private long totalExecutions;
    private String jsonFilePath;
    private int testingPeriod;
    private int numberOfCacheRetrievals;

    public JsonService() {
        this.metricsInterface = new MetricsService();
        this.executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                Integer.parseInt(PropertiesUtils.getProperty(PropertiesConstants.THREADS))
        );
    }

    @Override
    public void testCachingSpeed() {
        loadProperties();
        List<Callable<Long>> tasks = prepareTasks();
        executeTasks(tasks);
        prepareExecutionReport();
    }

    private List<Callable<Long>> prepareTasks() {
        List<Callable<Long>> tasks = new ArrayList<>();
        for (int i = 0; i < executorService.getCorePoolSize(); i++) {
            tasks.add(new CacheLoadTask(jsonFilePath, testingPeriod, numberOfCacheRetrievals));
        }
        return tasks;
    }

    private void executeTasks(List<Callable<Long>> tasks) {
        try {
            List<Future<Long>> futureThreadsExecutionCountList = executorService.invokeAll(tasks);
            for (Future<Long> futureThreadExecutionCount : futureThreadsExecutionCountList) {
                updateTotalExecutions(futureThreadExecutionCount.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new CacheTestingException(e.getMessage(), e.getCause(), false, true);
        } finally {
            executorService.shutdown();
        }
    }

    private void prepareExecutionReport() {
        try {
            metricsInterface.generateMetricsPresentation(totalExecutions);
        } catch (Exception e) {
            throw new CacheTestingException(e.getMessage(), e.getCause(), false, true);
        }
    }

    private void loadProperties() {
        jsonFilePath = PropertiesUtils.getProperty(PropertiesConstants.JSON_PATH);
        testingPeriod = Integer.parseInt(PropertiesUtils.getProperty(PropertiesConstants.TESTING_PERIOD_IN_MILLIS));
        numberOfCacheRetrievals = Integer.parseInt(PropertiesUtils.getProperty(PropertiesConstants.NUMBER_OF_CACHE_RETRIEVALS));
    }

    private synchronized void updateTotalExecutions(long value) {
        totalExecutions = totalExecutions + value;
    }

}
