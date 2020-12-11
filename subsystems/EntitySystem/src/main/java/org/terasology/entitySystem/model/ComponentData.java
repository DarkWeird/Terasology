// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.entitySystem.model;

import java.util.List;

public class ComponentData {
    private final Integer typeIndex;
    private final String type;
    private final List<NameValue> field;

    public ComponentData(Integer typeIndex, String type, List<NameValue> field) {
        this.typeIndex = typeIndex;
        this.type = type;
        this.field = field;
    }

    public Integer getTypeIndex() {
        return typeIndex;
    }

    public String getType() {
        return type;
    }

    public List<NameValue> getField() {
        return field;
    }
}
