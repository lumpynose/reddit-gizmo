package com.objecteffects.redditgizmo.reddit4j;

import io.micronaut.core.annotation.Introspected;

import java.util.Collections;

@Introspected
public record Friend(String name, long latest) {
}
