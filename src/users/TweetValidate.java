package users;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class TweetValidate {
    static Consumer<User> viewTweets=user -> {
        UiFormat.drawTweetTable.draw();
        user.getTweets().stream()
                .sorted(Comparator.comparing(Tweet::getDate).reversed())
                .forEach(Tweet::print);
    };
    static Consumer<List<User>> viewAllTweets=users -> {
        UiFormat.drawTweetTable.draw();
        users.stream()
                .flatMap(u -> u.getTweets().stream())
                .sorted(Comparator.comparing(Tweet::getDate).reversed())
                .forEach(Tweet::print);
    };

}
