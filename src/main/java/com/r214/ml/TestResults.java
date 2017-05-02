package com.r214.ml;

import com.googlecode.fannj.Fann;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by vlad on 02/05/2017.
 */
public class TestResults {

    public static Fann fann = new Fann("ann");

    public static void main(String... args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("dataset.txt"))) {
            List<String> lines = stream
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(
                            line -> Arrays.stream(line.split(" "))
                                    .map(String::trim)
                                    .filter(s -> !s.isEmpty())
                                    .map(Float::parseFloat)
                                    .toArray(Float[]::new)
                    )
                    .map(TestResults::test)
                    .collect(Collectors.toList());

            Files.write(Paths.get("results.txt"), lines);
        }
    }

    static String test(Float... args) {
        float[] arr = new float[15];

        for (int i = 0; i < args.length; i++) {
            arr[i] = args[i];
        }
        return getAction(fann.run(arr)[0]);
    }

    private static String getAction(float out) {
        return (out < 0.5f ? "не фейк" : "фейк") + " (вероятность " + Math.round((out < 0.5f ? 1 - out : out) * 100.f) + "%)";
    }
}
