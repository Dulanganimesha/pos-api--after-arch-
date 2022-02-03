package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.custom.QueryDAO;
import lk.ijse.dep7.pos.db.DBConnection;
import lk.ijse.dep7.pos.entity.CustomEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    private final Connection connection;

    public QueryDAOImpl() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public List<HashMap<String, Object>> findOrders(String query) throws Exception {
        List<HashMap<String, Object>> orderList = new ArrayList<>();

        String[] searchWords = query.split("\\s");
        StringBuilder sqlBuilder = new StringBuilder("SELECT o.*, c.name, order_total.total\n" +
                "FROM `order` o\n" +
                "         INNER JOIN customer c on o.customer_id = c.id\n" +
                "         INNER JOIN\n" +
                "     (SELECT order_id, SUM(qty * unit_price) AS total FROM order_detail od GROUP BY order_id) AS order_total\n" +
                "     ON o.id = order_total.order_id\n" +
                "WHERE (order_id LIKE ?\n" +
                "    OR date LIKE ?\n" +
                "    OR customer_id LIKE ?\n" +
                "    OR name LIKE ?) ");

        for (int i = 1; i < searchWords.length; i++) {
            sqlBuilder.append("AND (\n" +
                    "            order_id LIKE ?\n" +
                    "        OR date LIKE ?\n" +
                    "        OR customer_id LIKE ?\n" +
                    "        OR name LIKE ?)");
        }
        PreparedStatement stm = connection.prepareStatement(sqlBuilder.toString());

        for (int i = 0; i < searchWords.length * 4; i++) {
            stm.setString(i + 1, "%" + searchWords[(i / 4)] + "%");
        }
        ResultSet rst = stm.executeQuery();

        while (rst.next()) {
            HashMap<String, Object> newRecord = new HashMap<>();
            newRecord.put("id", rst.getString("id"));
            newRecord.put("date", rst.getDate("date"));
            newRecord.put("customer_id", rst.getString("customer_id"));
            newRecord.put("name", rst.getString("name"));
            newRecord.put("total", rst.getBigDecimal("total"));

            orderList.add(newRecord);
        }

        return orderList;

    }

    @Override
    public long countOrders(String query) throws Exception {
        String[] searchWords = query.split("\\s");
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) \n" +
                "FROM `order` o\n" +
                "         INNER JOIN customer c on o.customer_id = c.id\n" +
                "         INNER JOIN\n" +
                "     (SELECT order_id, SUM(qty * unit_price) AS total FROM order_detail od GROUP BY order_id) AS order_total\n" +
                "     ON o.id = order_total.order_id\n" +
                "WHERE (order_id LIKE ?\n" +
                "    OR date LIKE ?\n" +
                "    OR customer_id LIKE ?\n" +
                "    OR name LIKE ?) ");

        for (int i = 1; i < searchWords.length; i++) {
            sqlBuilder.append("AND (\n" +
                    "            order_id LIKE ?\n" +
                    "        OR date LIKE ?\n" +
                    "        OR customer_id LIKE ?\n" +
                    "        OR name LIKE ?)");
        }
        PreparedStatement stm = connection.prepareStatement(sqlBuilder.toString());

        for (int i = 0; i < searchWords.length * 4; i++) {
            stm.setString(i + 1, "%" + searchWords[(i / 4)] + "%");
        }

        ResultSet rst = stm.executeQuery();
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public List<CustomEntity> findOrders(String query, int page, int size) throws Exception{
        List<CustomEntity> orderList = new ArrayList<>();

        String[] searchWords = query.split("\\s");
        StringBuilder sqlBuilder = new StringBuilder("SELECT o.*, c.name, order_total.total\n" +
                "FROM `order` o\n" +
                "         INNER JOIN customer c on o.customer_id = c.id\n" +
                "         INNER JOIN\n" +
                "     (SELECT order_id, SUM(qty * unit_price) AS total FROM order_detail od GROUP BY order_id) AS order_total\n" +
                "     ON o.id = order_total.order_id\n" +
                "WHERE (order_id LIKE ?\n" +
                "    OR date LIKE ?\n" +
                "    OR customer_id LIKE ?\n" +
                "    OR name LIKE ?) ");

        for (int i = 1; i < searchWords.length; i++) {
            sqlBuilder.append("AND (\n" +
                    "            order_id LIKE ?\n" +
                    "        OR date LIKE ?\n" +
                    "        OR customer_id LIKE ?\n" +
                    "        OR name LIKE ?)");
        }
        sqlBuilder.append(" LIMIT ? OFFSET ?");
        PreparedStatement stm = connection.prepareStatement(sqlBuilder.toString());

        for (int i = 0; i < searchWords.length * 4; i++) {
            stm.setString(i + 1, "%" + searchWords[(i / 4)] + "%");
        }
        stm.setInt((searchWords.length * 4) + 1, size);
        stm.setInt((searchWords.length * 4) + 2, size * (page - 1));
        ResultSet rst = stm.executeQuery();

        while (rst.next()) {
            orderList.add(new CustomEntity(rst.getString("id"), rst.getDate("date"),
                    rst.getString("customer_id"), rst.getString("name"), rst.getBigDecimal("total")));
        }

        return orderList;

    }

}
