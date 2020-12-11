/*
 * Copyright 2013 MovingBlocks
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

package org.terasology.entitySystem.metadata;

import org.terasology.module.ModuleEnvironment;
import org.terasology.persistence.typeHandling.TypeHandlerLibrary;
import org.terasology.reflection.copy.CopyStrategyLibrary;
import org.terasology.reflection.reflect.ReflectFactory;

/**
 * The set of metadata libraries used by the entity system
 *
 */
public class EntitySystemLibrary {

    private final TypeHandlerLibrary typeHandlerLibrary;
    private final ComponentLibrary componentLibrary;
    private final EventLibrary eventLibrary;

    public EntitySystemLibrary(ModuleEnvironment environment,
                               TypeHandlerLibrary typeHandlerLibrary,
                               ReflectFactory reflectFactory,
                               CopyStrategyLibrary copyStrategyLibrary) {

        this.typeHandlerLibrary = typeHandlerLibrary;
        this.componentLibrary = new ComponentLibrary(environment, reflectFactory, copyStrategyLibrary);
        this.eventLibrary = new EventLibrary(environment, reflectFactory, copyStrategyLibrary);
    }

    /**
     * @return The library of component metadata
     */
    public ComponentLibrary getComponentLibrary() {
        return componentLibrary;
    }

    /**
     * @return The library of serializers
     */
    public TypeHandlerLibrary getSerializationLibrary() {
        return typeHandlerLibrary;
    }

    /**
     * @return The library of event metadata
     */
    public EventLibrary getEventLibrary() {
        return eventLibrary;
    }

}
