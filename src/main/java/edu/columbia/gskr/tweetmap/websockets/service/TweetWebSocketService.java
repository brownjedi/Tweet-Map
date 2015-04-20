package edu.columbia.gskr.tweetmap.websockets.service;

import edu.columbia.gskr.tweetmap.domain.Tweet;
import edu.columbia.gskr.tweetmap.util.StringUtils;
import edu.columbia.gskr.tweetmap.websockets.encoder.TweetMessageEncoder;
import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by saikarthikreddyginni on 3/14/15.
 */

@ServerEndpoint(value = "/websockets", encoders = TweetMessageEncoder.class)
@SuppressWarnings("unused")

public class TweetWebSocketService {

    private static final Logger LOGGER = Logger.getLogger(TweetWebSocketService.class.getClass().getCanonicalName());
    private static Configuration configuration = null;
    private static Hashtable<Session, TwitterStream> map = new Hashtable<Session, TwitterStream>();

    public static Configuration getConfiguration() {

        if (configuration == null) {
            Properties twitterProperties = new Properties();
            InputStream input = TweetWebSocketService.class.getClassLoader().getResourceAsStream("twitter.properties");

            LOGGER.log(Level.INFO, "Accessing the twitter.properties file");
            try {
                twitterProperties.load(input);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "IOException Occurred when getting the twitter.properties", ex);
                System.exit(-1);
            }

            LOGGER.log(Level.INFO, "Creating the Configuration Builder");

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(twitterProperties.getProperty("twitter.OAuthConsumerKey"))
                    .setOAuthConsumerSecret(twitterProperties.getProperty("twitter.OAuthConsumerSecret"))
                    .setOAuthAccessToken(twitterProperties.getProperty("twitter.OAuthAccessToken"))
                    .setOAuthAccessTokenSecret(twitterProperties.getProperty("twitter.OAuthAccessTokenSecret"));

            LOGGER.log(Level.INFO, "Creating the Configuration Builder");

            configuration = cb.build();
        }

        return configuration;
    }


    @OnOpen
    public void onOpen(final Session session) {
        LOGGER.log(Level.INFO, session.getId() + " has opened a connection");
        final TwitterStream twitterStream = new TwitterStreamFactory(getConfiguration()).getInstance();
        StatusListener statusListener = new StatusListener() {
            public void onStatus(Status status) {
                if (status.getGeoLocation() != null) {
                    try {
                        Tweet tweet = new Tweet();
                        tweet.setId(status.getId());
                        tweet.setUserScreenName(StringUtils.convertToUTF8(status.getUser().getScreenName()));
                        tweet.setUserLocation(StringUtils.convertToUTF8(status.getUser().getLocation()));
                        tweet.setProfileImageURL(status.getUser().getBiggerProfileImageURL());
                        tweet.setStatusText(StringUtils.convertToUTF8(status.getText()));
                        tweet.setLatitude(status.getGeoLocation().getLatitude());
                        tweet.setLongitude(status.getGeoLocation().getLongitude());
                        tweet.setCreatedDate(status.getCreatedAt());
                        tweet.setUpdatedDate(new Date());
                        session.getBasicRemote().sendObject(tweet);
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "IO Exception", e);
                    } catch (EncodeException e) {
                        LOGGER.log(Level.SEVERE, "Encode Exception", e);
                    }
                }
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                // Do Nothing
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                // Do Nothing
            }

            public void onScrubGeo(long userId, long upToStatusId) {
                // Do Nothing
            }

            public void onStallWarning(StallWarning warning) {
                // Do Nothing
            }

            public void onException(Exception ex) {
                LOGGER.log(Level.SEVERE, "Exception in Status Listener", ex);
            }

        };
        twitterStream.addListener(statusListener);
        map.put(session, twitterStream);
    }

    @OnMessage
    public void onMessage(final String message, final Session session) {

        TwitterStream twitterStream = map.get(session);
        if (twitterStream != null) {
            if (!message.equalsIgnoreCase("")) {
                FilterQuery filterQuery = new FilterQuery();
                filterQuery.track(message.split(","));
                twitterStream.filter(filterQuery);
            } else {
                twitterStream.sample();
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        LOGGER.log(Level.INFO, session.getId() + " has ended");
        TwitterStream twitterStream = map.get(session);
        if (twitterStream != null) {
            twitterStream.clearListeners();
            twitterStream.cleanUp();
            twitterStream.shutdown();
        }
        map.remove(session);
    }

    @OnError
    public void onError(Throwable t, Session session) {
        LOGGER.log(Level.SEVERE, session.getId() + " Returned an Error (Probably force closed)...!!!", t);
        TwitterStream twitterStream = map.get(session);
        if (twitterStream != null) {
            twitterStream.clearListeners();
            twitterStream.cleanUp();
            twitterStream.shutdown();
        }
        map.remove(session);
    }

}
