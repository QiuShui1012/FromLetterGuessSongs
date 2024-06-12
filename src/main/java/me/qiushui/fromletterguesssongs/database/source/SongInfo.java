package me.qiushui.fromletterguesssongs.database.source;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SongInfo {
    public final int idx;
    public final String name;
    public final List<String> artists;
    public final String pack;

    public SongInfo(int idx, String name, List<String> artists, String pack) {
        this.idx = idx;
        this.name = name;
        this.artists = artists;
        this.pack = pack;
    }

    public static SongInfo createSongInfo(int idx, @NotNull String name, @NotNull List<String> artists) {
        return new SongInfo(idx, name, artists, "Unsorted");
    }

    public static SongInfo createSongInfo(int idx, @NotNull String name, @NotNull List<String> artists, String pack) {
        return new SongInfo(idx, name, artists, pack);
    }

    public int getIdx() {
        return this.idx;
    }
    public String getName() {
        return this.name;
    }
    public List<String> getArtists() {
        return this.artists;
    }
    public String getJsonArtists() {
        return new Gson().toJson(this.artists);
    }
    public String getPack() {
        return this.pack;
    }
}
