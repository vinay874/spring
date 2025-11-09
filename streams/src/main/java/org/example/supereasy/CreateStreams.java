package org.example.supereasy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CreateStreams {
    public static void main(String[] args) {
        // 1. List -> stream
        List<String> names = Arrays.asList("alice", "bob");
        Stream<String> steam = names.stream();

        // 2. Array -> stream
        String[] aNames = new String[]{"alice", "bob"};
        Stream<String> aStream = Arrays.stream(aNames);

        //3. Stream of
        Stream<Integer> intStream = Stream.of(1, 2, 3, 4);

        //4. Stream generate
        Stream<Double> randomStream = Stream.generate(Math::random).limit(100);
    }
}
