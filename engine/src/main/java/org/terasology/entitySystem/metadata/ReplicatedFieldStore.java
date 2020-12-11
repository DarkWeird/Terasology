// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.entitySystem.metadata;

import org.terasology.entitySystem.metadata.extandable.ExtendableClassMetadata;
import org.terasology.entitySystem.metadata.extandable.ExtendableFieldMetadata;
import org.terasology.entitySystem.metadata.extandable.store.FieldMetadataStore;
import org.terasology.network.NoReplicate;
import org.terasology.network.Replicate;

import java.lang.reflect.Field;

/**
 * An extended FieldMetadata that provides information on whether a the field should be replicated, and under what
 * conditions
 */
public class ReplicatedFieldStore<T> implements FieldMetadataStore<T> {

    private boolean replicated;
    private Replicate replicationInfo;

    @Override
    public void processField(ExtendableClassMetadata<T, ?> owner, ExtendableFieldMetadata<T,?> fieldMetadata) {
        ReplicatedByDefaultClassStore replicatedByDefaultClassStore =
                owner.getStore(ReplicatedByDefaultClassStore.class);
        replicated = replicatedByDefaultClassStore.getReplicatedByDefault();
        if (fieldMetadata.getField().getAnnotation(NoReplicate.class) != null) {
            replicated = false;
        }
        if (fieldMetadata.getField().getAnnotation(Replicate.class) != null) {
            replicated = true;
        }
        this.replicationInfo = fieldMetadata.getField().getAnnotation(Replicate.class);
    }

    /**
     * @return Whether this field should be replicated on the network
     */
    public boolean isReplicated() {
        return replicated;
    }

    /**
     * @return The replication information for this field, or null if it isn't marked with the Replicate annotation
     */
    public Replicate getReplicationInfo() {
        return replicationInfo;
    }

}
