// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.entitySystem.model;

import org.terasology.entitySystem.Component;
import org.terasology.entitySystem.entity.internal.EntityScope;

import java.util.List;
import java.util.Optional;

public class EntityData {
    private final Optional<Long> id;
    private final List<ComponentData> componentList;
    private final List<Integer> removedComponentIndex;
    private final Optional<String> parentPrefab;
    private final  Optional<Boolean> alwaysRelevant;
    private final Optional<Long> owner;
    private final List<String> removedComponent;
    private final Optional<EntityScope> scope;
    
}
