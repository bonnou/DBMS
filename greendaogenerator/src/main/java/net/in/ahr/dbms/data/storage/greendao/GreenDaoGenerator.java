package net.in.ahr.dbms.data.storage.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "greendao");
        Entity memo = schema.addEntity("Memo");
        memo.addIdProperty();
        memo.addStringProperty("text");
        memo.addLongProperty("date");
        new DaoGenerator().generateAll(schema, args[0]);
    }
}
