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
package io.micronaut.starter.feature.netflix;

import io.micronaut.starter.application.ApplicationType;
import io.micronaut.starter.application.generator.GeneratorContext;
import io.micronaut.starter.build.dependencies.Dependency;
import io.micronaut.starter.feature.Category;
import io.micronaut.starter.feature.Feature;

import javax.inject.Singleton;

@Singleton
public class Ribbon implements Feature {

    @Override
    public String getName() {
        return "netflix-ribbon";
    }

    @Override
    public String getTitle() {
        return "Netflix Ribbon LoadBalancer";
    }

    @Override
    public String getDescription() {
        return "Adds support for Netflix Ribbon";
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        generatorContext.getConfiguration().put("ribbon.VipAddress", "test");
        generatorContext.getConfiguration().put("ribbon.ServerListRefreshInterval", 2000);
        generatorContext.addDependency(Dependency.builder()
                .groupId("io.micronaut.netflix")
                .artifactId("micronaut-netflix-ribbon")
                .compile());
    }

    @Override
    public boolean supports(ApplicationType applicationType) {
        return true;
    }

    @Override
    public String getCategory() {
        return Category.CLIENT;
    }

    @Override
    public String getMicronautDocumentation() {
        return "https://docs.micronaut.io/latest/guide/index.html#netflixRibbon";
    }
}
