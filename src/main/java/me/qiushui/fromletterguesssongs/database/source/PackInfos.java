package me.qiushui.fromletterguesssongs.database.source;

import java.util.ArrayList;
import java.util.List;

public class PackInfos extends ArrayList<PackInfo> {
    public PackInfos() {
    }

    public static PackInfos ofInfo(PackInfo... packInfos) {
        return (PackInfos) List.of(packInfos);
    }
}
