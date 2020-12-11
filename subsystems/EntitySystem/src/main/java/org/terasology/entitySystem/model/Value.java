// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.entitySystem.model;

import javax.naming.Name;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class Value {
    private final List<Double> doubleList;
    private final List<Float> floatList;
    private final List<Integer> integerList;
    private final List<Long> longList;
    private final List<Boolean> booleanList;
    private final List<String> stringList;
    private final Optional<byte[]> bytes;
    private final List<Value> valueList;
    private final List<NameValue> nameValue;

}
