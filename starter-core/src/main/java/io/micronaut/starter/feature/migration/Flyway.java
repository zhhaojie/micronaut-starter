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
package io.micronaut.starter.feature.migration;

import io.micronaut.starter.application.generator.GeneratorContext;
import io.micronaut.starter.build.dependencies.Dependency;

import javax.inject.Singleton;

@Singleton
public class Flyway implements MigrationFeature {

    @Override
    public String getName() {
        return "flyway";
    }

    @Override
    public String getTitle() {
        return "Flyway Database Migration";
    }

    @Override
    public String getDescription() {
        return "Adds support for Flyway database migrations (https://flywaydb.org/)";
    }

    @Override
    public String getThirdPartyDocumentation() {
        return "https://flywaydb.org/";
    }

    @Override
    public String getMicronautDocumentation() {
        return "https://micronaut-projects.github.io/micronaut-flyway/latest/guide/index.html";
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        generatorContext.addDependency(Dependency.builder()
                .groupId("io.micronaut.flyway")
                .artifactId("micronaut-flyway")
                .compile());
    }
}

