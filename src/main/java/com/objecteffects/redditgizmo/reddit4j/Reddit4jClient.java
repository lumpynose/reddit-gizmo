package com.objecteffects.redditgizmo.reddit4j;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import masecla.reddit4j.client.Reddit4J;
import masecla.reddit4j.client.UserAgentBuilder;
import masecla.reddit4j.exceptions.AuthenticationException;
import masecla.reddit4j.objects.RedditPost;
import masecla.reddit4j.objects.RedditUser;
import masecla.reddit4j.requests.RedditUserListingEndpointRequest;
import masecla.reddit4j.requests.SubredditPostListingEndpointRequest;
import org.jsoup.HttpStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

@Singleton
public class Reddit4jClient {
    private static final Logger log =
            LoggerFactory.getLogger(Reddit4jClient.class);

    @Inject
    Reddit4jConfig reddit4jConfig;

    Reddit4J client = null;

    public void setup()
            throws AuthenticationException, IOException, InterruptedException {
        UserAgentBuilder userAgent =
                new UserAgentBuilder().appname("Reddit4J").author("masecla22")
                        .version("1.0");
        client =
                Reddit4J.rateLimited().setUsername(reddit4jConfig.getUserName())
                        .setPassword(reddit4jConfig.getPassword())
                        .setClientId(reddit4jConfig.getClientId())
                        .setClientSecret(reddit4jConfig.getSecret())
                        .setUserAgent(userAgent);

        client.connect();
        client.ensureConnection();
    }

    public Map<String, Object> getFriends()
            throws AuthenticationException, IOException, InterruptedException {
        if (client == null) {
            setup();
        }

        RedditUserListingEndpointRequest ruler = client.getFriends();

        List<RedditUser> users = ruler.submit();
        List<Friend> friends = new ArrayList<>();

        for (RedditUser user: users) {
            SubredditPostListingEndpointRequest spler = client.getUserSubmitted(user.getName());

            try {
                List<RedditPost> posts = spler.submit();
                if (posts.isEmpty()) {
                    Friend friend = new Friend(user.getName(), 0L);
                    friends.add(friend);
                }
                else {
                    Friend friend = new Friend(user.getName(),
                            posts.getFirst().getCreatedUtc());
                    friends.add(friend);

                    log.info("{} {}", user.getName(), posts.getFirst().getCreatedUtc());
                }
            }
            catch (HttpStatusException ex) {
                Friend friend = new Friend(user.getName(), 0L);
                friends.add(friend);
            }
        }

        Map<String, Object> model = new HashMap<>();
        model.put("friends", friends);

        return model;
    }
}
