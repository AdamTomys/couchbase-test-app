package org.cachetester.couchbase.service;

import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.json.JsonObject;
import org.apache.commons.math3.util.Pair;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.cachetester.couchbase.cluster.ClusterConnection;
import org.cachetester.pptx.PptxGenerator;
import org.cachetester.utils.PropertiesConstants;
import org.cachetester.utils.PropertiesUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MetricsService extends CacheAdvisor implements MetricsInterface {

    private final List<String> metricKeys = List.of("hit_ratio", "ep_cache_miss_rate", "ep_data_read_failed",
            "ep_item_commit_failed", "avg_disk_commit_time", "cpu_user_rate", "cpu_cores_available");
    private final PptxGenerator pptxGenerator;
    private final Map<String, Double> results = new HashMap<>();

    public MetricsService() {
        this.pptxGenerator = new PptxGenerator();
    }

    public void generateMetricsPresentation(long totalExecutions) throws Exception {
        String metricsUrl = PropertiesUtils.getProperty(PropertiesConstants.COUCHBASE_METRICS_URL);
        Pair<String, String> credentials = ClusterConnection.getInstance().getCredentials();
        String auth = credentials.getFirst() + ":" + credentials.getSecond();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(metricsUrl);
            request.setHeader("Authorization", "Basic " + encodedAuth);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                JsonObject root = JsonObject.fromJson(jsonResponse);
                JsonObject opNode = JsonObject.fromJson(root.get("op").toString());
                JsonObject samplesNode = JsonObject.fromJson(opNode.get("samples").toString());

                metricKeys.forEach(key -> {
                    JsonArray values = samplesNode.getArray(key);
                    Double avg = values.toList().stream().map(String::valueOf).collect(Collectors.averagingDouble(Double::valueOf));
                    results.put(key, avg);
                });
            }
        }
        pptxGenerator.generatePresentation(totalExecutions, results);
    }
}