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

    public void print() {
        System.out.println("\tBy: "+UserValidate.findByUserId.apply(App.getUserList(),userId) +
                "\n\t  "+ comment +"\n\t  " + date);
    }
}
