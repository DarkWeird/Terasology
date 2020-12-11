// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.engine.bootstrap.eventSystem;

import org.terasology.assets.ResourceUrn;
import org.terasology.entitySystem.Component;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.Event;
import org.terasology.entitySystem.event.internal.EventReceiver;
import org.terasology.entitySystem.event.internal.EventSystem;
import org.terasology.entitySystem.metadata.EventLibrary;
import org.terasology.entitySystem.metadata.EventMetadata;
import org.terasology.entitySystem.systems.ComponentSystem;
import org.terasology.network.BroadcastEvent;
import org.terasology.network.Client;
import org.terasology.network.NetworkComponent;
import org.terasology.network.NetworkEvent;
import org.terasology.network.NetworkMode;
import org.terasology.network.NetworkSystem;
import org.terasology.network.OwnerEvent;
import org.terasology.network.ServerEvent;
import org.terasology.world.block.BlockComponent;

public class NetworkEventSystemDecorator extends AbstractEventSystemDecorator {
    private final EventSystem eventSystem;
    private final NetworkSystem networkSystem;
    private final EventLibrary eventLibrary;

    public NetworkEventSystemDecorator(EventSystem eventSystem, NetworkSystem networkSystem,
                                       EventLibrary eventLibrary) {
        this.eventSystem = eventSystem;
        this.networkSystem = networkSystem;
        this.eventLibrary = eventLibrary;
    }

    @Override
    public void process() {
        eventSystem.process();
    }

    @Override
    public void registerEvent(ResourceUrn uri, Class<? extends Event> eventType) {
        eventSystem.registerEvent(uri, eventType);
        if (shouldAddToLibrary(eventType)) {
            eventLibrary.register(uri, eventType);
        }
    }

    /**
     * Events are added to the event library if they have a network annotation
     *
     * @param eventType the type of the event to be checked
     * @return Whether the event should be added to the event library
     */
    private boolean shouldAddToLibrary(Class<? extends Event> eventType) {
        return eventType.getAnnotation(ServerEvent.class) != null
                || eventType.getAnnotation(OwnerEvent.class) != null
                || eventType.getAnnotation(BroadcastEvent.class) != null;
    }

    @Override
    public void registerEventHandler(ComponentSystem handler) {
        eventSystem.registerEventHandler(handler);
    }

    @Override
    public void unregisterEventHandler(ComponentSystem handler) {
        eventSystem.unregisterEventHandler(handler);
    }

    @Override
    public <T extends Event> void registerEventReceiver(EventReceiver<T> eventReceiver, Class<T> eventClass, Class<?
            extends Component>... componentTypes) {
        eventSystem.registerEventReceiver(eventReceiver, eventClass, componentTypes);
    }

    @Override
    public <T extends Event> void registerEventReceiver(EventReceiver<T> eventReceiver, Class<T> eventClass,
                                                        int priority, Class<? extends Component>... componentTypes) {
        eventSystem.registerEventReceiver(eventReceiver, eventClass, priority, componentTypes);
    }

    @Override
    public <T extends Event> void unregisterEventReceiver(EventReceiver<T> eventReceiver, Class<T> eventClass, Class<
            ? extends Component>... componentTypes) {
        eventSystem.unregisterEventReceiver(eventReceiver, eventClass, componentTypes);
    }

    @Override
    public void send(EntityRef entity, Event event) {
        if (currentThreadIsMain()) {
            networkReplicate(entity, event);
        }
        eventSystem.send(entity, event);
    }

    @Override
    public void send(EntityRef entity, Event event, Component component) {
        eventSystem.send(entity, event, component);
    }


    private void networkReplicate(EntityRef entity, Event event) {
        EventMetadata metadata = eventLibrary.getMetadata(event);
        if (metadata != null && metadata.isNetworkEvent()) {
            switch (metadata.getNetworkEventType()) {
                case BROADCAST:
                    broadcastEvent(entity, event, metadata);
                    break;
                case OWNER:
                    sendEventToOwner(entity, event);
                    break;
                case SERVER:
                    sendEventToServer(entity, event);
                    break;
                default:
                    break;
            }
        }
    }

    private void sendEventToServer(EntityRef entity, Event event) {
        if (networkSystem.getMode() == NetworkMode.CLIENT) {
            NetworkComponent netComp = entity.getComponent(NetworkComponent.class);
            if (netComp != null) {
                networkSystem.getServer().send(event, entity);
            }
        }
    }

    private void sendEventToOwner(EntityRef entity, Event event) {
        if (networkSystem.getMode().isServer()) {
            NetworkComponent netComp = entity.getComponent(NetworkComponent.class);
            if (netComp != null) {
                Client client = networkSystem.getOwner(entity);
                if (client != null) {
                    client.send(event, entity);
                }
            }
        }
    }

    private void broadcastEvent(EntityRef entity, Event event, EventMetadata metadata) {
        if (networkSystem.getMode().isServer()) {
            NetworkComponent netComp = entity.getComponent(NetworkComponent.class);
            BlockComponent blockComp = entity.getComponent(BlockComponent.class);
            if (netComp != null || blockComp != null) {
                Client instigatorClient = null;
                if (metadata.isSkipInstigator() && event instanceof NetworkEvent) {
                    instigatorClient = networkSystem.getOwner(((NetworkEvent) event).getInstigator());
                }
                for (Client client : networkSystem.getPlayers()) {
                    if (!client.equals(instigatorClient)) {
                        client.send(event, entity);
                    }
                }
            }
        }
    }
}
