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
package io.micronaut.starter.feature.discovery;

import io.micronaut.starter.application.generator.GeneratorContext;
import io.micronaut.starter.build.dependencies.Dependency;

import javax.inject.Singleton;

@Singleton
public class Eureka implements DiscoveryFeature {

    @Override
    public String getName() {
        return "discovery-eureka";
    }

    @Override
    public String getTitle() {
        return "Eureka Service Discovery";
    }

    @Override
    public String getDescription() {
        return "Adds support for Service Discovery with Eureka";
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        generatorContext.addDependency(Dependency.builder()
                .groupId("io.micronaut.discovery")
                .artifactId("micronaut-discovery-client")
                .compile());

        generatorContext.getConfiguration().put("eureka.client.registration.enabled", true);
        generatorContext.getConfiguration().put("eureka.client.defaultZone", "${EUREKA_HOST:localhost}:${EUREKA_PORT:8761}");
    }

    @Override
    public String getMicronautDocumentation() {
        return "https://docs.micronaut.io/latest/guide/index.html#serviceDiscoveryEureka";
    }
}
