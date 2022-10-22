package com.lightbulb.registry.data;

import com.lightbulb.util.Utils;

public class Instance {
    private final String id;
    private String name;
    private String host;
    private Integer port;
    private InstanceStatus instanceStatus;

    public Instance(String name, String host, int port) {
        this.id = Utils.sha1Hash(String.format("%s:%s", host, port));
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InstanceStatus getInstanceStatus() {
        return instanceStatus;
    }

    public void setInstanceStatus(InstanceStatus instanceStatus) {
        this.instanceStatus = instanceStatus;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
