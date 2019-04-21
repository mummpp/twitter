package users;

import java.util.*;
import java.util.stream.Collectors;

public class test {
    static List<String> strings=new ArrayList<>();

    public static void main(String[] args) {
        strings.add("prakash");
        strings.add("koju");
        System.out.println(strings.toString());
        System.out.println("My anme is "+strings.stream().map(s -> s.length()).collect(Collectors.toList()));
    }
}
