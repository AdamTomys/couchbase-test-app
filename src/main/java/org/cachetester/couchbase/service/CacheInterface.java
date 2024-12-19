package org.cachetester.couchbase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.cachetester.json.dto.Cacheable;

public interface CacheInterface {
    void uploadJson(Cacheable object) throws JsonProcessingException;
    Cacheable extractJson(String id, String collectionName);
}
