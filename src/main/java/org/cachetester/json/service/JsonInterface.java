package org.cachetester.json.service;

import org.cachetester.exceptions.CacheTestingException;

public interface JsonInterface {
    void testCachingSpeed() throws CacheTestingException;
}
