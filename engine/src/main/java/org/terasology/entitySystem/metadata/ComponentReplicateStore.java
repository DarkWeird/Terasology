// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.entitySystem.metadata;

import org.terasology.entitySystem.metadata.extandable.ExtendableClassMetadata;
import org.terasology.entitySystem.metadata.extandable.ExtendableFieldMetadata;
import org.terasology.entitySystem.metadata.extandable.store.ClassMetadataStore;
import org.terasology.network.Replicate;

public class ComponentReplicateStore implements ClassMetadataStore {

    private boolean replicated;
    private boolean replicatedFromOwner;

    @Override
    public void processClass(ExtendableClassMetadata<?, ?> classMetadata) {

        replicated = classMetadata.getType().getAnnotation(Replicate.class) != null;
        for (ExtendableFieldMetadata<?, ?> field : classMetadata.getFields()) {
            ReplicatedFieldStore replicatedFieldStore = field.getStore(ReplicatedFieldStore.class);
            if (replicatedFieldStore.isReplicated()) {
                replicated = true;
                if (replicatedFieldStore.getReplicationInfo().value().isReplicateFromOwner()) {
                    replicatedFromOwner = true;
                }
            }
        }
    }

    /**
     * @return Whether this component replicates any fields from owner to server
     */
    public boolean isReplicatedFromOwner() {
        return replicatedFromOwner;
    }

    /**
     * @return Whether this component needs to be replicated
     */
    public boolean isReplicated() {
        return replicated;
    }

}
