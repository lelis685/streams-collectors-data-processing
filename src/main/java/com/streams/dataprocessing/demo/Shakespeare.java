package com.streams.dataprocessing.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Shakespeare {

    public static void main(String[] args) throws IOException {

        Set<String> shakespeareWords =
                Files.lines(Paths.get("src/main/resources/files/words.shakespeare.txt"))
                        .map(word -> word.toLowerCase())
                        .collect(Collectors.toSet());

        Set<String> scrabbleWords =
                Files.lines(Paths.get("src/main/resources/files/ospd.txt"))
                        .map(word -> word.toLowerCase())
                        .collect(Collectors.toSet());


        final int [] scrabbleENScore = {
                // a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,  q, r, s, t, u, v, w, x, y,  z
                   1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10} ;


        Function<String, Integer> score =
                word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();

        ToIntFunction<String> scoreint =
                word -> word.chars()
                        .map(letter -> scrabbleENScore[letter - 'a'])
                        .sum();

        System.out.println("score of hello " + scoreint.applyAsInt("hello"));

        String bestWord = shakespeareWords.stream()
                .filter(scrabbleWords::contains)
                .max(Comparator.comparing(score))
                .get();

        System.out.println("Best word: " + bestWord);

        IntSummaryStatistics intSummaryStatistics = shakespeareWords.stream()
                .filter(scrabbleWords::contains)
                .mapToInt(scoreint)
                .summaryStatistics();

        System.out.println("stats: " + intSummaryStatistics);


    }

}
