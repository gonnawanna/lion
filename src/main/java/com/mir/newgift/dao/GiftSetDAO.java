package com.mir.newgift.dao;

import com.mir.newgift.model.GiftSet;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

@Component
public class GiftSetDAO {

    private static Connection connection;
    private ArrayList<GiftSet> giftSets;

    static {
        String url = null;
        String username = null;
        String password = null;

        try(InputStream in = GiftSetDAO.class.getClassLoader().getResourceAsStream("persistence.properties")) {

            Properties properties = new Properties();
            properties.load(in);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void createTemporaryViews() throws SQLException {
        Statement stmtCreateTempView = connection.createStatement();
        Statement stmtCreateSetsView = connection.createStatement();

        String sqlQueryCreateTempView = "CREATE VIEW temp AS\n" +
                    "SELECT product_id, feature_1_id, feature_2_id\n" +
                    "FROM products NATURAL INNER JOIN product_feature_1 NATURAL INNER JOIN product_feature_2;";
        String sqlQueryCreateSetsView = "CREATE VIEW sets AS\n" +
                    "SELECT products1.product_id AS product1_id, products2.product_id AS product2_id," +
                    "products3.product_id AS product3_id, feature_1_id, feature_2_id\n" +
                    "FROM\n" +
                    "(excess_reserve NATURAL INNER JOIN temp) AS products1\n" +
                    "INNER JOIN temp AS products2 USING (feature_1_id, feature_2_id)\n" +
                    "INNER JOIN temp AS products3 USING (feature_1_id, feature_2_id);";

        stmtCreateTempView.executeUpdate(sqlQueryCreateTempView);
        stmtCreateSetsView.executeUpdate(sqlQueryCreateSetsView);
    }

    public void delTemporaryViews() throws SQLException {
        Statement stmtDeleteViews = connection.createStatement();
        String sqlQueryDeleteViews = "DROP VIEW sets;DROP VIEW temp";
        stmtDeleteViews.executeUpdate(sqlQueryDeleteViews);
    }

    public void createGiftSets() throws SQLException {
        String sqlQuerySelectSets = "SELECT products1.name, products2.name, products3.name, feature_1_id, feature_2_id,\n" +
                "products1.price+products2.price+products3.price AS total_price\n" +
                "FROM sets INNER JOIN products AS products1 ON product1_id = products1.product_id\n" +
                "INNER JOIN products AS products2 ON product2_id = products2.product_id\n" +
                "INNER JOIN products AS products3 ON product3_id = products3.product_id;";
        PreparedStatement ps = connection.prepareStatement(sqlQuerySelectSets);
        ResultSet rs = ps.executeQuery();
        ArrayList<GiftSet> giftSets = new ArrayList<>();
        while (rs.next()) {
            String firstProductName = rs.getString(1);
            String secondProductName = rs.getString(2);
            String thirdProductName = rs.getString(3);
            int firstFeatureValue = rs.getInt(4);
            int secondFeatureValue = rs.getInt(5);
            //may be String not int
            float price = rs.getFloat(6);
            GiftSet giftSet = new GiftSet(firstProductName, secondProductName, thirdProductName, price);

            if (giftSets.contains(giftSet)) {
                int i = giftSets.indexOf(giftSet);
                giftSets.get(i).addFeature(1, firstFeatureValue);
                giftSets.get(i).addFeature(2, secondFeatureValue);
            } else {
                giftSet.addFeature(1, firstFeatureValue);
                giftSet.addFeature(2, secondFeatureValue);
                giftSets.add(giftSet);
            }
        }
        this.giftSets = giftSets;
    }

    public ArrayList<GiftSet> getGiftSets() {
        return giftSets;
    }
}
