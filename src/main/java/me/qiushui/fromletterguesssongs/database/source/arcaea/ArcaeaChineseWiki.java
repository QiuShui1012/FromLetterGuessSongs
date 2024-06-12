package me.qiushui.fromletterguesssongs.database.source.arcaea;

import me.qiushui.fromletterguesssongs.database.source.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArcaeaChineseWiki {
    public static Source getArcaeaInfosFromWiki() throws IOException {
        Pair<PackInfos, List<String>> packInfosAndSongLinks = getPackInfosFromWiki();

        Pair<SongInfos, Integer> songInfos = getNormalSongsInfosFromWiki(packInfosAndSongLinks.getRight());
        SongInfos unsortedSongsInfos = getUnsortedSongsInfosFromWiki(songInfos.getRight());

        SongInfos allSongsInfos = new SongInfos();
        allSongsInfos.addAll(songInfos.getLeft());
        allSongsInfos.addAll(unsortedSongsInfos);

        return Source.buildInformation("Arcaea-FromChineseWiki", packInfosAndSongLinks.getLeft(), allSongsInfos);
    }

    private static Pair<PackInfos, List<String>> getPackInfosFromWiki() throws IOException {
        Document categoryDoc = Jsoup.connect("https://arcwiki.mcd.blue/Category:曲包").get();

        List<String> packLinkSuffixes = categoryDoc.selectXpath("//div[@class='mw-category mw-category-columns']//a").eachAttr("href");
        List<String> packLinks = new ArrayList<>();
        for (String suffix : packLinkSuffixes) {
            if ((suffix.contains("Users:") & suffix.contains("/Sandbox")) | suffix.contains("Template:") | suffix.equals("Favorites")) {
                packLinkSuffixes.remove(suffix);
                continue;
            }
            packLinks.add("https://arcwiki.mcd.blue" + suffix);
        }

        PackInfos packInfos = new PackInfos();
        List<String> songLinks = new ArrayList<>();
        int packIdx = 0;
        for (String packLink : packLinks) {
            Document packDoc = Jsoup.connect(packLink).get();

            List<String> allPackNames = packDoc.selectXpath("//div[@id='title']").eachText();

            PackInfo mainPackInfo = PackInfo.createPackInfo(packIdx++, allPackNames.getFirst());
            packInfos.add(mainPackInfo);
            for (int i=1;i<allPackNames.size();i++) {
                PackInfo appendPackInfo = PackInfo.createPackInfo(packIdx++, allPackNames.get(i), mainPackInfo);
                packInfos.add(appendPackInfo);
            }

            List<String> songLinkSuffixes = packDoc.selectXpath("//div[text()='曲目']/following-sibling::div[1][@class='data']/a").eachAttr("href");
            for (String suffix : songLinkSuffixes) {
                if ((suffix.contains("Users:") & suffix.contains("/Sandbox")) | suffix.contains("Template:") | suffix.equals("Favorites")) {
                    packLinkSuffixes.remove(suffix);
                    continue;
                }
                songLinks.add("https://arcwiki.mcd.blue" + suffix);
            }
        }

        return new ImmutablePair<>(packInfos, songLinks);
    }

    private static Pair<SongInfos, Integer> getNormalSongsInfosFromWiki(List<String> songLinks) throws IOException {
        SongInfos songInfos = new SongInfos();
        int songIdx = 0;
        for (String songLink : songLinks) {
            Document songDoc = Jsoup.connect(songLink).get();

            String songName = songDoc.selectXpath("//div[@id='title']").text();
            String songPack = songDoc.selectXpath("//tbody[.//a[contains(text(), '" + songName + "')]]//a[./span]/@title").text();
            List<String> songArtists = songDoc.selectXpath("//div[text()='曲师']/following-sibling::div[1]").eachText();

            songInfos.add(SongInfo.createSongInfo(songIdx++, songName, songArtists, songPack));
        }
        return new ImmutablePair<>(songInfos, songIdx);
    }

    private static SongInfos getUnsortedSongsInfosFromWiki(int normalSongsCount) throws IOException {

        Document songsDoc = Jsoup.connect("https://arcwiki.mcd.blue/Category:非正式游戏杂曲目").get();

        List<String> songLinkSuffixes = songsDoc.selectXpath("//div[@class='mw-category mw-category-columns']//a").eachAttr("href");
        List<String> songLinks = new ArrayList<>();
        for (String suffix : songLinkSuffixes) {
            songLinks.add("https://arcwiki.mcd.blue" + suffix);
        }

        SongInfos songInfos = new SongInfos();
        int songIdx = normalSongsCount;
        for (String songLink : songLinks) {
            Document songDoc = Jsoup.connect(songLink).get();

            String songName = songDoc.selectXpath("//div[@id='title']").text();
            List<String> songArtists = songDoc.selectXpath("//div[text()='曲师']/following-sibling::div[1]").eachText();

            songInfos.add(SongInfo.createSongInfo(songIdx++, songName, songArtists));
        }
        return songInfos;
    }
}
