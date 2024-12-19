package org.cachetester.json.dto;

import java.io.Serializable;

public interface Cacheable extends Serializable {
    String getId();
    String getCollectionName();
}
