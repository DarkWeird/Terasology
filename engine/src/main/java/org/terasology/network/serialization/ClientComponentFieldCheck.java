/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.network.serialization;

import org.terasology.entitySystem.Component;
import org.terasology.entitySystem.metadata.ReplicatedFieldStore;
import org.terasology.entitySystem.metadata.extandable.ExtendableFieldMetadata;
import org.terasology.persistence.serializers.FieldSerializeCheck;
import org.terasology.reflection.metadata.ClassMetadata;
import org.terasology.reflection.metadata.FieldMetadata;

/**
 * Determines which fields should be serialized and deserialized by the client.
 */
public class ClientComponentFieldCheck implements FieldSerializeCheck<ExtendableFieldMetadata<?, Component>,
        Component> {

    @Override
    public boolean shouldSerializeField(ExtendableFieldMetadata<?, Component> field, Component object) {
        return shouldSerializeField(field, object, false);
    }

    @Override
    public boolean shouldSerializeField(ExtendableFieldMetadata<?, Component> field, Component component,
                                        boolean componentInitial) {
        // Clients only send fields that are replicated from the owner
        ReplicatedFieldStore replicatedFieldStore = field.getStore(ReplicatedFieldStore.class);
        return replicatedFieldStore.isReplicated() && replicatedFieldStore.getReplicationInfo().value().isReplicateFromOwner();
    }

    @Override
    public boolean shouldDeserialize(ClassMetadata<?, ?> classMetadata, FieldMetadata<?, ?> fieldMetadata) {
        // Clients should use all replicated fields
        return true;
    }
}
