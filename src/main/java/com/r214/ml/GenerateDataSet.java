package com.r214.ml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 02/05/2017.
 */
public class GenerateDataSet {

    public static void main(String... args) throws IOException {

        List<String> lines = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            int friends = (int) Math.round(Math.random() * 9000 + 15);
            int subscribers = (int) Math.round(Math.random() * 20000 + 10);
            int audio = (int) Math.round(Math.random() * 5000 + 0);
            int video = (int) Math.round(Math.random() * 2000 + 0);
            int photos = (int) Math.round(Math.random() * 2500 + 1);
            int likesAva = (int) Math.round(Math.random() * 2000 + 0);
            int postByWeek = (int) Math.round(Math.random() * 30 + 0);
            int postByMonthMoreThen50 = (int) Math.round(Math.random());
            int insterestPages = (int) Math.round(Math.random() * 500 + 0);
            int verified = 0;
            int realPhoto = (int) Math.round(Math.random());
            int hasInfo = (int) Math.round(Math.random());
            int name = (int) Math.round(Math.random());
            int gifts = (int) Math.round(Math.random());
            int reposts = (int) Math.round(Math.random());

            String line = friends + " " +
                    subscribers + " " +
                    audio + " " +
                    video + " " +
                    photos + " " +
                    likesAva + " " +
                    postByWeek + " " +
                    postByMonthMoreThen50 + " " +
                    insterestPages + " " +
                    verified + " " +
                    realPhoto + " " +
                    hasInfo + " " +
                    name + " " +
                    gifts + " " +
                    reposts + " ";

            lines.add(line);
        }
        Files.write(Paths.get("dataset.txt"), lines);

    }

}
