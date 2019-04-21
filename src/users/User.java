package users;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class User {

    private String userName;
    private String password;
    private int userId;
    private List<User> followers;
    private List<User> followings;
    private List<Tweet> tweets;

    public User(String userName, String password, int id) {
        this.userName = userName;
        this.password = password;
        this.userId= id;
        followers=new ArrayList<>();
        followings=new ArrayList<>();
        tweets=new ArrayList<>();

    }

    public String getUserName() {return userName;}
    public String getPassword() {return password;}
    public int getUserId() {return userId;}
    public List<User> getFollowers() {return followers;}
    public List<User> getFollowings() {return followings;}
    public List<Tweet> getTweets() {return tweets;}
    @Override
    public String toString(){return this.userId+" "+this.userName;}

    public void addTweet(Tweet tweet){
        tweet.setUserId(this.userId);
        tweet.setTweetId(this.tweets.size()+1);
        this.tweets.add(tweet);
    }

}
