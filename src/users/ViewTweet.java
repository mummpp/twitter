package users;

import java.util.Comparator;

public class ViewTweet {
    private void drawLine() {
        System.out.println("--------------------------------------------");
    }
    private void viewTweetBar() {
        drawLine();
        System.out.println("Date#\t\tTweet#\tTweet\tComments#\tLikes#");
        drawLine();
    }
    private void viewMyTweets(User user) {
        viewTweetBar();
        user.getTweets().stream()
                .sorted(Comparator.comparing(Tweet::getDate))
                .forEach(Tweet::print);
      //  succeedLogIn(user);

    }
}
