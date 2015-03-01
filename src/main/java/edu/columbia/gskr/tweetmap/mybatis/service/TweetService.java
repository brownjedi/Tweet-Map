package edu.columbia.gskr.tweetmap.mybatis.service;

import edu.columbia.gskr.tweetmap.domain.Tweet;
import edu.columbia.gskr.tweetmap.mybatis.mapper.TweetMapper;
import edu.columbia.gskr.tweetmap.mybatis.util.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;

import java.util.List;

/**
 * Created by saikarthikreddyginni on 2/27/15.
 */

public class TweetService {

    public Tweet getTweetById(long id) {

        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession(TransactionIsolationLevel.READ_UNCOMMITTED);
        Tweet tweet;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweet = tweetMapper.getTweetById(id);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return tweet;
    }

    public List<Tweet> getAllTweets() {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession(TransactionIsolationLevel.READ_UNCOMMITTED);
        List<Tweet> tweetList;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetList = tweetMapper.getAllTweets();
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return tweetList;
    }

    public List<Tweet> getTweetsLimit(int limit) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession(TransactionIsolationLevel.READ_UNCOMMITTED);
        List<Tweet> tweetList;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetList = tweetMapper.getTweetsLimit(limit);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return tweetList;
    }

    public List<Tweet> getTweetsByHashTag(List<String> hashTags) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession(TransactionIsolationLevel.READ_UNCOMMITTED);
        List<Tweet> tweetList;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetList = tweetMapper.getTweetsByHashTag(hashTags);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return tweetList;
    }

    public List<String> getAllHashTags() {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession(TransactionIsolationLevel.READ_UNCOMMITTED);
        List<String> hashTagList;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            hashTagList = tweetMapper.getAllHashTags();
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return hashTagList;
    }

    public List<String> getHashTagsLimit(int limit) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession(TransactionIsolationLevel.READ_UNCOMMITTED);
        List<String> hashTagList;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            hashTagList = tweetMapper.getHashTagsLimit(limit);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return hashTagList;
    }

    public void updateTweet(Tweet tweet) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetMapper.updateTweet(tweet);
            tweetMapper.deleteHashTags(tweet.getId());
            if (tweet.getHashTags() != null && tweet.getHashTags().size() > 0) {
                tweetMapper.insertHashTags(tweet.getHashTags(), tweet.getId());
            }
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    public void insertTweet(Tweet tweet) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetMapper.insertTweet(tweet);
            if (tweet.getHashTags() != null && tweet.getHashTags().size() > 0) {
                tweetMapper.insertHashTags(tweet.getHashTags(), tweet.getId());
            }
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    public void deleteTweet(Tweet tweet) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetMapper.deleteHashTags(tweet.getId());
            tweetMapper.deleteTweet(tweet);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    public int getTweetCount() {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession(TransactionIsolationLevel.READ_UNCOMMITTED);
        int count = 0;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            count = tweetMapper.getTweetCount();
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return count;
    }

    public long getOldestTweetId() {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession(TransactionIsolationLevel.READ_UNCOMMITTED);
        long tweetId;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetId = tweetMapper.getOldestTweetId();
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return tweetId;
    }

    public boolean tweetExists(long id) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        boolean tweetPresent;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetPresent = tweetMapper.tweetExists(id);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return tweetPresent;
    }

    public void deleteTweetById(long tweetId) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetMapper.deleteHashTags(tweetId);
            tweetMapper.deleteTweetById(tweetId);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}

