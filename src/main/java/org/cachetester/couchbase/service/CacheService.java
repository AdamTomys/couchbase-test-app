package org.cachetester.couchbase.service;

import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.cachetester.couchbase.enums.CacheCollectionEnum;
import org.cachetester.json.dto.Cacheable;
import org.cachetester.json.dto.SampleFile;

public class CacheService extends CacheAdvisor implements CacheInterface {

    public CacheService() {
        super();
    }

    @Override
    public void uploadJson(Cacheable sampleFile) throws JsonProcessingException {
        Collection collection = getCollection(CacheCollectionEnum.valueOf(sampleFile.getCollectionName()));
        JsonObject jsonObject = JsonObject.fromJson(objectMapper.writeValueAsString(sampleFile));
        collection.upsert(sampleFile.getId(), jsonObject);
    }

    @Override
    public SampleFile extractJson(String id, String sector) {
        Collection collection = getCollection(CacheCollectionEnum.valueOf(sector));
        return collection.get(id).contentAs(SampleFile.class);
    }
}
