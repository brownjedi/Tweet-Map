package edu.columbia.gskr.tweetmap.rest.service;

import edu.columbia.gskr.tweetmap.domain.Tweet;
import edu.columbia.gskr.tweetmap.mybatis.service.TweetService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by saikarthikreddyginni on 2/27/15.
 */


@Path("/tweets")
public class TweetRestService {

    private final TweetService tweetService = new TweetService();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Tweet> getTweetsLimit(@QueryParam("limit") int limit, @QueryParam("hashTag") List<String> hashTags) {

        if (hashTags != null && hashTags.size() > 0) {
            return tweetService.getTweetsByHashTag(hashTags);
        } else if(limit != 0) {
            return tweetService.getTweetsLimit(limit);
        } else {
            return tweetService.getAllTweets();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Tweet getTweetById(@PathParam("id") long id) {
        return tweetService.getTweetById(id);
    }

    @GET
    @Path("/hashTags")
    @Produces({MediaType.APPLICATION_JSON})
    public List<String> getHashTagsLimit(@QueryParam("limit") int limit) {
        if(limit != 0){
            return tweetService.getHashTagsLimit(limit);
        }else{
            return tweetService.getAllHashTags();
        }
    }

    @GET
    @Path("/tweetCount")
    @Produces({MediaType.APPLICATION_JSON})
    public int getTweetCount() {
        return tweetService.getTweetCount();
    }
}