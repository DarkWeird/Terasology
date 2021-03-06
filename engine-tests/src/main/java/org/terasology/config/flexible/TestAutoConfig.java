/*
 * Copyright 2019 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.config.flexible;

import com.google.common.collect.ImmutableList;
import org.terasology.reflection.TypeInfo;

import static org.terasology.config.flexible.SettingArgument.*;

public class TestAutoConfig extends AutoConfig {
    public final Setting<String> stringSetting = setting(
        type(String.class),
        defaultValue(""),
        name("Human Readable Name")
    );

    public final Setting<ImmutableList<Integer>> integerListSetting = setting(
        type(new TypeInfo<ImmutableList<Integer>>() {}),
        defaultValue(ImmutableList.of())
    );
}
