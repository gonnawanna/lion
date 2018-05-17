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

    private ArrayList<GiftSet> giftSets;

    private static Connection connection;
    private static final String SQL_QUERY_CREATE_TEMP_VIEW = "CREATE VIEW temp AS\n" +
            "SELECT product_id, feature_1_id, feature_2_id\n" +
            "FROM products NATURAL INNER JOIN product_feature_1 NATURAL INNER JOIN product_feature_2;";
    private static final String SQL_QUERY_CREATE_SETS_VIEW = "CREATE VIEW sets AS\n" +
            "SELECT products1.product_id AS product1_id, products2.product_id AS product2_id," +
            "products3.product_id AS product3_id, feature_1_id, feature_2_id\n" +
            "FROM\n" +
            "(excess_reserve NATURAL INNER JOIN temp) AS products1\n" +
            "INNER JOIN temp AS products2 USING (feature_1_id, feature_2_id)\n" +
            "INNER JOIN temp AS products3 USING (feature_1_id, feature_2_id)" +
            "WHERE products1.product_id <> products2.product_id AND products1.product_id <> products3.product_id\n" +
            "AND products2.product_id <> products3.product_id;";
    private static final String SQL_QUERY_DELETE_VIEWS = "DROP VIEW sets;" +
            "DROP VIEW temp";
    private static final String SQL_QUERY_SELECT_SETS = "SELECT products1.name, products2.name, products3.name,"+
    "feature_1.feature_value, feature_2.feature_value,"+
    "products1.price+products2.price+products3.price AS total_price\n" +
    "FROM sets INNER JOIN products AS products1 ON product1_id = products1.product_id\n" +
    "INNER JOIN products AS products2 ON product2_id = products2.product_id\n" +
    "INNER JOIN products AS products3 ON product3_id = products3.product_id\n" +
    "INNER JOIN feature_1 ON feature_1.feature_1_id = sets.feature_1_id\n" +
    "INNER JOIN feature_2 ON feature_2.feature_2_id = sets.feature_1_id\n" +
    "ORDER BY total_price;";

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
        stmtCreateTempView.executeUpdate(SQL_QUERY_CREATE_TEMP_VIEW);
        stmtCreateSetsView.executeUpdate(SQL_QUERY_CREATE_SETS_VIEW);
    }

    public void delTemporaryViews() throws SQLException {
        Statement stmtDeleteViews = connection.createStatement();
        stmtDeleteViews.executeUpdate(SQL_QUERY_DELETE_VIEWS);
    }

    public void createGiftSets() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SQL_QUERY_SELECT_SETS);
        ResultSet rs = ps.executeQuery();
        ArrayList<GiftSet> giftSets = new ArrayList<>();

        while (rs.next()) {
            ArrayList<String> products = new ArrayList<>();
            for (int column = 1; column <= 3; column++) {
                products.add(rs.getString(column));
            }
            String firstFeatureValue = rs.getString(4);
            String secondFeatureValue = rs.getString(5);
            float price = rs.getFloat(6);

            GiftSet giftSet = new GiftSet();
            giftSet.setProducts(products);
            giftSet.setPrice(price);

            if (giftSets.contains(giftSet)) {
                int i = giftSets.indexOf(giftSet);
                giftSets.get(i).addFeature(firstFeatureValue);
                giftSets.get(i).addFeature(secondFeatureValue);
            } else {
                ArrayList<String> features = new ArrayList<>();
                features.add(firstFeatureValue);
                features.add(secondFeatureValue);
                giftSet.setFeatures(features);
                giftSets.add(giftSet);
            }
        }

        this.giftSets = giftSets;
    }

    public ArrayList<GiftSet> getGiftSets() {
        return giftSets;
    }

    public ArrayList<GiftSet> getGiftSets(String feature1, String feature2) {
        ArrayList<GiftSet> result = new ArrayList<>();
        ArrayList<String> inputFeatures= new ArrayList<>();
        inputFeatures.add(feature1);
        inputFeatures.add(feature2);

        for (GiftSet giftSet : giftSets) {
            ArrayList<String> features = giftSet.getFeatures();
            if (features.containsAll(inputFeatures)) {
                result.add(giftSet);
            }
        }
        return result;
    }
}
