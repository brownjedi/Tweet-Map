package edu.columbia.gskr.tweetmap.app;


import edu.columbia.gskr.tweetmap.domain.Tweet;
import edu.columbia.gskr.tweetmap.mybatis.service.TweetService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saikarthikreddyginni on 2/27/15.
 */


public class App {

    public static void main(String[] args) {
        System.out.println("Booga Booga Booga...!!!");
        TweetService tweetService = new TweetService();
        List<String> hash = new ArrayList<String>();
        hash.add("KCA");
        hash.add("Jobs");
        List<Tweet> tweet = tweetService.getTweetsByHashTag(hash);
        System.out.println(tweet);
    }

}
