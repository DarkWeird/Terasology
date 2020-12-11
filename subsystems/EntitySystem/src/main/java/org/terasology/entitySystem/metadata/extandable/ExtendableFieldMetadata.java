// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.entitySystem.metadata.extandable;

import org.terasology.entitySystem.metadata.extandable.store.FieldMetadataStore;
import org.terasology.reflection.copy.CopyStrategyLibrary;
import org.terasology.reflection.metadata.ClassMetadata;
import org.terasology.reflection.metadata.FieldMetadata;
import org.terasology.reflection.reflect.InaccessibleFieldException;
import org.terasology.reflection.reflect.ObjectConstructor;
import org.terasology.reflection.reflect.ReflectFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtendableFieldMetadata<T, U> extends FieldMetadata<T, U> {

    private final Map<Class<?>, FieldMetadataStore<T>> fieldMetadataStore;

    /**
     * @param owner The ClassMetadata that owns this field
     * @param field The field this metadata is for
     * @param copyStrategyLibrary Something to provide a CopyStrategy appropriate for the type of the field
     * @param factory The reflection provider
     */
    public ExtendableFieldMetadata(ExtendableClassMetadata<T, ?> owner,
                                   Field field,
                                   CopyStrategyLibrary copyStrategyLibrary,
                                   ReflectFactory factory,
                                   List<Class<? extends FieldMetadataStore<T>>> fieldMetadataStore) throws InaccessibleFieldException {
        super(owner, field, copyStrategyLibrary, factory);
        this.fieldMetadataStore =
                fieldMetadataStore.stream()
                        .map(type1 -> {
                            try {
                                return factory.createConstructor(type1);
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .map(ObjectConstructor::construct)
                        .peek(m -> m.processField(owner,field))
                        .collect(Collectors.toMap(Object::getClass, ms -> ms));
    }

    public <R extends FieldMetadataStore<T>> R getStore(Class<R> storeType) {
        return (R) fieldMetadataStore.get(storeType);
    }
}
