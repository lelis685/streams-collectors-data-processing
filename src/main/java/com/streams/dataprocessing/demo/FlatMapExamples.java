package com.streams.dataprocessing.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FlatMapExamples {


    public static void main(String[] args) throws IOException {

        Stream<String> stream1 = Files.lines(Paths.get("src/main/resources/files/TomSawyer_01.txt"));
        Stream<String> stream2 = Files.lines(Paths.get("src/main/resources/files/TomSawyer_02.txt"));
        Stream<String> stream3 = Files.lines(Paths.get("src/main/resources/files/TomSawyer_03.txt"));
        Stream<String> stream4 = Files.lines(Paths.get("src/main/resources/files/TomSawyer_04.txt"));

        Stream<Stream<String>> streamOfStream = Stream.of(stream1, stream2, stream3, stream4);
        Stream<String> streamOfLines = streamOfStream.flatMap(Function.identity());

        Function<String, Stream<String>> lineSplitter =
                line -> Pattern.compile(" ").splitAsStream(line);

        Stream<String> streamOfWords = streamOfLines.flatMap(lineSplitter)
                .map(String::toLowerCase)
                .filter(word -> word.length() == 4)
                .distinct();

        System.out.println(streamOfWords.count());


    }

}
