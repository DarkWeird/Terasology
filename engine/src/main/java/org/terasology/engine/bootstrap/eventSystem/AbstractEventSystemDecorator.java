// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.engine.bootstrap.eventSystem;

import org.terasology.entitySystem.event.internal.EventSystem;

public abstract class AbstractEventSystemDecorator implements EventSystem {
    private Thread mainThread;

    public AbstractEventSystemDecorator() {
        this.mainThread = Thread.currentThread();
    }

    public boolean currentThreadIsMain() {
        return mainThread == Thread.currentThread();
    }
}
