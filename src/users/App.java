package users;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;


public class App {
    private static Scanner scan;
    private List<User> userList;

    App() {
        userList = new ArrayList<>();
        System.out.println("-------------Welcome to MUM.TWITTER---------------");
        scan = new Scanner(System.in);

    }
    private void viewTweetBar() {
        drawLine();
        System.out.println("Date#\t\tTweet#\tTweet\tComments#\tLikes#");
        drawLine();
    }

    private int getStart() {
        drawLine();
        System.out.println("Enter a number as indicated below:\n" +
                "Number\tAction");
        drawLine();
        entryMenuList();
        return scan.nextInt();
    }

    private boolean checkIfUserNameAlreadyExists(String userName) {
        return userList.stream().anyMatch(user -> user.getUserName().equals(userName));
    }

    private boolean checkIfUserExists(String userName, String userPassword) {
        return checkIfUserNameAlreadyExists(userName) && userList.stream()
                .anyMatch(user -> user.getPassword().equals(userPassword));
    }

    private User getUser(String userName, String userPassword) {
        return checkIfUserExists(userName, userPassword) ? userList.stream()
                .filter(user -> user.getUserName().equals(userName) && user.getPassword().equals(userPassword))
                .collect(Collectors.toList()).get(0) : new User("temp", "temp", 0);


    }

    private /*List<String>*/ void userRegister() {
        String[] userRegInfo = new String[2];
        scan.nextLine();
        System.out.println(">>\tEnter UserName");
        userRegInfo[0] = (scan.nextLine());

        //check if the username is already taken
        while (new UserValidate(userList).test(userRegInfo[0])) {
            System.out.println("User name already exists.\nEnter new User Name\n" +
                    "(or enter 'x' to return to main menu\n");
            userRegInfo[0] = (scan.nextLine());
        }
        System.out.println(">>\tEnter PassWord");
        userRegInfo[1] = (scan.nextLine());
        //return userRegInfo;
        userList.add(new User(userRegInfo[0], userRegInfo[1], userList.size() + 1));
        System.out.println("User Registration Successful");
        drawLine();
    }

    private void userLogIn() {
        scan.nextLine();
        System.out.println(">>\tEnter UserName");
        String userName = (scan.nextLine());
        System.out.println(">>\tEnter PassWord");
        String userPassword = (scan.nextLine());
        if (!new UserValidate(userList).test(userName,userPassword))//(!checkIfUserExists(userName, userPassword)) {
            System.out.println("User name and/or password provided does not match with record");
        else {
            System.out.println("\nLog-in Successful\n");
            succeedLogIn(getUser(userName, userPassword));
        }
    }

    private void succeedLogIn(User user) {
        System.out.println("My Profile Summary\nUserID#\tUserName\tFollowing#\tFollower#\tTweets#");
        drawLine();
        user.print();
//        System.out.println(user.getUserId()+"\t"+user.getUserName() + "\t\t" + user.getFollowings().size() + "\t\t\t" +
//                user.getFollowers().size() + "\t\t\t" + user.getTweets().size());
        drawLine();
        System.out.println("Enter a number as indicated below:\nNumber\tAction");
        drawLine();
        logInMenuList();
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
        System.out.println(String.format("Your new tweet, \"%s\" is added", newTweet));
        user.addTweet(new Tweet(newTweet));
        succeedLogIn(user);

    }

