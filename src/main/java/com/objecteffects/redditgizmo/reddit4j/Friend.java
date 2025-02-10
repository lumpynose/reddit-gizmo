package com.objecteffects.redditgizmo.reddit4j;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record Friend(String name, long latest) {
}
