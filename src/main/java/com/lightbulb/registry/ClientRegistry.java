package com.lightbulb.registry;

import com.lightbulb.registry.data.Client;
import com.lightbulb.registry.data.Instance;

public interface ClientRegistry {

    Client getClient(String name);

    void register(Instance instance);

    void clearRegistry();
}
