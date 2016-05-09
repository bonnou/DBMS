package net.in.ahr.dbms.data.storage.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "greendao");

        // MusicMst
        Entity musicMst = schema.addEntity("MusicMst");
        musicMst.implementsSerializable();
        musicMst.setSuperclass("net.in.ahr.dbms.others.ToStringMusicNameMark");

        musicMst.addIdProperty().autoincrement();

        Property musicMstNameProperty = musicMst.addStringProperty("name").getProperty();
        Property musicMstNhaProperty = musicMst.addStringProperty("nha").getProperty();

        musicMst.addStringProperty("version");
        musicMst.addStringProperty("genre");
        musicMst.addStringProperty("artist");
        musicMst.addIntProperty("bpmFrom");
        musicMst.addIntProperty("bpmTo");
        musicMst.addStringProperty("difficult");
        musicMst.addIntProperty("notes");
        musicMst.addIntProperty("scratchNotes");
        musicMst.addIntProperty("chargeNotes");
        musicMst.addIntProperty("backSpinScratchNotes");
        musicMst.addIntProperty("sortNumInDifficult");
        musicMst.addStringProperty("mstVersion");

        musicMst.addDateProperty("insDate");
        musicMst.addDateProperty("updDate");

        // TODO: カラム追加時は・・・？


        // MusicResult
        Entity musicResult_DBHR = schema.addEntity("MusicResultDBHR");
        musicResult_DBHR.implementsSerializable();

        musicResult_DBHR.addIdProperty().autoincrement();

        musicResult_DBHR.addStringProperty("name");
        musicResult_DBHR.addStringProperty("nha");

        musicResult_DBHR.addStringProperty("clearLamp");
        musicResult_DBHR.addIntProperty("exScore");
        musicResult_DBHR.addIntProperty("bp");

        musicResult_DBHR.addStringProperty("scoreRank");
        musicResult_DBHR.addDoubleProperty("scoreRate");
        musicResult_DBHR.addDoubleProperty("missRate");

        musicResult_DBHR.addStringProperty("tag");                          // #74 にて追加
        musicResult_DBHR.addStringProperty("fav");                          // #109にて追加
        musicResult_DBHR.addStringProperty("clearLamp_DBR");                // #84 にて追加
        musicResult_DBHR.addStringProperty("clearLamp_DBRR");               // #84 にて追加
        musicResult_DBHR.addStringProperty("clearLamp_DBM");                // #84 にて追加
        musicResult_DBHR.addStringProperty("clearLamp_DBSR");               // #84 にて追加
        musicResult_DBHR.addStringProperty("clearLamp_DBM_NONAS");          // #84 にて追加
        musicResult_DBHR.addStringProperty("clearLamp_RH");                 // #84 にて追加
        musicResult_DBHR.addStringProperty("clearLamp_LH");                 // #84 にて追加
        musicResult_DBHR.addStringProperty("myDifficult");                  // #75 にて追加

        musicResult_DBHR.addDoubleProperty("djPoint");                      // #108にて追加
        musicResult_DBHR.addDoubleProperty("clearProgressRate");            // #91 にて追加
        musicResult_DBHR.addDateProperty("lastPlayDate");                   // #98 にて追加
        musicResult_DBHR.addDateProperty("lastUpdateDate");                 // #98 にて追加

        // 残ゲージor到達ノーツ数
        musicResult_DBHR.addIntProperty("remainingGaugeOrDeadNotes");       // #60 にて追加
