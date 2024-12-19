package org.cachetester.couchbase.cluster;

import com.couchbase.client.java.*;
import org.apache.commons.math3.util.Pair;
import org.cachetester.utils.PropertiesConstants;
import org.cachetester.utils.PropertiesUtils;

public class ClusterConnection {

    private static ClusterConnection clusterConnection = null;
    private final ThreadLocal<Cluster> cluster = new ThreadLocal<>();
    private final String username;
    private final String password;

    private ClusterConnection(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static void loginWith(String username, String password) {
        clusterConnection = new ClusterConnection(username, password);
    }

    public static ClusterConnection getInstance() {
        return clusterConnection;
    }

    public Cluster getCluster() {
        if (cluster.get() == null) {
            cluster.set(Cluster.connect(
                    PropertiesUtils.getProperty(PropertiesConstants.COUCHBASE_SERVER_ADDRESS),
                    ClusterOptions.clusterOptions(username, password).environment(env -> {})
            ));
        }
        return cluster.get();
    }

    public Pair<String, String> getCredentials() {
        return new Pair<>(username, password);
    }
}
