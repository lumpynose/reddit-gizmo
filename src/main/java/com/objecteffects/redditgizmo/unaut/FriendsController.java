/*
 * Copyright 2017-2024 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.objecteffects.redditgizmo.unaut;

import com.objecteffects.redditgizmo.reddit4j.Reddit4jClient;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.views.View;
import jakarta.inject.Inject;
import masecla.reddit4j.exceptions.AuthenticationException;

import java.io.IOException;

@Controller("/")
@ExecuteOn(TaskExecutors.BLOCKING)
public class FriendsController {
    @Inject
    Reddit4jClient reddit4jClient;

    @View("friends")
    @Get("/")
    public HttpResponse<?> index()
            throws AuthenticationException, IOException, InterruptedException {
        return HttpResponse.ok(reddit4jClient.getFriends());
    }
}
