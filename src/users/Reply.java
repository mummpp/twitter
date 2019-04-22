package users;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Reply {
    private String comment;
    private int tweetId;
    private int userId;
    private LocalDate date;

    protected Reply(String comment, int tweetId, int userId) {
        this.comment = comment;
        this.tweetId = tweetId;
        this.userId = userId;
        this.date=LocalDate.now();
    }

    protected String getReply() {
        return comment;
    }

    protected LocalDate getDate() {
        return date;
    }

    Function<Integer,String> getUserName=id->{
        return new User().getTweets().stream()
                .filter(tweet -> tweet.getUserId()==id)
                .map(tweet ->tweet.getUser().getUserName())
                .collect(Collectors.toList()).get(0);
    };

    public void print() {
        System.out.println("\tBy: "+getUserName.apply(userId) +"\n\t  "+ comment +"\n\t  " + date);
    }
}
