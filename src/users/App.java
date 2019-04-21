package users;

import java.util.*;
import java.util.stream.Collectors;


public class App {
    static Scanner scan;
    private List<User> userList;

    public App(){
        userList=new ArrayList<>();
        System.out.println("---------Welcome to MUM.TWITTER-----------");
        scan=new Scanner(System.in);

    }
    private int getStart(){
        System.out.println("-------------------------------------------");
        System.out.println("Enter a number as indicated below:\n" +
                "Number\tAction\n-----+-------------------------");
        mainMenuList();
        return scan.nextInt();
    }
    private void mainMenuList(){
        int i=0;
        for(EntryMenu em: EntryMenu.values()) {
            System.out.println(Integer.toString(i++)+" >>\t"+em);
        }
        System.out.println("-------------------------------------------\n>>");
    }

    private void loggedInMenuList(){
        int i=EntryMenu.values().length-1;
        for(LoggedInMenu lm: LoggedInMenu.values()) {
            System.out.println(Integer.toString((i==EntryMenu.values().length-1)?0:i)+" >>\t"+lm);
            i++;
        }
        System.out.println("-------------------------------------------\n>>");
    }
    private boolean checkIfUserNameAlreadyExists(String userName){
        return userList.stream().anyMatch(user -> user.getUserName().equals(userName));
    }

    private boolean checkIfUserExists(String userName, String userPassword){
        return checkIfUserNameAlreadyExists(userName) && userList.stream()
                .anyMatch(user -> user.getPassword().equals(userPassword));
    }

    private User getUser(String userName, String userPassword){
        return checkIfUserExists(userName,userPassword)?userList.stream()
                .filter(user -> user.getUserName().equals(userName) && user.getPassword().equals(userPassword))
                .collect(Collectors.toList()).get(0):new User("temp","temp",0);


    }

    private /*List<String>*/ void userRegister(){
        String[] userRegInfo=new String[2];
        scan.nextLine();
        System.out.println(">>\tEnter UserName");
        userRegInfo[0]=(scan.nextLine());

        //check if the username is already taken
        while (checkIfUserNameAlreadyExists(userRegInfo[0])){
            System.out.println("User name already exists.\nEnter new User Name\n" +
                    "(or enter 'x' to return to main menu\n");
            userRegInfo[0]=(scan.nextLine());
//            if(Integer.parseInt(userRegInfo[0])==120)
//                return;
        }
        System.out.println(">>\tEnter PassWord");
        userRegInfo[1]=(scan.nextLine());
        //return userRegInfo;
        userList.add(new User(userRegInfo[0],userRegInfo[1],userList.size()+1));
        System.out.println("User Registration Successful\n----------------------------");
    }

    private void userLogIn(){
        scan.nextLine();
        System.out.println(">>\tEnter UserName");
        String userName=(scan.nextLine());
        System.out.println(">>\tEnter PassWord");
        String userPassword=(scan.nextLine());
        if(!checkIfUserExists(userName,userPassword)){
            System.out.println("User name and/or password provided does not match with record");
        }
        else {
            System.out.println("\nLog-in Successful\n");
            succeedLogIn(getUser(userName,userPassword));
        }
    }

    private void succeedLogIn(User user){
        System.out.println("UserName\tFollowing#\tFollower#\tTweets#" +
                "\n------+-------------------------------------");
        System.out.println(user.getUserName()+"\t\t"+user.getFollowings().size()+"\t\t\t"+
                user.getFollowers().size()+"\t\t\t"+user.getTweets().size()+
                "\n--------------------------------------------");
        System.out.println("Enter a number as indicated below:\n" +
                "Number\tAction\n------+----------------------------");
        loggedInMenuList();
        int userInput=scan.nextInt();
        switch (userInput){
            //Need to re-arrange case numbers according to order of
            // options in 'LoggedInMenu' class and loggedInMenuList() method
            case 0:System.out.println("Main Menu");return;
            case 3:addTweet(user);break;
            case 4:viewMyTweets(user);
            case 5:viewAllTweets(user);break;
            case 6:viewFollowers(user);break;
            case 7:viewFollowings(user);break;
            case 8:viewRecommendation(user);break;
            default:System.out.println("Select valid number from the list");succeedLogIn(user);
        }
    }
    private void addTweet(User user){
        scan.nextLine();
        System.out.print("Type your tweet here:\t");
        String newTweet=scan.nextLine();
        System.out.println(String.format("Your new tweet, \"%s\" is added",newTweet));
        user.addTweet(new Tweet(newTweet));
        succeedLogIn(user);
    }
    private void viewMyTweets(User user){}
    private void viewAllTweets(User user){}
    private void viewFollowers(User user){}
    private void viewFollowings(User user){}
    private void viewRecommendation(User user){}

    public static void main(String[] args) {
        App twitter=new App();
        try {while (true) {
            int initValue = twitter.getStart();
            switch (initValue) {
                case 0:System.out.println("Exiting from App");return;
                case 1:twitter.userRegister();break;
                case 2:twitter.userLogIn();break;
                default:System.out.println("Select valid number from the list");break;
            }

        }
        }
        catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            scan.close();
        }
    }

}
