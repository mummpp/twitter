package users;

import jdk.dynalink.beans.StaticClass;

import java.lang.invoke.SwitchPoint;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

class UiFormat {

    static Simple welcome=()->System.out.println("-------------Welcome to MUM.TWITTER---------------");
    static Simple drawLine =()->System.out.println("--------------------------------------------------");
    static Simple drawTweetTable=()->{
            drawLine.draw();
            System.out.println("Date#\t\tTweet#\tTweet\tComments#\tLikes#");
            drawLine.draw();
    };
    static Simple drawProfileTable=()->{
        drawLine.draw();
        System.out.println("UserID#\tUserName\tFollowing#\tFollower#\tTweets#");
        drawLine.draw();

    };

    static Consumer<Integer> startBar=(x)->{
        drawLine.draw();
        System.out.println("Enter a number as indicated below:\nNumber\tAction");
        drawLine.draw();
        switch (x){
            case 1:entryMenuList();break;
            case 2:logInMenuList();break;
            case 3:tweetMenuList();break;
        }
    };
    static void entryMenuList() {
        int i = 0;
        for (EntryMenu em : EntryMenu.values()) {
            System.out.println(Integer.toString(i++) + " >>\t" + em);
        }
        drawLine.draw();
        System.out.print(">>");
    }
    static void logInMenuList() {
        int startIndex=EntryMenu.values().length - 1;
        int i = startIndex;
        for (LoggedInMenu lm : LoggedInMenu.values()) {
            System.out.println(Integer.toString((i == startIndex) ? 0: i) + " >>\t" + lm);
            i++;
        }
        drawLine.draw();
        System.out.print(">>");
    }
    static void tweetMenuList() {
        int startIndex=EntryMenu.values().length + LoggedInMenu.values().length- 2;
        int i = startIndex;
        for (TweetMenu tm : TweetMenu.values()) {
            System.out.println(Integer.toString((i == startIndex) ? 0 : i) + " >>\t" + tm);
            i++;
        }
        drawLine.draw();
        System.out.print(">>");
    }


}
