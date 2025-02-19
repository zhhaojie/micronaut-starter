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
package io.micronaut.starter.netty.analytics;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.starter.analytics.Generated;
import io.micronaut.starter.api.analytics.AnalyticsOperations;

import java.util.concurrent.CompletableFuture;

@Requires(property = AnalyticsClient.SERVICE_ANALYTICS)
@Client("analytics")
public interface AnalyticsClient extends AnalyticsOperations {

    String SERVICE_ANALYTICS = "micronaut.http.services.analytics";

    @Override
    @Post("/analytics/report")
    CompletableFuture<HttpStatus> applicationGenerated(
            @NonNull @Body Generated generated
    );
}
