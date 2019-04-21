package users;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    protected String getUserName() {return userName;}
    protected String getPassword() {return password;}
    protected int getUserId() {return userId;}
    protected List<User> getFollowers() {return followers;}
    protected List<User> getFollowings() {return followings;}
    protected List<Tweet> getTweets() {return tweets;}
    @Override
    public String toString(){return this.userId+" "+this.userName;}

    protected void addTweet(Tweet tweet){
        tweet.setUserId(this.userId);
        tweet.setTweetId(RandomID.getID());
        this.tweets.add(tweet);
    }

    protected void addFollower(User user) {
        if (getFollower(user) != null) {
            System.out.println("This user is already following you.");
            return;
        }
        this.followers.add(user);
    }

    protected void removeFollower(User user){
        if (getFollower(user) == null) {
            System.out.println("This user is not following you.");
            return;
        }

        followers.remove(user);
    }

    private User getFollower(User user) {
        return followers
                .stream()
                .reduce(null, (accum, user2) -> user2.getUserId() == user.getUserId() ? user2 : accum);
    }

    protected void addFollowing(User user){
        this.followings.add(user);
        user.followers.add(this);
    }
    private User findByUserId(List<User> users, int id) {
        return users
                .stream()
                .reduce(null, (accum, user) -> user.getUserId() == id ? user : accum);
    }

    protected void print() {
        System.out.println(userId + "\t" + userName + "\t" + followings.size() +
                "\t" + followers.size() + "\t" + tweets.size());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        User user = (User) obj;
        return user.getUserId() == this.userId;
    }

    static class RandomID {
        private RandomID(){}

        static final List<Integer>  numbers = new Random(1000).ints(0,350).limit(100)
                .boxed()
                .distinct()
                .collect(Collectors.toList());
        static int i=-1;
        public static int getID(){
            return numbers.get(++i);
        }

    }

}
