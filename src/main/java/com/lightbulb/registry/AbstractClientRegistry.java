package com.lightbulb.registry;

import com.lightbulb.core.logging.Logger;
import com.lightbulb.core.logging.LoggerFactory;
import com.lightbulb.registry.data.Client;
import com.lightbulb.registry.data.Instance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractClientRegistry implements ClientRegistry {

    private static final Logger logger = LoggerFactory.getLogger(AbstractClientRegistry.class);
    private final Map<String, Client> registry = new ConcurrentHashMap<>();

    @Override
    public Client getClient(String name) {
        return registry.get(name);
    }

    @Override
    public synchronized void register(Instance instance) {
        if (isRegistered(instance.getName())) {
            registry.get(instance.getName()).addInstance(instance);
        } else {
            Client client = new Client(instance.getName());
            client.addInstance(instance);
            registry.put(instance.getName(), client);
            logger.info("Registered instance {}/{}", instance.getName(), instance.getId());
        }
    }

    @Override
    public void clearRegistry() {
        registry.clear();
    }

    private boolean isRegistered(String name) {
        return registry.containsKey(name);
    }
}
