// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.persistence.serializers;

import org.terasology.entitySystem.metadata.extandable.ExtendableFieldMetadata;
import org.terasology.reflection.metadata.ClassMetadata;
import org.terasology.reflection.metadata.FieldMetadata;

/**
 * Interface for providing serializers with a method to check whether a given field should be serialized.
 */
public interface FieldSerializeCheck<M extends ExtendableFieldMetadata<?, T>, T> extends DeserializeFieldCheck {

    /**
     * @param field The field to check
     * @param object The object it belongs to
     * @return Whether the field should be serialized
     */
    boolean shouldSerializeField(M field, T object);

    /**
     * @param field The field to check
     * @param object The object it belongs to
     * @param componentInitial In a network situation, whether the component is newly added or not
     * @return Whether the field should be serialized
     */
    boolean shouldSerializeField(M field, T object, boolean componentInitial);

    /**
     * Null implementation, returns true for all fields
     */
    final class NullCheck<T> implements FieldSerializeCheck<ExtendableFieldMetadata<?, T>, T> {

        private static final FieldSerializeCheck.NullCheck INSTANCE = new FieldSerializeCheck.NullCheck();

        private NullCheck() {
        }

        @SuppressWarnings("unchecked")
        public static <T> FieldSerializeCheck.NullCheck<T> newInstance() {
            return INSTANCE;
        }

        @Override
        public boolean shouldSerializeField(ExtendableFieldMetadata<?, T> field, T object) {
            return true;
        }

        @Override
        public boolean shouldSerializeField(ExtendableFieldMetadata<?, T> field, T object, boolean componentInitial) {
            return true;
        }

        @Override
        public boolean shouldDeserialize(ClassMetadata<?, ?> classMetadata, FieldMetadata<?, ?> fieldMetadata) {
            return true;
        }
    }
}