//        musicResult_DBHR.addStringProperty("memoProgress");               // #60 にて削除
        musicResult_DBHR.addStringProperty("memoOther");
        musicResult_DBHR.addStringProperty("pGreat");                       // 現在未使用
        musicResult_DBHR.addStringProperty("great");                        // 現在未使用
        musicResult_DBHR.addStringProperty("good");                         // 現在未使用
        musicResult_DBHR.addStringProperty("bad");                          // 現在未使用
        musicResult_DBHR.addStringProperty("poor");                         // 現在未使用
        musicResult_DBHR.addStringProperty("comboBreak");
        musicResult_DBHR.addDateProperty("insDate");
        musicResult_DBHR.addDateProperty("updDate");

        // 結合
        Property musicResultId_DBHR =
                musicMst.addLongProperty("musicResultIdDBHR")
                    .notNull().getProperty();
        musicMst.addToOne(musicResult_DBHR, musicResultId_DBHR);


        // MusicResultHistory
        Entity musicResult_DBHR_History = schema.addEntity("MusicResultDBHR_History");
        musicResult_DBHR_History.implementsSerializable();

        musicResult_DBHR_History.addIdProperty().autoincrement();

        Property historyNameProperty = musicResult_DBHR_History.addStringProperty("name").getProperty();
        Property historyNhaProperty = musicResult_DBHR_History.addStringProperty("nha").getProperty();
        musicResult_DBHR_History.addDateProperty("insDate");

        musicResult_DBHR_History.addBooleanProperty("playedFlg");

        musicResult_DBHR_History.addStringProperty("clearLamp");
        musicResult_DBHR_History.addIntProperty("exScore");
        musicResult_DBHR_History.addIntProperty("bp");

        musicResult_DBHR_History.addStringProperty("scoreRank");
        musicResult_DBHR_History.addDoubleProperty("scoreRate");
        musicResult_DBHR_History.addDoubleProperty("missRate");

        musicResult_DBHR_History.addStringProperty("tag");                          // #74 にて追加
        musicResult_DBHR_History.addStringProperty("fav");                          // #109にて追加
        musicResult_DBHR_History.addStringProperty("clearLamp_DBR");                // #84 にて追加
        musicResult_DBHR_History.addStringProperty("clearLamp_DBRR");               // #84 にて追加
        musicResult_DBHR_History.addStringProperty("clearLamp_DBM");                // #84 にて追加
        musicResult_DBHR_History.addStringProperty("clearLamp_DBSR");               // #84 にて追加
        musicResult_DBHR_History.addStringProperty("clearLamp_DBM_NONAS");          // #84 にて追加
        musicResult_DBHR_History.addStringProperty("clearLamp_RH");                 // #84 にて追加
        musicResult_DBHR_History.addStringProperty("clearLamp_LH");                 // #84 にて追加
        musicResult_DBHR_History.addStringProperty("myDifficult");                  // #75 にて追加

        musicResult_DBHR_History.addDoubleProperty("djPoint");                         // #108にて追加
        musicResult_DBHR_History.addDoubleProperty("clearProgressRate");            // #91 にて追加
        musicResult_DBHR_History.addDateProperty("lastPlayDate");                   // #98 にて追加
        musicResult_DBHR_History.addDateProperty("lastUpdateDate");                 // #98 にて追加

        // 残ゲージor到達ノーツ数
        musicResult_DBHR_History.addIntProperty("remainingGaugeOrDeadNotes");       // #60 にて追加
//        musicResult_DBHR.addStringProperty("memoProgress");               // #60 にて削除
        musicResult_DBHR_History.addStringProperty("memoOther");
        musicResult_DBHR_History.addStringProperty("pGreat");                       // 現在未使用
        musicResult_DBHR_History.addStringProperty("great");                        // 現在未使用
        musicResult_DBHR_History.addStringProperty("good");                         // 現在未使用
        musicResult_DBHR_History.addStringProperty("bad");                          // 現在未使用
        musicResult_DBHR_History.addStringProperty("poor");                         // 現在未使用
        musicResult_DBHR_History.addStringProperty("comboBreak");
//        musicResult_DBHR_History.addDateProperty("updDate");

        // 結合
        Property[] sourceProperties = new Property[] {musicMstNameProperty, musicMstNhaProperty};
        Property[] targetProperties = new Property[] {historyNameProperty, historyNhaProperty};
        musicMst.addToMany(sourceProperties, musicResult_DBHR_History, targetProperties);

        // 生成
        new DaoGenerator().generateAll(schema, args[0]);





    }
}
