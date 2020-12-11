// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.entitySystem.model;

import java.util.List;
import java.util.Optional;

public class PrefabData {
    private final Optional<Integer> nameIndex;
    private final List<ComponentData> componentList;
    private final List<Integer> deprecated;
    private final Optional<Boolean> persisted;
    private final List<String> removedComponent;
    private final Optional<Boolean> alwaysRelevant;
    private final Optional<String> name;
    private final Optional<String> parentName;
}
