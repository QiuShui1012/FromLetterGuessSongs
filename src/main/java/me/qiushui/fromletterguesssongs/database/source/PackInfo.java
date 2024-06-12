package me.qiushui.fromletterguesssongs.database.source;

import org.jetbrains.annotations.NotNull;

public class PackInfo {
    public final int idx;
    public final String title;
    public final PackInfo appendTo;

    public PackInfo(int idx, String title, PackInfo appendTo) {
        this.idx = idx;
        this.title = title;
        this.appendTo = appendTo;
    }

    public static PackInfo createPackInfo(int idx, @NotNull String title) {
        return new PackInfo(idx, title, null);
    }
    public static PackInfo createPackInfo(int idx, @NotNull String title, PackInfo appendTo) {
        return new PackInfo(idx, title, appendTo);
    }

    public int getIdx() {
        return this.idx;
    }
    public String getTitle() {
        return this.title;
    }
    public boolean isAppended() {
        return this.appendTo != null;
    }
}
