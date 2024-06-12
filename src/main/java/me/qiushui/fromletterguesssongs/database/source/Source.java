package me.qiushui.fromletterguesssongs.database.source;

import java.util.List;
import java.util.Map;

public class Source {
    public final String sourceName;
    public final PackInfos packInfos;
    public final SongInfos songInfos;

    public Source(String sourceName, PackInfos packInfos, SongInfos songInfos) {
        this.sourceName = sourceName;
        this.packInfos = packInfos;
        this.songInfos = songInfos;
    }
    public static Source buildInformation(String sourceName, PackInfos packInfos, SongInfos songInfos) {
        return new Source(sourceName, packInfos, songInfos);
    }

    public PackInfos getPackInfos() {
        return this.packInfos;
    }

    public SongInfos getSongInfos() {
        return this.songInfos;
    }
}
