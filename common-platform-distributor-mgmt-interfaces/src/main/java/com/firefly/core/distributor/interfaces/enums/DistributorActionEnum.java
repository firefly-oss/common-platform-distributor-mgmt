package com.firefly.core.distributor.interfaces.enums;

/**
 * Enum representing the possible actions that can be performed on a distributor entity.
 * Used for audit logging purposes.
 */
public enum DistributorActionEnum {
    /**
     * Indicates that a distributor entity was created
     */
    CREATED,
    
    /**
     * Indicates that a distributor entity was updated
     */
    UPDATED,
    
    /**
     * Indicates that a distributor entity was terminated
     */
    TERMINATED
}