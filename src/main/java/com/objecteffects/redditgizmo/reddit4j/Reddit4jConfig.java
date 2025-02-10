package com.objecteffects.redditgizmo.reddit4j;

import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class Reddit4jConfig {
    private static final Logger log =
            LoggerFactory.getLogger(Reddit4jConfig.class);

    @Property(name = "micronaut.server.host")
    private String host;

    @Property(name = "reddit.clientid")
    private String clientid;

    @Property(name = "reddit.secret")
    private String secret;

    @Property(name = "reddit.username")
    private String userName;

    @Property(name = "reddit.password")
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public String getClientId() {
        return clientid;
    }

    public void setClientId(final String clientId) {
        this.clientid = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(final String secret) {
        this.secret = secret;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
