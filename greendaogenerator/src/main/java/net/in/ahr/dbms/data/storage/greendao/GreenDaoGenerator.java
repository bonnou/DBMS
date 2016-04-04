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

        musicMst.addStringProperty("name");
        musicMst.addStringProperty("nha");

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

        musicResult_DBHR.addStringProperty("clearLamp");
        musicResult_DBHR.addIntProperty("exScore");
        musicResult_DBHR.addIntProperty("bp");
        musicResult_DBHR.addStringProperty("scoreRank");
        musicResult_DBHR.addDoubleProperty("scoreRate");
        musicResult_DBHR.addDoubleProperty("missRate");
        musicResult_DBHR.addStringProperty("memoProgress");
        musicResult_DBHR.addStringProperty("memoOther");
        musicResult_DBHR.addStringProperty("pGreat");
        musicResult_DBHR.addStringProperty("great");
        musicResult_DBHR.addStringProperty("good");
        musicResult_DBHR.addStringProperty("bad");
        musicResult_DBHR.addStringProperty("poor");
        musicResult_DBHR.addStringProperty("comboBreak");
        musicResult_DBHR.addDateProperty("insDate");
        musicResult_DBHR.addDateProperty("updDate");

        // 結合
        Property musicResultId_DBHR =
                musicMst.addLongProperty("musicResultIdDBHR")
                    .notNull().getProperty();
        musicMst.addToOne(musicResult_DBHR, musicResultId_DBHR);

        // 生成
        new DaoGenerator().generateAll(schema, args[0]);





    }
}
