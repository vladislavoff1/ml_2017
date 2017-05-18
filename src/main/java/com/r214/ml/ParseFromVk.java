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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by vlad on 18/05/2017.
 */
public class ParseFromVk {

    private static VkApiClient vk;

    public static void main(String[] args) throws IOException, InterruptedException {
//        org.apache.log4j.BasicConfigurator.configure();

        Scanner scanner = new Scanner(System.in);

        Fann fann = new Fann("ann");

        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient, new Gson());

        String[] fakeIds = new String[]{
                "id164831688",
                "id373487462",
                "id371538274",
                "id409576401",
                "id381441913",
                "id132894634",
                "id177746452",
                "id266582593",
                "id368723012",
                "id208187746",
                "id189514219",
                "id381441913",
                "id414316769",
                "id369032001",
                "id382675946",
                "id266582593",
                "id58028323",
                "id5617796",
                "id69114229",
                "id95675773",
                "id32456690",
                "id92861888",
                "id57611184",
                "id54758226",
                "id93014816",
                "id81725127",
                "id70250013",
                "id54701901",
                "id11605447",
                "id33323790",
                "id53794351",
                "id91686928",
                "id45755333",
                "id51432084",
                "id78230132",
                "id48405641",
                "id48517420",
                "id62828142",
                "id49103696",
                "id71092275",
                "id48551220",
                "id29381995",
                "id62825397",
                "id52899538",
                "id82206740",
                "id58938297",
                "id55673322",
                "id72614763",
                "id15062529",
                "id13137675",
                "id19609561",
                "id67433337",
                "id56415952",
                "id71592930",
                "id43656445",
                "id83676755",
                "id48148168",
                "id40853879",
                "id56895151",
                "id65983666",
                "id71838718",
                "id29008212",
                "id17899503",
                "id7949778",
                "id55505913",
                "id20474226",
                "id7458269",
        };

        String[] notFakeIds = new String[]{
                "enchantinggg",
                "superklassnaya",
                "rvladislavv",
                "a.kaysarow",
                "id26365294",
                "cranberrypiecity",
                "id92145444",
                "id229221655",
                "id48912",
                "id20623773",
                "id54721464",
                "ilya_nikiforov_97",
                "wip_er",
                "she__wolf",
                "alexandrumanskij",
                "acedead",
                "niels.bohr",
                "poolaza",
                "id13331498",
                "vechervchera",
                "freimann230",
                "alinakodoeva",
                "id129427285",
                "bastyuk95",
                "alexandrov_nikitk",
                "id153177739",
                "fmyar",
                "id189183825",
                "vavaevaolivia",
                "id139712586",
                "unlim21",
                "ann__m",
                "e.dvuzhilova98",
                "niki_j09",
                "id22187838",
                "id135520438",
                "nata_ahsat",
                "sweemik",
                "id117465214",
                "id265853287",
                "mgarsia",
                "id123523273",
                "martdn",
                "anyajakovleva",
                "orange_agaric",
                "id3170695",
                "gfi01",
                "lizzzzzi",
                "id5271927",
                "id6860682",
                "id8726015",
                "avontis",
                "koneva18",
                "zlodey1",
                "id11096480",
                "id14818514",
                "batek_andreich",
                "id16723299",
                "dzhonsmitt2015",
                "egorov_egor",
                "naaastya97",
                "alexfoxcub",
                "muravevpavel",
                "godless_catt",
                "id19933555",
                "vlshumakov",
                "myxa011989",
                "sanek1995ky",
                "nataliberdnikova",
                "bukin360",
                "aleksandra_rich",
                "ukc_rem",
                "id29748020",
                "eduardkopylov",
                "id34440549",
                "id35088357",
                "kasha_from_russia",
                "id37215240",
                "id37567578",
                "nillili__mambo",
                "kornifleks",
                "id42408885",
                "cold_depression",
                "by_depresnyak",
                "annadunyad",
                "joker_of_devil",
                "bazils",
                "ruslanoblak",
                "msalicelove",
                "id91784944",
                "plisetskaya97",
                "id96011049",
                "id97682526",
                "pashka9876",
        };

        List<String> lines = new LinkedList<>();

        for (String id : fakeIds) {
            addTrainingRow(true, id, lines);
            TimeUnit.MILLISECONDS.sleep(200);
        }

        for (String id : notFakeIds) {
            addTrainingRow(false, id, lines);
            TimeUnit.MILLISECONDS.sleep(200);
        }

        lines.add(0, lines.size() / 2 + " 9 1");
        Files.write(Paths.get("train.data"), lines);


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

    private static void addTrainingRow(boolean isBot, String id, List<String> list) {
        ServiceActor serviceActor = new ServiceActor(6037039, "VBJdWQZXCJ7To70Jnk7t", "ed947165ed947165ed94716581edc86f4aeed94ed947165b4892094f0a26554ae0f47b3");
        UserActor userActor = new UserActor(81115323, "856c564e39a971e4c764eb82428c9aefd54b6ccb7740485d487da66c47600dddad2b5ffe61b7b7da42eae");

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
                System.out.println("id" + user.getId() + ", " + user.getFirstName() + " " + user.getLastName());

                UserCounters counters = user.getCounters();
                LastSeen lastSeen = user.getLastSeen();

                String friends = "-1";
                String followers = "-1";
                String audios = "-1";
                String videos = "-1";
                String photos = "-1";
                String pages = "-1";
                String gifts = "-1";
                String notes = "-1";
                String groups = "-1";
                String lastSeenTime = "-1";

                if (counters != null) {
                    friends = counters.getFriends() + "";
                    followers = counters.getFollowers() + "";
                    audios = counters.getAudios() + "";
                    videos = counters.getVideos() + "";
                    photos = counters.getPhotos() + "";
                    pages = counters.getPages() + "";
                    gifts = counters.getGifts() + "";
                    notes = counters.getNotes() + "";
                    groups = counters.getGroups() + "";

                    if (friends.equals("null")) {
                        friends = "0";
                    }
                    if (followers.equals("null")) {
                        followers = "0";
                    }
                    if (audios.equals("null")) {
                        audios = "0";
                    }
                    if (videos.equals("null")) {
                        videos = "0";
                    }
                    if (photos.equals("null")) {
                        photos = "0";
                    }
                    if (pages.equals("null")) {
                        pages = "0";
                    }
                    if (gifts.equals("null")) {
                        gifts = "0";
                    }
                    if (notes.equals("null")) {
                        notes = "0";
                    }
                    if (groups.equals("null")) {
                        groups = "0";
                    }
                }

                if (lastSeen != null) {
                    lastSeenTime = lastSeen.getTime() + "";

                    if (lastSeenTime.equals("null")) {
                        lastSeenTime = "0";
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
                                    "    groups: " + groups + ", " + '\n' +
                                    "    lastSeen: " + lastSeenTime + ", " + '\n'
                    );

                    String params = String.join(" ", friends, followers, audios, videos, photos, pages, gifts, notes, groups);
                    list.add(params);
                    list.add(isBot ? "1" : "0");

                }
            }
        }
    }

    private static String getAction(float[] out) {
        return (out[0] < 0.5f ? "не фейк" : "фейк") + " (вероятность " + Math.round((out[0] < 0.5f ? 1 - out[0] : out[0]) * 100.f) + "%)";
    }

}
