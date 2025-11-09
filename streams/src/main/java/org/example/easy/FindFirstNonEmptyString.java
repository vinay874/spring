package org.example.easy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FindFirstNonEmptyString {
    public static void main(String[] args) {

        List<String> strings = Arrays.asList("", "", "Hello", "world", "");

        Optional<String> nonEmptyStr = strings.stream().filter(st -> !st.isEmpty()).findFirst();

        System.out.println(nonEmptyStr);
    }
}
