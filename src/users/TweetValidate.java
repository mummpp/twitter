package users;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TweetValidate {
    static Consumer<User> viewTweets=user -> {
        UiFormat.drawTweetTable.draw();
        user.getTweets().stream()
                .sorted(Comparator.comparing(Tweet::getDate).reversed())
                .forEach(Tweet::print);
    };
    static Consumer<User> viewAllTweets = user -> {
        UiFormat.drawTweetTable.draw();
        Stream.concat(user.getTweets().stream(),
                user.getFollowings().stream()
                        .flatMap(user1 -> user1.getTweets().stream()))
                .sorted(Comparator.comparing(Tweet::getDate).reversed()).forEach(Tweet::print);
//        List<User> userAndItsFollowings = new ArrayList<>();
//        userAndItsFollowings.add(user);
//        userAndItsFollowings.addAll(user.getFollowings());
//        TweetValidate.viewAllTweets.accept(userAndItsFollowings);
        App.reactionOnTweet(user);
        App.succeedLogIn.accept(user);
    };

    static BiConsumer<User, Scanner> addTweet= (user,scan) -> {
        //private void addTweet(User user) {
        scan.nextLine();
        System.out.print("Type your tweet here:\t");
        user.addTweet(new Tweet(scan.nextLine()));
        System.out.println("Tweet added successfully");
        App.succeedLogIn.accept(user);

    };
    static BiConsumer<User, Scanner> viewMyTweets=(user,scan) ->  {
        TweetValidate.viewTweets.accept(user);
        App.reactionOnTweet(user);
        App.succeedLogIn.accept(user);

    };

    static BiFunction<Integer, User, Tweet> validateTweet=(tweetID, user)->{
        //List<User> userAndItsFollowings = UserValidate.getUserAndItsFollowings.apply(user);
        return Stream.concat(user.getTweets().stream(),user.getFollowings().stream()
                .flatMap(user1 -> user1.getTweets().stream()))
                .filter(tweet -> tweet.getTweetId()==tweetID)
                .collect(Collectors.toList()).get(0);
//        List<User> userAndItsFollowings = new ArrayList<>();
//        userAndItsFollowings.add(user);
//        userAndItsFollowings.addAll(user.getFollowings());
//        return userAndItsFollowings.stream()
//                .flatMap(u -> u.getTweets().stream())
//                .filter(tweet -> tweet.getTweetId()==tweetID)
//                .collect(Collectors.toList()).get(0);
    };


}
