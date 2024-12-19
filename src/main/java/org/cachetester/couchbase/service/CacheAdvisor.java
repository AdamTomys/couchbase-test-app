package org.cachetester.couchbase.service;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.Scope;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cachetester.couchbase.cluster.ClusterConnection;
import org.cachetester.couchbase.enums.CacheCollectionEnum;

import java.time.Duration;

public class CacheAdvisor {

    private final Cluster cluster;
    protected final ObjectMapper objectMapper;
    private Bucket bucket;
    private Scope scope;
    private Collection collection;

    public CacheAdvisor() {
        this.cluster = ClusterConnection.getInstance().getCluster();
        objectMapper = new ObjectMapper();
    }

    protected Collection getCollection(CacheCollectionEnum collectionEnum) {
        if (collection == null || !collection.name().equals(collectionEnum.name())) {
            getBucket(collectionEnum.getBucketName());
            getScope(collectionEnum.getScopeName());
            collection = scope.collection(collectionEnum.name());
        }
        return collection;
    }

    private void getScope(String scopeName) {
        if (scope == null || !scope.name().equals(scopeName)) {
            scope = bucket.scope(scopeName);
        }
    }

    private void getBucket(String bucketName) {
        if (bucket == null || !bucket.name().equals(bucketName)) {
            bucket = cluster.bucket(bucketName);
            bucket.waitUntilReady(Duration.ofSeconds(10));
        }
    }
}
