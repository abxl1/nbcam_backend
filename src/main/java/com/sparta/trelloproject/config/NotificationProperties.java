package com.sparta.trelloproject.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "webhook")
public class NotificationProperties {

    private String slackUrl;
    private String discordUrl;

    public String getSlackUrl() {
        return slackUrl;
    }

    public void setSlackUrl(String slackUrl) {
        this.slackUrl = slackUrl;
    }

    public String getDiscordUrl() {
        return discordUrl;
    }

    public void setDiscordUrl(String discordUrl) {
        this.discordUrl = discordUrl;
    }
}