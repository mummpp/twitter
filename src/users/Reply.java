package users;

import java.time.LocalDate;

public class Reply {
    private String reply;
    private int tweetId;
    private int userId;
    private LocalDate date;

    protected Reply(String comment, int tweetId, int userId) {
        this.reply = comment;
        this.tweetId = tweetId;
        this.userId = userId;
        this.date=LocalDate.now();
    }

    protected String getReply() {
        return reply;
    }

    protected LocalDate getDate() {
        return date;
    }

    public void print() {
        System.out.println("\tBy: "+UserValidate.findByUserId.apply(App.getUserList.get(),userId) +
                "\n\t  "+ reply +"\n\t  " + date);
    }
}
