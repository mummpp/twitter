package users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Tweet {
    private String tweet;
    private List<Tweet> reTweets;
    private List<Like> likes;
    private List<Reply> replies;
    private int tweetId;
    private int userId;
    private LocalDate date;
    private User user;


    public Tweet(String tweet) {
        this.tweet = tweet;
        user=new User();
        this.date=LocalDate.now();
        this.reTweets=new ArrayList<>();
        this.likes=new ArrayList<>();
        this.replies =new ArrayList<>();

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTweet() {return tweet;}
    public void setTweet(String tweet) {this.tweet = tweet;}
    public List<Tweet> getReTweets() {return reTweets;}
    public void setReTweets(List<Tweet> reTweets) {this.reTweets = reTweets;}
    public List<Like> getLikes() {return likes;}
    public void setLikes(List<Like> likes) {this.likes = likes;}
    public List<Reply> getReplies() {return replies;}
    public void setReplies(List<Reply> replies) {this.replies = replies;}
    public int getTweetId() {return tweetId;}
    public void setTweetId(int tweeterId) {this.tweetId = tweeterId;}
    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}
    public LocalDate getDate() {return date;}

    public void addReply(String reply, int userId) {
        Reply newReply = new Reply(reply, this.tweetId, userId);
        this.replies.add(newReply);
    }

    public void addLikeOnThisTweet(int userId){
        likes.add(new Like(userId));
    }
    public void removeLikeOnThisTweet(int userId){
        likes.remove(new Like(userId));
    }


    public void addRetweet(Tweet reTweeet){
        this.reTweets.add(reTweeet);
    }

    public void print(){
        System.out.println(date+"\t"+tweetId + "\t\t" + tweet+"\t"+ replies.size()+"\t\t\t"+likes.size());
        replies.stream()
        .filter(reply -> !reply.getReply().equals("") || (reply.getReply() != null))
                .sorted(Comparator.comparing(Reply::getDate).reversed())
                .forEach(Reply::print);
    }
}
