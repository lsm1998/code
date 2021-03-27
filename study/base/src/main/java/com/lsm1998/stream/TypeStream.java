package com.lsm1998.stream;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TypeStream
{
    public static void main(String[] args)
    {
        Stream<Integer> s1 = Stream.of(1, 2, 3, 4, 5, 6);
        Stream<String> s2 = s1.map(Object::toString);

        System.out.println(s1);
        System.out.println(s2);

        Optional<Integer> max = Stream.of(1, 2, 3, 4, 5, 6).
                map(x -> x * x).
                filter(x -> x < 20).
                reduce(Math::max);
        max.ifPresent(System.out::println);

        Stream<Character> s3 = Stream.of("my", "mono").flatMap(s -> s.chars().mapToObj(i -> (char) i));
        s3.forEach(System.out::println);

        Random r = new Random();
        List<Integer> list = IntStream.
                range(0, 100_000_000).
                map(x -> r.nextInt()).
                boxed().
                collect(Collectors.toList());

        Optional<Integer> maxInt = list.stream().max(Integer::compareTo);
        maxInt.ifPresent(System.out::println);
    }
}
