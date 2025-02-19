/*
 * Copyright 2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.starter.cli.command;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.starter.options.BuildTool;
import picocli.CommandLine;

@Introspected
public class BuildToolConverter implements CommandLine.ITypeConverter<BuildTool> {

    @Override
    public BuildTool convert(String value) throws Exception {
        if (value != null) {
            for (BuildTool bt : BuildTool.values()) {
                if (value.equalsIgnoreCase(bt.toString())) {
                    return bt;
                }
            }
        }
        return BuildTool.DEFAULT_OPTION;
    }
}
