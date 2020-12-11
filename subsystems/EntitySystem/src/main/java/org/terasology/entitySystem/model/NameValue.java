// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.entitySystem.model;

import java.util.Optional;

public class NameValue {
    private final Optional<String> name;
    private final Optional<Value> value;
    private final Optional<Integer> nameIndex;

    public NameValue(Optional<String> name, Optional<Value> value, Optional<Integer> nameIndex) {
        this.name = name;
        this.value = value;
        this.nameIndex = nameIndex;
    }

    public Optional<String> getName() {
        return name;
    }

    public Optional<Value> getValue() {
        return value;
    }

    public Optional<Integer> getNameIndex() {
        return nameIndex;
    }
}
