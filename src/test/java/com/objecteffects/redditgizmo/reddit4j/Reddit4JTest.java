package com.objecteffects.redditgizmo.reddit4j;

import io.micronaut.context.ApplicationContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import masecla.reddit4j.client.Reddit4J;
import masecla.reddit4j.client.UserAgentBuilder;
import masecla.reddit4j.exceptions.AuthenticationException;
import masecla.reddit4j.objects.RedditUser;
import masecla.reddit4j.requests.RedditUserListingEndpointRequest;
import org.jsoup.HttpStatusException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@MicronautTest
class Reddit4JTest {
    private static final Logger log =
            LoggerFactory.getLogger(Reddit4JTest.class);

    @Inject
    ApplicationContext applicationContext;

    @Inject
    Reddit4jConfig reddit4jConfig;

    private static void badUser(final List<RedditUser> friends, final Reddit4J client)
            throws IOException, InterruptedException {
        for (RedditUser ru : friends) {
            RedditUser friend = null;

            try {
                friend = client.getUser(ru.getName());
            }
            catch (HttpStatusException ex) {
                log.info("url: {}", ex.getUrl());
            }

            if (friend == null) {
                log.info("friend: {}", ru.getName());
            }
        }
    }

    @Test
    void testItWorks()
            throws AuthenticationException, IOException, InterruptedException {
        log.info("testItWorks: {}",
                applicationContext.getEnvironment().getActiveNames());
        log.info("testItWorks: {}", reddit4jConfig.getHost());

        Assertions.assertTrue(applicationContext.isRunning());

        UserAgentBuilder userAgent =
                new UserAgentBuilder().appname("Reddit4J").author("masecla22")
                        .version("1.0");
        Reddit4J client =
                Reddit4J.rateLimited().setUsername(reddit4jConfig.getUserName())
                        .setPassword(reddit4jConfig.getPassword())
                        .setClientId(reddit4jConfig.getClientId())
                        .setClientSecret(reddit4jConfig.getSecret())
                        .setUserAgent(userAgent);

        client.connect();
        client.ensureConnection();

        log.info("profile: {}", client.getSelfProfile());

        RedditUserListingEndpointRequest rulep = client.getFriends();
        List<RedditUser> friends = rulep.submit();
        List<String> names = new ArrayList<>();


        log.info("friends: {}", friends.size());

        // badUser(friends, client);
    }
}