    private void viewMyTweets(User user) {
        viewTweetBar();
        user.getTweets().stream()
                .sorted(Comparator.comparing(Tweet::getDate).reversed())
                .forEach(Tweet::print);
        reactionOnTweet(user);
        succeedLogIn(user);

    }
    private void reactionOnTweet(User user){
        drawLine();
        System.out.println("Your action on any of above tweet(s):\nNumber\tAction");
        tweetMenuList();
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
    Function<User,List<User>> getUserAndItsFollowings=user -> {
        List<User> userAndItsFollowings = new ArrayList<>();
        userAndItsFollowings.add(user);
        userAndItsFollowings.addAll(user.getFollowings());
        return userAndItsFollowings;

    };

    private BiFunction<Integer, User, Tweet> validateTweet=(tweetID, user)->{
        List<User> userAndItsFollowings = getUserAndItsFollowings.apply(user);
        return userAndItsFollowings.stream()
                .flatMap(u -> u.getTweets().stream())
                .filter(tweet -> tweet.getTweetId()==tweetID)
                .collect(Collectors.toList()).get(0);
    };

    //return tweet from tweet-id
    private Tweet getTweetFromID(User user){
        drawLine();
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
    }
    private void likeTweet(User user){
        Tweet tweet=getTweetFromID(user);
        if(tweet!=null){
            tweet.addLikeOnThisTweet(user.getUserId());
        }
    }
    private void unlikeTweet(User user){
        Tweet tweet=getTweetFromID(user);
        List<User> userAndItsFollowings = getUserAndItsFollowings.apply(user);
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
        //userAndItsFollowings.forEach(this::viewMyTweets);
        viewTweetBar();
        userAndItsFollowings.stream()
                .flatMap(u -> u.getTweets().stream())
                .sorted(Comparator.comparing(Tweet::getDate).reversed())
                .forEach(Tweet::print);
        reactionOnTweet(user);
        succeedLogIn(user);
    }

    private void viewFollowers(User user) {
        System.out.println("List of Followers:");
        System.out.println("Profile Summary\nUserID#\tUserName\tFollowing#\tFollower#\tTweets#");
        drawLine();
        user.getFollowers().forEach(User::print);
        drawLine();
        succeedLogIn(user);
    }

    private void viewFollowings(User user) {
        System.out.println("List of Followings:");
        System.out.println("Profile Summary\nUserID#\tUserName\tFollowing#\tFollower#\tTweets#");
        drawLine();
        user.getFollowings().forEach(User::print);
        drawLine();
        succeedLogIn(user);
    }
    //get 5 recommendation to follow
    private void viewRecommendation(User user) {
        System.out.println("Some Recommendations for you to follow:");
        drawLine();
        System.out.println("UserID#\tUserName\tFollowing#\tFollower#\tTweets#");
        drawLine();
        userList.stream()
                .filter(user1 -> !user.equals(user1)&&!user.getFollowings().contains(user1))
                .distinct()
                .limit(5)
                .forEach(User::print);
        drawLine();
        succeedLogIn(user);
    }

    private void addFollowing(User user){
        System.out.println("Enter User ID you want to follow:");
        int id=scan.nextInt();
        System.out.println("You entered "+id);
        User userToFollow=findByUserId(extractedUserList.apply(userList,user),id);
        if(userToFollow!=null)
            user.addFollowing(userToFollow);
        else System.out.println("User ID not found in record");
        succeedLogIn(user);
    }

    //Get list of following-users and follower-users
    BiFunction<List<User>, User, List<User>> extractedUserList=(users, user) ->{
        return users.stream().filter(user1 -> !user.equals(user1)&&!user.getFollowings().contains(user1))
                .distinct().collect(Collectors.toList());
    };

    private User findByUserId(List<User> users, int id) {
        return users
                .stream()
                .reduce(null, (accum, user) -> user.getUserId() == id ? user : accum);
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

        protected void drawLine() {
            System.out.println("--------------------------------------------------");
        }


        protected void entryMenuList() {
            drawLine();
            int i = 0;
            for (EntryMenu em : EntryMenu.values()) {
                System.out.println(Integer.toString(i++) + " >>\t" + em);
            }
            drawLine();
            System.out.print(">>");
        }


        protected void logInMenuList() {
            drawLine();
            int startIndex=EntryMenu.values().length - 1;
            int i = startIndex;
            for (LoggedInMenu lm : LoggedInMenu.values()) {
                System.out.println(Integer.toString((i == startIndex) ? 0: i) + " >>\t" + lm);
                i++;
            }
            drawLine();
            System.out.print(">>");
        }

        protected void tweetMenuList() {
            drawLine();
            int startIndex=EntryMenu.values().length + LoggedInMenu.values().length- 2;
            int i = startIndex;
            for (TweetMenu tm : TweetMenu.values()) {
                System.out.println(Integer.toString((i == startIndex) ? 0 : i) + " >>\t" + tm);
                i++;
            }
            drawLine();
            System.out.print(">>");
        }
}
