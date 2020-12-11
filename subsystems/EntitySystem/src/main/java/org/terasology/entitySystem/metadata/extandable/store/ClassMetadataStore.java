// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.entitySystem.metadata.extandable.store;

import org.terasology.entitySystem.metadata.extandable.ExtendableClassMetadata;
import org.terasology.entitySystem.metadata.extandable.ExtendableFieldMetadata;

public interface ClassMetadataStore {

    void processClass(ExtendableClassMetadata<?,? extends ExtendableFieldMetadata<?,?>> classMetadata);
}
