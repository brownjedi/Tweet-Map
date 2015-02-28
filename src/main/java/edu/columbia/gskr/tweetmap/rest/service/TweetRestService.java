package edu.columbia.gskr.tweetmap.rest.service;

import edu.columbia.gskr.tweetmap.domain.Tweet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by saikarthikreddyginni on 2/27/15.
 */


@Path("/tweet")
public class TweetRestService {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getHome() {
        return TweetRestService.class.getCanonicalName();
    }

    @GET
    @Path("/getTweet/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Tweet getTweets(@PathParam("id") int id) {
        Tweet tweet = new Tweet();
        tweet.setId(id);
        return tweet;
    }

}