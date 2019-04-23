package users;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


public class App {

    private static Scanner scan;
    private static List<User> userList;

    App() {
        userList = new ArrayList<>();
        UiFormat.welcome.draw();
        scan = new Scanner(System.in);

    }

    public static List<User> getUserList() {
        return userList;
    }

    private int getStart() {
        UiFormat.startBar.accept(1);
        //entryMenuList();
        return scan.nextInt();
    }

    private /*List<String>*/ void userRegister() {
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
            succeedLogIn(loggingUser);
        }
    }

    private void succeedLogIn(User user) {
        System.out.println("My Profile Summary\nUserID#\tUserName\tFollowing#\tFollower#\tTweets#");
        UiFormat.drawLine.draw();
        user.print();
//        drawLine();
//        System.out.println("Enter a number as indicated below:\nNumber\tAction");
//        drawLine();
//        logInMenuList();
        UiFormat.startBar.accept(2);
        int userInput = scan.nextInt();
        switch (userInput) {
            //Need to re-arrange case numbers according to order of
            // options in EntryMenu and LoggedInMenu enumerations
            case 0:
                System.out.println("Main Menu");
                return;
            case 3:
                addTweet(user);
                break;
            case 4:
                viewMyTweets(user);
                break;
            case 5:
                viewAllTweets(user);
                break;
            case 6:
                viewFollowers(user);
                break;
            case 7:
                viewFollowings(user);
                break;
            case 8:
                viewRecommendation(user);
                break;
            case 9:
                addFollowing(user);
                break;
            default:
                System.out.println("Select valid number from the list");
                succeedLogIn(user);
        }
    }

    private void addTweet(User user) {
        scan.nextLine();
        System.out.print("Type your tweet here:\t");
        String newTweet = scan.nextLine();
        user.addTweet(new Tweet(newTweet));
        System.out.println(String.format("Your new tweet, \"%s\" is added", newTweet));
        succeedLogIn(user);

    }

    private void viewMyTweets(User user) {
        TweetValidate.viewTweets.accept(user);
        reactionOnTweet(user);
        succeedLogIn(user);

    }
    private void reactionOnTweet(User user){
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


    private BiFunction<Integer, User, Tweet> validateTweet=(tweetID, user)->{
        //List<User> userAndItsFollowings = UserValidate.getUserAndItsFollowings.apply(user);
        List<User> userAndItsFollowings = new ArrayList<>();
        userAndItsFollowings.add(user);
        userAndItsFollowings.addAll(user.getFollowings());
        return userAndItsFollowings.stream()
                .flatMap(u -> u.getTweets().stream())
                .filter(tweet -> tweet.getTweetId()==tweetID)
                .collect(Collectors.toList()).get(0);
    };

    //return tweet from tweet-id
    private Tweet getTweetFromID(User user){
        UiFormat.drawLine.draw();
        System.out.println("Enter tweet ID as listed under column:Tweet#");
        int userInput=scan.nextInt();
        if(userInput==0)
            return null;
        Tweet tweet=validateTweet.apply(userInput,user);
        if(tweet==null) {
            System.out.println("Invalid tweet ID, type again or enter '0' to go back");
            getTweetFromID(user);
        }
        return tweet;

    }
    private void reTweet(User user){
        Tweet tweet=getTweetFromID(user);
        if(tweet!=null)
            user.addRetweet(tweet);
    }


    private void replyOnTweet(User user){
        Tweet tweet=getTweetFromID(user);
        if(tweet!=null)
        {
            scan.nextLine();
            System.out.print("Type here your reply\n>> ");
            String reply = scan.nextLine();
            tweet.addReply(reply,user.getUserId());
        }
        succeedLogIn(user);
    }
    private void likeTweet(User user){
        Tweet tweet=getTweetFromID(user);
        if(tweet!=null){
            tweet.addLikeOnThisTweet(user.getUserId());
        }
    }
    private void unlikeTweet(User user){
        Tweet tweet=getTweetFromID(user);
        List<User> userAndItsFollowings = new ArrayList<>();
        userAndItsFollowings.add(user);
        userAndItsFollowings.addAll(user.getFollowings());
        //List<User> userAndItsFollowings = UserValidate.getUserAndItsFollowings.apply(user);
        if(userAndItsFollowings.stream().filter(u -> u.getTweets().contains(tweet)).count()>0)
                tweet.removeLikeOnThisTweet(user.getUserId());
    }
    private void deleteTweet(User user){
        Tweet tweet=getTweetFromID(user);
        if(user.getTweets().contains(tweet))
            user.getTweets().remove(tweet);
        else
            System.out.println("The tweet either doesn't belong to you or it does not exist");
    }

    private void viewAllTweets(User user) {
        List<User> userAndItsFollowings = new ArrayList<>();
        userAndItsFollowings.add(user);
        userAndItsFollowings.addAll(user.getFollowings());
        TweetValidate.viewAllTweets.accept(userAndItsFollowings);
        reactionOnTweet(user);
        succeedLogIn(user);
    }

    private void viewFollowers(User user) {
        UserValidate.showFollowers.accept(user);
        succeedLogIn(user);
    }

    private void viewFollowings(User user) {
        UserValidate.showFollowings.accept(user);
        succeedLogIn(user);
    }
    //get 5 recommendation to follow
    private void viewRecommendation(User user) {
        UserValidate.showRecommendation.accept(userList,user);
        succeedLogIn(user);
    }

    private void addFollowing(User user){
        System.out.println("Enter User ID you want to follow:");
        int id=scan.nextInt();
        System.out.println("You entered "+id);
        User userToFollow=UserValidate.findByUserId.apply(UserValidate.extractedUserList.apply(userList,user),id);
        if(userToFollow!=null)
            user.addFollowing(userToFollow);
        else System.out.println("You are already following or ID not found in record");
        succeedLogIn(user);
    }

    public static void main(String[] args) {
        App twitter = new App();

        try {
            while (true) {
                int initValue = twitter.getStart();
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
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        } finally {
            scan.close();
        }

    }

}
