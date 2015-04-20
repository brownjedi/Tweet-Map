package edu.columbia.gskr.tweetmap.websockets.decoder;

import com.owlike.genson.Genson;
import edu.columbia.gskr.tweetmap.domain.Tweet;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by saikarthikreddyginni on 3/14/15.
 */
public class TweetMessageDecoder implements Decoder.Text<Tweet> {

    public Tweet decode(String tweet) throws DecodeException {
        return new Genson().deserialize(tweet, Tweet.class);
    }

    public boolean willDecode(String s) {
        return true;
    }

    public void init(EndpointConfig config) {

    }

    public void destroy() {

    }
}
