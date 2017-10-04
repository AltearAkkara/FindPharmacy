package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyDAO {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.atoz.akkaratanapat.daogenerator");
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema,"../app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addUserEntities(schema);
        // addPhonesEntities(schema);
    }

    // This is use to describe the colums of your table
    private static Entity addUserEntities(final Schema schema) {
        Entity pharmacy = schema.addEntity("Pharmacy");
        pharmacy.addIdProperty().primaryKey().autoincrement();
        pharmacy.addStringProperty("name");
        pharmacy.addStringProperty("address");
        pharmacy.addDoubleProperty("lat");
        pharmacy.addDoubleProperty("lng");
        pharmacy.addStringProperty("number");
        pharmacy.addStringProperty("owner");

        return pharmacy;
    }
}
