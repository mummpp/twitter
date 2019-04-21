package users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Tweet {
    String tweet;
    List<Tweet> reTweets;
    List<Like> likes;
    List<Comment> comments;
    int tweetId;
    int userId;
    LocalDate date;


    public Tweet(String tweet) {
        this.tweet = tweet;
        this.date=LocalDate.now();
        this.reTweets=new ArrayList<>();
        this.likes=new ArrayList<>();
        this.comments=new ArrayList<>();

    }

    public String getTweet() {return tweet;}
    public void setTweet(String tweet) {this.tweet = tweet;}
    public List<Tweet> getReTweets() {return reTweets;}
    public void setReTweets(List<Tweet> reTweets) {this.reTweets = reTweets;}
    public List<Like> getLikes() {return likes;}
    public void setLikes(List<Like> likes) {this.likes = likes;}
    public List<Comment> getComments() {return comments;}
    public void setComments(List<Comment> comments) {this.comments = comments;}
    public int getTweetId() {return tweetId;}
    public void setTweetId(int tweeterId) {this.tweetId = tweeterId;}
    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}
    public LocalDate getDate() {return date;}

    public void print() {
        System.out.println(date+"\t"+tweetId + "\t\t" + tweet+"\t"+comments.size()+"\t\t\t"+likes.size());
        comments.stream()
        .filter(comment -> !comment.getComment().equals("") || (comment.getComment() != null))
                .sorted(Comparator.comparing(Comment::getDate))
                .forEach(Comment::print);
    }
}
