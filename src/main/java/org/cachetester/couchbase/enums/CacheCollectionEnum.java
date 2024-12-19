package org.cachetester.couchbase.enums;

public enum CacheCollectionEnum {
    BBR("cache", "json");

    private final String bucketName;
    private final String scopeName;

    CacheCollectionEnum(String bucketName, String scopeName) {
        this.bucketName = bucketName;
        this.scopeName = scopeName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getScopeName() {
        return scopeName;
    }
}
