package me.qiushui.fromletterguesssongs.database;

import me.qiushui.fromletterguesssongs.database.source.PackInfo;
import me.qiushui.fromletterguesssongs.database.source.SongInfo;
import me.qiushui.fromletterguesssongs.database.source.Source;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import me.qiushui.fromletterguesssongs.database.source.arcaea.ArcaeaChineseWiki;

public class InitDatabase {
    public static void buildDatabase() {
        try (Connection db = createDatabase()) {
            createSheets(db);
            importDataIntoDatabase(db);
        } catch (Exception e) {
            if (e.getClass().equals(IOException.class)) {
                System.err.println("报错！错误码为100：" + e.getMessage());
                System.exit(100);
            } else if (e.getClass().equals(SQLException.class)) {
                System.err.println("报错！错误码为900：" + e.getMessage());
                System.exit(900);
            } else {
                System.err.println("报错！错误码为-1：" + e.getMessage());
                System.exit(-1);
            }
        }
    }

    private static Connection createDatabase() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:songInfos.db");
    }

    private static void createSheets(Connection db) throws SQLException {
        Statement createSheetStatement = db.createStatement();
        createArcaeaSheet(createSheetStatement);
        createSheetStatement.close();
    }

    private static void importDataIntoDatabase(Connection db) throws IOException {
        Source arcaeaDataFromWiki = ArcaeaChineseWiki.getArcaeaInfosFromWiki();

    }

    private static void importArcaeaDataIntoDatabase(Connection db, Source sourceData) throws SQLException {
        Statement importDataStatement = db.createStatement();

        for (PackInfo packInfo : sourceData.getPackInfos()) {
            String importPacksTableOrder;
            if (packInfo.isAppended()) {
                importPacksTableOrder = "INSERT INTO ArcaeaPacks(idx, title, append_to)" +
                        "VALUES(" + packInfo.getIdx() + ", " + packInfo.getTitle() + ", " + packInfo.appendTo.idx + ")";
            } else {
                importPacksTableOrder = "INSERT INTO ArcaeaPacks(idx, title, append_to)" +
                        "VALUES(" + packInfo.getIdx() + ", " + packInfo.getTitle() + ")";
            }

            importDataStatement.executeUpdate(importPacksTableOrder);
        }

        for (SongInfo songInfo : sourceData.getSongInfos()) {
            String importSongsTableOrder = "INSERT INTO ArcaeaSongs(idx, pack, title, artist)" +
                    "VALUES(" + songInfo.getIdx() + ", " + songInfo.getPack() + "," +
                    "      " + songInfo.getName() + ", " + songInfo.getJsonArtists() + ")";

            importDataStatement.executeUpdate(importSongsTableOrder);
        }
    }

    private static void createArcaeaSheet(Statement statement) throws SQLException {
        String createPacksTableOrder = "CREATE TABLE ArcaeaPacks" +
                "(idx         INTEGER  NOT NULL  PRIMARY KEY ," +
                " title       TEXT     NOT NULL              ," +
                " append_to   INTEGER                        )";
        String createSongsTableOrder = "CREATE TABLE ArcaeaSongs" +
                "(idx         INTEGER  NOT NULL  PRIMARY KEY ," +
                " pack        TEXT                           ," +
                " title       TEXT     NOT NULL              ," +
                " artist      TEXT     NOT NULL              )";
        statement.executeUpdate(createPacksTableOrder);
        statement.executeUpdate(createSongsTableOrder);
    }
}
