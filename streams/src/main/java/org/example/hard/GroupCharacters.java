package org.example.hard;

import org.example.hard.dto.CharCategory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupCharacters {
    public static void main(String[] args) {
        List<Character> chars = Arrays.asList(
                'A', 'b', '3', 'Z', 'x', '#', '7', 'm', '@');
        Map<Object, List<Character>> categoryChars = chars
                .stream()
                .collect(Collectors.groupingBy(
                        GroupCharacters::getCharCategory
        ));
        System.out.println(categoryChars);
    }

    private static Object getCharCategory(char c){
        if(Character.isUpperCase(c)) return CharCategory.UPPERCASE;
        else if(Character.isLowerCase(c)) return  CharCategory.LOWERCASE;
        else if(Character.isDigit(c)) return CharCategory.DIGIT;
        else return CharCategory.OTHERS;
    }
}
