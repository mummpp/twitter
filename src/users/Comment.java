package users;

import java.time.LocalDate;

public class Comment {
    private String comment;
    private String userName;
    private int tweetId;
    private int userId;
    private LocalDate date;

    public Comment(String comment, int tweetId, int userId) {
        this.comment = comment;
        this.tweetId = tweetId;
        this.userId = userId;
        this.date=LocalDate.now();
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void print() {
        System.out.println("\tBy: "+userName +"\n\t  "+ comment +"\n\t  " + date);
    }
}
