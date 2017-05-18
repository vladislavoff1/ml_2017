package com.r214.ml;

import com.google.gson.Gson;
import com.googlecode.fannj.Fann;
import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.LastSeen;
import com.vk.api.sdk.objects.users.UserCounters;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;

import java.util.List;
import java.util.Scanner;

/**
 * Created by vlad on 18/05/2017.
 */
public class TestVkUser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Fann fann = new Fann("ann");

        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient, new Gson());

        ServiceActor serviceActor = new ServiceActor(6037039, "VBJdWQZXCJ7To70Jnk7t", "ed947165ed947165ed94716581edc86f4aeed94ed947165b4892094f0a26554ae0f47b3");
        UserActor userActor = new UserActor(81115323, "856c564e39a971e4c764eb82428c9aefd54b6ccb7740485d487da66c47600dddad2b5ffe61b7b7da42eae");

        System.out.print("User id: ");
//
        String id = scanner.nextLine();

        List<UserXtrCounters> users = null;
        try {
            users = vk.users().get(userActor)
                    .userIds(id)
                    .fields(UserField.COUNTERS, UserField.LAST_SEEN)
                    .lang(Lang.EN)
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }

        if (users != null) {
            for (UserXtrCounters user : users) {
                System.out.println(user.getFirstName() + " " + user.getLastName());

                UserCounters counters = user.getCounters();
                LastSeen lastSeen = user.getLastSeen();

                float friends = 0;
                float followers = 0;
                float audios = 0;
                float videos = 0;
                float photos = 0;
                float pages = 0;
                float gifts = 0;
                float notes = 0;
                float groups = 0;
                float lastSeenTime = 0;

                if (counters != null) {
                    Integer friendsInt = counters.getFriends();
                    Integer followersInt = counters.getFollowers();
                    Integer audiosInt = counters.getAudios();
                    Integer videosInt = counters.getVideos();
                    Integer photosInt = counters.getPhotos();
                    Integer pagesInt = counters.getPages();
                    Integer giftsInt = counters.getGifts();
                    Integer notesInt = counters.getNotes();
                    Integer groupsInt = counters.getGroups();


                    if (friendsInt != null) {
                        friends = friendsInt.floatValue();
                    }
                    if (followersInt != null) {
                        followers = followersInt.floatValue();
                    }
                    if (audiosInt != null) {
                        audios = audiosInt.floatValue();
                    }
                    if (videosInt != null) {
                        videos = videosInt.floatValue();
                    }
                    if (photosInt != null) {
                        photos = photosInt.floatValue();
                    }
                    if (pagesInt != null) {
                        pages = pagesInt.floatValue();
                    }
                    if (giftsInt != null) {
                        gifts = giftsInt.floatValue();
                    }
                    if (notesInt != null) {
                        notes = notesInt.floatValue();
                    }
                    if (groupsInt != null) {
                        groups = groupsInt.floatValue();
                    }
                }

                if (lastSeen != null) {
                    Integer lastSeenTimeInt = lastSeen.getTime();
                    if (lastSeenTimeInt != null) {
                        lastSeenTime = lastSeenTimeInt.floatValue();
                    }
                }

                if (counters == null && lastSeen == null) {
                    System.out.println("    blocked" + '\n');
                } else {

                    System.out.println(
                            "    friends: " + friends + ", " + '\n' +
                                    "    followers: " + followers + ", " + '\n' +
                                    "    audios: " + audios + ", " + '\n' +
                                    "    videos: " + videos + ", " + '\n' +
                                    "    photos: " + photos + ", " + '\n' +
                                    "    pages: " + pages + ", " + '\n' +
                                    "    gifts: " + gifts + ", " + '\n' +
                                    "    notes: " + notes + ", " + '\n' +
                                    "    groups: " + groups + ", " + '\n'
//                                    "    lastSeen: " + lastSeenTime + ", " + '\n'
                    );

                    float[] res = fann.run(new float[]{friends, followers, audios, videos, photos, pages, gifts, notes, groups, lastSeenTime});
                    System.out.println(getAction(res));
                }
            }
        }

//        String[] titles = {"друзья", "подписчики", "аудиозаписи", "видеозаписи", "фото", "лайков на аве", "постов за неделю", "постов за месяц >50?", "интересные страницы", "галочка", "картинка(0) или фото(1)", "личная инфо (работа, город, учеба)", "имя", "подарки", "репосты"};
//        float[] test = new float[15];
//
//        for (int i = 0; i < titles.length; i++) {
//            System.out.print(titles[i] + ": ");
//
//            String input = scanner.nextLine();
//            test[i] = Float.parseFloat(input);
//        }
//        System.out.println("Результат: " + getAction(fann.run(test)));
    }

    private static String getAction(float[] out) {
        return (out[0] < 0.5f ? "не фейк" : "фейк") + " (вероятность " + Math.round((out[0] < 0.5f ? 1 - out[0] : out[0]) * 100.f) + "%)";
    }

}
