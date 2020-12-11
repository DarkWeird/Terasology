// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.entitySystem.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.entitySystem.metadata.extandable.ExtendableClassMetadata;
import org.terasology.entitySystem.metadata.extandable.store.ClassMetadataStore;
import org.terasology.network.BroadcastEvent;
import org.terasology.network.OwnerEvent;
import org.terasology.network.ServerEvent;

import java.lang.reflect.Modifier;

public class NetworkClassMetadataStore implements ClassMetadataStore {

    public static final Logger logger = LoggerFactory.getLogger(NetworkClassMetadataStore.class);

    private NetworkEventType networkEventType = NetworkEventType.NONE;
    private boolean lagCompensated;
    private boolean skipInstigator;

    @Override
    public void processClass(ExtendableClassMetadata<?,?> classMetadata) {
        Class<?> clz = classMetadata.getType();
        if (clz.getAnnotation(ServerEvent.class) != null) {
            networkEventType = NetworkEventType.SERVER;
            lagCompensated = clz.getAnnotation(ServerEvent.class).lagCompensate();
        } else if (clz.getAnnotation(OwnerEvent.class) != null) {
            networkEventType = NetworkEventType.OWNER;
        } else if (clz.getAnnotation(BroadcastEvent.class) != null) {
            networkEventType = NetworkEventType.BROADCAST;
            skipInstigator = clz.getAnnotation(BroadcastEvent.class).skipInstigator();
        }
        if (networkEventType != NetworkEventType.NONE && !classMetadata.isConstructable() && !Modifier.isAbstract(clz.getModifiers())) {
            logger.error("Event '{}' is a network event but lacks a default constructor - will not be replicated", this);
        }
    }

    /**
     * @return Whether this event is a network event.
     */
    public boolean isNetworkEvent() {
        return networkEventType != NetworkEventType.NONE;
    }

    /**
     * @return The type of network event this event is.
     */
    public NetworkEventType getNetworkEventType() {
        return networkEventType;
    }

    /**
     * @return Whether this event is compensated for lag.
     */
    public boolean isLagCompensated() {
        return lagCompensated;
    }

    /**
     * @return Whether this event should not be replicated to the instigator
     */
    public boolean isSkipInstigator() {
        return skipInstigator;
    }
}
