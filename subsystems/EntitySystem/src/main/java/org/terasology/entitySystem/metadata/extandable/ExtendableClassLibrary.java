// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.entitySystem.metadata.extandable;

import com.google.common.collect.Lists;
import org.terasology.assets.ResourceUrn;
import org.terasology.entitySystem.metadata.extandable.store.ClassMetadataStore;
import org.terasology.entitySystem.metadata.extandable.store.FieldMetadataStore;
import org.terasology.module.ModuleEnvironment;
import org.terasology.reflection.copy.CopyStrategyLibrary;
import org.terasology.reflection.metadata.AbstractClassLibrary;
import org.terasology.reflection.metadata.ClassMetadata;
import org.terasology.reflection.reflect.ReflectFactory;

import java.util.List;

public class ExtendableClassLibrary<T> extends AbstractClassLibrary<T> {

    private final List<Class<ClassMetadataStore>> classMetadataStoreClasses = Lists.newLinkedList();
    private final List<Class<? extends FieldMetadataStore<?>>> fieldMetadataStoreClasses = Lists.newLinkedList();

    public ExtendableClassLibrary(ModuleEnvironment environment, ReflectFactory reflectFactory,
                                  CopyStrategyLibrary copyStrategyLibrary) {
        super(environment, reflectFactory, copyStrategyLibrary);
    }

    public ExtendableClassLibrary(AbstractClassLibrary<T> factory, CopyStrategyLibrary copyStrategies) {
        super(factory, copyStrategies);
    }


    @Override
    protected <C extends T> ClassMetadata<C, ?> createMetadata(Class<C> type, ReflectFactory factory,
                                                               CopyStrategyLibrary copyStrategies, ResourceUrn name) {
        return null;
    }

    protected List<Class<ClassMetadataStore>> getClassMetadataStoreClasses() {
        return classMetadataStoreClasses;
    }

    protected List<Class<? extends FieldMetadataStore<?>>> getFieldMetadataStoreClasses() {
        return fieldMetadataStoreClasses;
    }

    public void addClassStore(Class<ClassMetadataStore> storeClass) {
        classMetadataStoreClasses.add(storeClass);
    }

    public void addFieldStore(Class<? extends FieldMetadataStore<?>> storeClass) {
        fieldMetadataStoreClasses.add(storeClass);
    }
}
