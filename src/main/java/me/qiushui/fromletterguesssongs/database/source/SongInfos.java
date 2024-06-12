package me.qiushui.fromletterguesssongs.database.source;

import java.util.ArrayList;
import java.util.List;

public class SongInfos extends ArrayList<SongInfo> {
    public SongInfos() {

    }

    public static SongInfos ofInfo(SongInfo... songInfos) {
        return (SongInfos) List.of(songInfos);
    }
}
