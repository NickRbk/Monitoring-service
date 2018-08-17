package com.petproject.monitoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class Twitter4JConfiguration {

    private final Environment env;

    public Twitter4JConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public Twitter getTwitterInstance() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(env.getProperty("twitter4j.oauth.consumer-key"))
		  .setOAuthConsumerSecret(env.getProperty("twitter4j.oauth.consumer-secret"))
		  .setOAuthAccessToken(env.getProperty("twitter4j.oauth.access-token"))
		  .setOAuthAccessTokenSecret(env.getProperty("twitter4j.oauth.access-token-secret"));
		TwitterFactory tf = new TwitterFactory(cb.build());

        return tf.getInstance();
    }
}
