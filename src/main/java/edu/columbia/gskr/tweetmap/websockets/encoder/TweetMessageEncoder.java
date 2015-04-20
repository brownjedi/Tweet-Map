package edu.columbia.gskr.tweetmap.websockets.encoder;

import com.owlike.genson.Genson;
import edu.columbia.gskr.tweetmap.domain.Tweet;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by saikarthikreddyginni on 3/14/15.
 */
public class TweetMessageEncoder implements Encoder.Text<Tweet> {

    public String encode(final Tweet tweet) throws EncodeException {
        return new Genson().serialize(tweet);
    }

    public void init(EndpointConfig config) {

    }

    public void destroy() {

    }
}
