/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.geronimo.config.cdi;

import org.apache.geronimo.config.ConfigImpl;
import javax.config.inject.ConfigProperty;

import java.util.function.Supplier;

class ConfigSupplier<T> implements Supplier<T> {

    private final Class<T> clazz;
    private final String key;
    private final String defaultValue;
    private final ConfigImpl config;

    ConfigSupplier(Class<T> clazz, String key, String defaultValue, ConfigImpl config) {
        this.clazz = clazz;
        this.key = key;
        this.defaultValue = defaultValue;
        this.config = config;
    }

    @Override
    public T get() {
        String rawValue = config.getOptionalValue(key, String.class).orElse(defaultValue);
        if (ConfigProperty.UNCONFIGURED_VALUE.equals(rawValue)) { // property not set
            return null;
        }
        return config.convert(rawValue, clazz);
    }
}
