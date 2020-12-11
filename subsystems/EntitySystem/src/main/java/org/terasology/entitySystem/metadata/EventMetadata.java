// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.entitySystem.metadata;

import org.terasology.assets.ResourceUrn;
import org.terasology.entitySystem.event.Event;
import org.terasology.entitySystem.metadata.extandable.ExtendableClassMetadata;
import org.terasology.entitySystem.metadata.extandable.ExtendableFieldMetadata;
import org.terasology.entitySystem.metadata.extandable.store.ClassMetadataStore;
import org.terasology.entitySystem.metadata.extandable.store.FieldMetadataStore;
import org.terasology.reflection.copy.CopyStrategyLibrary;
import org.terasology.reflection.reflect.InaccessibleFieldException;
import org.terasology.reflection.reflect.ReflectFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 *
 */
public class EventMetadata<T extends Event> extends ExtendableClassMetadata<T, ExtendableFieldMetadata<T, ?>> {


    /**
     * Creates a class metatdata
     *
     * @param uri The uri that identifies this type
     * @param type The type to create the metadata for
     * @param factory A reflection library to provide class construction and field get/set functionality
     * @param copyStrategyLibrary A copy strategy library
     * @param classMetadataStores classes, which should be used for extend information about class
     * @param fieldStoreTypes classes, which should be used for extend information about field
     * @throws NoSuchMethodException If the class has no default constructor
     */
    public EventMetadata(ResourceUrn uri,
                         Class<T> type,
                         ReflectFactory factory,
                         CopyStrategyLibrary copyStrategyLibrary,
                         List<Class<ClassMetadataStore>> classMetadataStores,
                         List<Class<? extends FieldMetadataStore<T>>> fieldStoreTypes) throws NoSuchMethodException {
        super(uri, type, factory, copyStrategyLibrary, classMetadataStores, fieldStoreTypes);
    }

    @Override
    protected ExtendableFieldMetadata<T, ?> createField(Field field, CopyStrategyLibrary copyStrategyLibrary,
                                                        ReflectFactory factory) throws InaccessibleFieldException {
        return new ExtendableFieldMetadata<T, Object>(this, field, copyStrategyLibrary, factory, getFieldStoreTypes());
    }
}
