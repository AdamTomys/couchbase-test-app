package org.cachetester;

import org.cachetester.couchbase.cluster.ClusterConnection;
import org.cachetester.json.service.JsonService;
import org.cachetester.utils.PropertiesUtils;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String... args) {
        List<String> arguments = Arrays.asList(args);
        String username = arguments.get(0);
        String password = arguments.get(1);

        PropertiesUtils.initApplicationProperties();
        ClusterConnection.loginWith(username, password);
        new JsonService().testCachingSpeed();
    }
}