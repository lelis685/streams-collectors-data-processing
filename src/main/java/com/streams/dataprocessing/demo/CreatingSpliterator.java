package com.streams.dataprocessing.demo;


import com.streams.dataprocessing.demo.model.Person;
import com.streams.dataprocessing.demo.spliterator.PersonSpliterator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CreatingSpliterator {

    public static void main(String[] args) {

        Path path = Paths.get("src/main/resources/files/people.txt");

        try (Stream<String> lines = Files.lines(path)) {

			Spliterator<String> lineSpliterator = lines.spliterator();
			Spliterator<Person> peopleSpliterator = new PersonSpliterator(lineSpliterator);

			Stream<Person> people = StreamSupport.stream(peopleSpliterator, false);
			people.forEach(System.out::println);

		} catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
