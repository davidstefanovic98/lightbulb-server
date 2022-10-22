package com.lightbulb.registry.data;

import java.util.HashSet;
import java.util.Set;

public class Client {
    private String name;
    private Set<Instance> instances = new HashSet<>();

    public Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Instance> getInstances() {
        return instances;
    }

    public void setInstance(Set<Instance> instances) {
        this.instances = instances;
    }

    public void addInstance(Instance instance) {
        this.instances.add(instance);
    }
}
