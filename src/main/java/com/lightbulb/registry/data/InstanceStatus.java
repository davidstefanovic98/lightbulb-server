package com.lightbulb.registry.data;

import com.lightbulb.core.logging.Logger;
import com.lightbulb.core.logging.LoggerFactory;

public enum InstanceStatus {
    UP,
    DOWN,
    STARTING,
    OUT_OF_SERVICE,
    UNKNOWN;

    private static final Logger logger = LoggerFactory.getLogger(InstanceStatus.class);

    public static InstanceStatus toEnum(String status) {
        if (status != null) {
            logger.info("Instance status is {}", status);
            return InstanceStatus.valueOf(status.toUpperCase());
        }
        logger.warn("Instance status is null, defaulting to UNKNOWN");
        return UNKNOWN;
    }
}
