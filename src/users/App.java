package users;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class App {

    private static Scanner scan;
    private static List<User> userList;
    private static final int start =1;

    App() {
        userList = new ArrayList<>();
        UiFormat.welcome.draw();
        scan = new Scanner(System.in);

    }

    static Supplier<List<User>> getUserList=()-> userList;

    private Function<Integer,Integer> getStart=x-> {
        UiFormat.startBar.accept(x);
        return scan.nextInt();
    };

    private void userRegister() {
        String[] userRegInfo = new String[2];
        scan.nextLine();
        System.out.println(">>\tEnter UserName");
        userRegInfo[0] = (scan.nextLine());

        //check if the username is already taken
        while (UserValidate.checkUserName.apply(userList,userRegInfo[0])){
            System.out.println("User name already exists.\nEnter new User Name\n" +
                    "(or enter 'x' to return to main menu\n");
            userRegInfo[0] = (scan.nextLine());
        }
        System.out.println(">>\tEnter PassWord");
        userRegInfo[1] = (scan.nextLine());
        userList.add(UserValidate.createUser.apply(userList,userRegInfo));
        System.out.println("User Registration Successful");
        UiFormat.drawLine.draw();
    }

    private void userLogIn() {
        scan.nextLine();
        String[] userInfo = new String[2];
        System.out.println(">>\tEnter UserName");
        userInfo[0] = (scan.nextLine());
        System.out.println(">>\tEnter PassWord");
        userInfo[1] = (scan.nextLine());
        User loggingUser=UserValidate.getUser.apply(userList,userInfo);
        if (loggingUser==null)
            System.out.println("User name and/or password provided does not match with record");
        else {
            System.out.println("\nLog-in Successful\n");
            succeedLogIn.accept(loggingUser);
        }
    }
    protected static Consumer<User> succeedLogIn= user -> {
        System.out.println("My Profile Summary\nUserID#\tUserName\tFollowing#\tFollower#\tTweets#");
        UiFormat.drawLine.draw();
        user.print();
        UiFormat.startBar.accept(2);
        switch (scan.nextInt()) {
            //Need to re-arrange case numbers according to order of
            // options in EntryMenu and LoggedInMenu enumerations
            case 0:
                System.out.println("Main Menu");
                return;
            case 3:
                TweetValidate.addTweet.accept(user,scan);
                break;
            case 4:
                TweetValidate.viewMyTweets.accept(user,scan);
                break;
            case 5:
                TweetValidate.viewAllTweets.accept(user);
                break;
            case 6:
                UserValidate.showFollowers.accept(user);
                break;
            case 7:
                UserValidate.showFollowings.accept(user);
                break;
            case 8:
                UserValidate.showRecommendation.accept(userList,user);
                break;
            case 9:
                addFollowing(user);
                break;
            default:
                System.out.println("Select valid number from the list");
                break;
        }
    };

    protected static void reactionOnTweet(User user){
        UiFormat.startBar.accept(3);
        int userInput = scan.nextInt();
        switch (userInput) {
            //Need to re-arrange case numbers according to order of
            // options in EntryMenu, LoggedInMenu and TweetMenu enumerations
            case 0:
                System.out.println("Going Back to Profile");
                return;
            case 10:
                reTweet(user);
                break;
            case 11:
                replyOnTweet(user);
                break;
            case 12:
                likeTweet(user);
                break;
            case 13:
                unlikeTweet(user);
                break;
            case 14:
                deleteTweet(user);
                break;
            default:
                System.out.println("Select valid number from the list");
                reactionOnTweet(user);
        }
    }

 //return tweet from tweet-id
    private static Tweet getTweetFromID(User user){
        UiFormat.drawLine.draw();
        System.out.println("Enter tweet ID as listed under column:Tweet#");
        int userInput=scan.nextInt();
        if(userInput==0)
            return null;
        Tweet tweet=TweetValidate.validateTweet.apply(userInput,user);
        if(tweet==null) {
            System.out.println("Invalid tweet ID, type again or enter '0' to go back");
            getTweetFromID(user);
        }
        return tweet;

    }
    private static void reTweet(User user){
        Tweet tweet=getTweetFromID(user);
        if(tweet!=null)
            user.addRetweet(tweet);
    }

    private static void replyOnTweet(User user){
        Tweet tweet=getTweetFromID(user);
        if(tweet!=null)
        {
            scan.nextLine();
            System.out.print("Type here your reply\n>> ");
            String reply = scan.nextLine();
            tweet.addReply(reply,user.getUserId());
        }
        succeedLogIn.accept(user);
    }
    private static void likeTweet(User user){
        Tweet tweet=getTweetFromID(user);
        if(tweet!=null){
            tweet.addLikeOnThisTweet(user.getUserId());
        }
    }
    private static void unlikeTweet(User user){
        Tweet tweet=getTweetFromID(user);
        List<User> userAndItsFollowings = new ArrayList<>();
        userAndItsFollowings.add(user);
        userAndItsFollowings.addAll(user.getFollowings());
        //List<User> userAndItsFollowings = UserValidate.getUserAndItsFollowings.apply(user);
        if(userAndItsFollowings.stream().anyMatch(u -> u.getTweets().contains(tweet))) {
            assert tweet != null;
            tweet.removeLikeOnThisTweet(user.getUserId());
        }
    }
    private static void deleteTweet(User user){
        Tweet tweet=getTweetFromID(user);
        if(user.getTweets().contains(tweet))
            user.getTweets().remove(tweet);
        else
            System.out.println("The tweet either doesn't belong to you or it does not exist");
    }



//    static Consumer<User> viewFollowers=user ->  {
//        UserValidate.showFollowers.accept(user);
//        succeedLogIn.accept(user);
//    };
//
//    static Consumer<User> viewFollowings=user -> {
//        UserValidate.showFollowings.accept(user);
//        succeedLogIn.accept(user);
//    };
//    //get 5 recommendation to follow
//    private void viewRecommendation(User user) {
//        UserValidate.showRecommendation.accept(userList,user);
//        succeedLogIn.accept(user);
//    }

     private static void addFollowing(User user){
        System.out.println("Enter User ID you want to follow:");
        User userToFollow=UserValidate.findByUserId.apply(UserValidate.extractedUserList.apply(userList,user),
                scan.nextInt());
        if(userToFollow!=null)
            user.addFollowing(userToFollow);
        else System.out.println("You are already following or ID not found in record");
        succeedLogIn.accept(user);
    }

    public static void main(String[] args) {
        App twitter = new App();

        try {
            while (true) {
                int initValue = twitter.getStart.apply(start);
                switch (initValue) {
                    case 0:
                        System.out.println("Exiting from App");
                        return;
                    case 1:
                        twitter.userRegister();
                        break;
                    case 2:
                        twitter.userLogIn();
                        break;
                    default:
                        System.out.println("Select valid number from the list");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        } finally {
            scan.close();
        }

    }

}
