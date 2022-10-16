package vn.tapbi.youtubeplayer3.common;

import java.util.ArrayList;
import java.util.List;

public class Constant {

    /* key api */
//    public static  String API = "AIzaSyCoVRVeURj-q8336ILoqdUj4dukH5YxM-s";
    public static  String API = "AIzaSyAqLTS8_2lvEz7Y6vtDrsl1modOmWIBJ7o";
//    public static  String API = "AIzaSyDy6HLgFCIFNBPfqxffuMotLYNrinN3-T0";

    /* shared preferences */
    public static final String NOTIFICATION = "state_notification";
    public static final String PLAY_BACKGROUND = "play_background";
    public static final String LANGUAGE = "language";

    /* notification */
    public static final String CHANNEL_ID = "CHANNEL_ID";

    /* language */
    public static final String LANGUAGE_VIETNAMESE = "vi";
    public static final String LANGUAGE_ENGLISH = "en";

    /* save state data when kill app */
    public static final String VIDEO = "videoId";
    public static final String ITEM_VIDEO = "videoId";

    public static List<String> setListApi(){
        List<String>listKey = new ArrayList<>();
//        list.add("AIzaSyA-hKJt7rFUrQr_EWLqGrS4fI7pTVVoCOo");
//        list.add("AIzaSyCoVRVeURj-q8336ILoqdUj4dukH5YxM-s");
        listKey.add("AIzaSyDqFswHZLy38xeVqe9YJoNqtEbJd43R8FM");
        listKey.add("AIzaSyBMkb33ZztjZTLsIAe9xlrHN9B2gWshd70");
        listKey.add("AIzaSyA7gMYoJ_bW5e1eiCHRd5k6fHNDXjZsLxE");
        listKey.add("AIzaSyBxmDawRESzhYHGJTjGSbIfSBOCkYLJS14");
        listKey.add("AIzaSyDQCg-t6HsSFyASrxZjJlsWAUFTaWkqh_U");
        listKey.add("AIzaSyDxrmC-mqq1dfVOFg_4oqCObuE3qUXE1RM");
        listKey.add("AIzaSyCpRxcTUjy8xSfbN2ThrxA-qs9IOWGq_5g");
        listKey.add("AIzaSyCijrHu2hK_doJk0PkN09vmt1kcKhaFu7U");
        listKey.add("AIzaSyC763lwWhSf0yWCalkk1fREW1rM5pKLQWM");
        listKey.add("AIzaSyAycjpTgl0dllejFmLHqhnkezF3yh227H4");
        listKey.add("AIzaSyCPlzFjO7SU4aweNpXbi0RxN3MeNtrEMcg");
        listKey.add("AIzaSyAwGuEJE7wy0lyzj63BHdfKEt5DcMaIBQc");
        listKey.add("AIzaSyBxRbXaD_GKO9RNBJhlXnseo9zKMIo84Wo");
        listKey.add("AIzaSyCPlzFjO7SU4aweNpXbi0RxN3MeNtrEMcg");
        listKey.add("AIzaSyC7WUPgWXfgLC6IMWLg4Q5Qz5m-mwEs1C4");
        listKey.add("AIzaSyBls28HhfgzWD_YRkX-Zk024vOmUVE_OZo");

        return listKey;
    }

    private static final String link = "https://youtube.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&key=AIzaSyAqLTS8_2lvEz7Y6vtDrsl1modOmWIBJ7o";
    private static final String LINK_maxResults = "https://youtube.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&maxResults=30&key=AIzaSyAqLTS8_2lvEz7Y6vtDrsl1modOmWIBJ7o";
    private static final String LINK_statistic = "https://youtube.googleapis.com/youtube/v3/videos?part=statistics&chart=mostPopular&maxResults=30&key=AIzaSyAqLTS8_2lvEz7Y6vtDrsl1modOmWIBJ7o";
    private static final String LINK_COUNT_VIDEO = "https://youtube.googleapis.com/youtube/v3/videos?part=statistics&chart=mostPopular&maxResults=30&key=AIzaSyAqLTS8_2lvEz7Y6vtDrsl1modOmWIBJ7o";
    private static final String LINK_COMMENTS = "https://www.googleapis.com/youtube/v3/commentThreads?key=AIzaSyAqLTS8_2lvEz7Y6vtDrsl1modOmWIBJ7o&textFormat=plainText&part=snippet&videoId=tcat9CPiAZ4&maxResults=300";
    private static final String LINK_Video_RELATED = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&relatedToVideoId=5efrC3vLH_U&type=video&key=AIzaSyAqLTS8_2lvEz7Y6vtDrsl1modOmWIBJ7o&maxresult=30";
    private static final String LINK_CHANNEL = "https://youtube.googleapis.com/youtube/v3/channels?part=statistics,snippet&id=UC_Eqg-tBPUUvA0heBZfdu6Q&key=AIzaSyAqLTS8_2lvEz7Y6vtDrsl1modOmWIBJ7o";
    private static final String LINK_SEARCH = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&order=rating&q=thong bao&type=video&key=AIzaSyCoVRVeURj-q8336ILoqdUj4dukH5YxM-s";
    public static final String API_KEY = "AIzaSyAJqPjz44VO97f-6gO_bi-q8BHxDNBQ5no";

}
