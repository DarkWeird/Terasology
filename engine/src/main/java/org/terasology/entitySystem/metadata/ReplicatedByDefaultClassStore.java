// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.entitySystem.metadata;

import org.terasology.entitySystem.metadata.extandable.ExtendableClassMetadata;
import org.terasology.entitySystem.metadata.extandable.store.ClassMetadataStore;

public class ReplicatedByDefaultClassStore implements ClassMetadataStore {
    @Override
    public void processClass(ExtendableClassMetadata<?, ?> classMetadata) {
        // No-OP;
    }

    public boolean getReplicatedByDefault() {
        return true;
    }
}
