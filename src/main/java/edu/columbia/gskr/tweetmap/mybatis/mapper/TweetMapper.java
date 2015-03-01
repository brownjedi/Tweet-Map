package edu.columbia.gskr.tweetmap.mybatis.mapper;

import edu.columbia.gskr.tweetmap.domain.Tweet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by saikarthikreddyginni on 2/27/15.
 */

public interface TweetMapper {

    public int getTweetCount();

    public Tweet getTweetById(long id);

    public List<Tweet> getAllTweets();

    public List<Tweet> getTweetsLimit(int limit);

    public List<Tweet> getTweetsByHashTag(@Param("hashTags") List<String> hashTags);

    public List<String> getAllHashTags();

    public List<String> getHashTagsLimit(int limit);

    public void updateTweet(Tweet tweet);

    public void insertTweet(Tweet tweet);

    public void insertHashTags(@Param("hashTags") List<String> hashTags, @Param("tweetId") long tweetId);

    public void deleteTweet(Tweet tweet);

    public void deleteTweetById(long tweetId);

    public void deleteHashTags(long tweetId);

    public long getOldestTweetId();

    public boolean tweetExists(long id);

}