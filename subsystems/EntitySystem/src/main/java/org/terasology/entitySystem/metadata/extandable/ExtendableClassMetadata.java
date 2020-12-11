// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.entitySystem.metadata.extandable;

import org.terasology.assets.ResourceUrn;
import org.terasology.entitySystem.metadata.extandable.store.ClassMetadataStore;
import org.terasology.entitySystem.metadata.extandable.store.FieldMetadataStore;
import org.terasology.reflection.copy.CopyStrategyLibrary;
import org.terasology.reflection.metadata.ClassMetadata;
import org.terasology.reflection.reflect.ObjectConstructor;
import org.terasology.reflection.reflect.ReflectFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ExtendableClassMetadata<T, FIELD extends ExtendableFieldMetadata<T, ?>>
        extends ClassMetadata<T, FIELD> {

    private final Map<Class<?>, ? extends ClassMetadataStore> classMetadataStores;
    private final List<Class<? extends FieldMetadataStore<T>>> fieldStoreTypes;

    /**
     * Creates a class metatdata
     *
     * @param uri The uri that identifies this type
     * @param type The type to create the metadata for
     * @param factory A reflection library to provide class construction and field get/set functionality
     * @param copyStrategyLibrary A copy strategy library
     * @param classMetadataStores classes, which should be used for extend information about class.
     * @throws NoSuchMethodException If the class has no default constructor
     */
    public ExtendableClassMetadata(ResourceUrn uri, Class<T> type, ReflectFactory factory,
                                   CopyStrategyLibrary copyStrategyLibrary,
                                   List<Class<ClassMetadataStore>> classMetadataStores,
                                   List<Class<? extends FieldMetadataStore<T>>> fieldStoreTypes) throws NoSuchMethodException {
        super(uri, type, factory, copyStrategyLibrary, f -> false);
        this.classMetadataStores =
                classMetadataStores.stream()
                        .map(type1 -> {
                            try {
                                return factory.createConstructor(type1);
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .map(ObjectConstructor::construct)
                        .peek(m -> m.processClass(this))
                        .collect(Collectors.toMap(Object::getClass, ms -> ms));
        this.fieldStoreTypes = fieldStoreTypes;
    }

    /**
     * Get FieldMetadataTypes for creating extandable field metadata;
     *
     * @return FieldMetadataStore
     */
    protected List<Class<? extends FieldMetadataStore<T>>> getFieldStoreTypes() {
        return fieldStoreTypes;
    }

    /**
     * Get extend metadata about this class;
     *
     * @param storeType store type of metadata
     * @param <R> type of metadata
     * @return instance of storeType with filled info.
     */
    public <R extends ClassMetadataStore> R getStore(Class<R> storeType) {
        return (R) classMetadataStores.get(storeType);
    }
}
