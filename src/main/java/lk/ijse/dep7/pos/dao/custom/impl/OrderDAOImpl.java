package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.custom.OrderDAO;
import lk.ijse.dep7.pos.db.DBConnection;
import lk.ijse.dep7.pos.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {

    private final Connection connection;

    public OrderDAOImpl() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void save(Order order) throws Exception {
        PreparedStatement stm = connection.prepareStatement("INSERT INTO `order` (id, date, customer_id) VALUES (?,?,?)");
        stm.setString(1, order.getId());
        stm.setDate(2, order.getDate());
        stm.setString(3, order.getCustomerId());
        stm.executeUpdate();
    }

    @Override
    public void update(Order order) throws Exception {
        PreparedStatement stm = connection.prepareStatement("UPDATE `order` SET date=?, customer_id=? WHERE id=?");
        stm.setDate(1, order.getDate());
        stm.setString(2, order.getCustomerId());
        stm.setString(3, order.getId());
        stm.executeUpdate();
    }

    @Override
    public void deleteById(String orderId) throws Exception {
        PreparedStatement stm = connection.prepareStatement("DELETE FROM `order` WHERE id=?");
        stm.setString(1, orderId);
        stm.executeUpdate();
    }

    @Override
    public Optional<Order> findById(String orderId) throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM `order` WHERE id=?");
        stm.setString(1, orderId);
        ResultSet rst = stm.executeQuery();
        return (rst.next()) ? Optional.of(new Order(orderId, rst.getDate("date"), rst.getString("customer_id"))) : Optional.empty();
    }

    @Override
    public List<Order> findAll() throws Exception {
        List<Order> orderList = new ArrayList<>();

        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM `order`");

        while (rst.next()) {
            orderList.add(new Order(rst.getString("id"), rst.getDate("date"), rst.getString("customer_id")));
        }

        return orderList;
    }

    @Override
    public long count() throws Exception {
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT COUNT(*) FROM `order`");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean existsById(String orderId) throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT id FROM `order` WHERE id=?");
        stm.setString(1, orderId);
        return stm.executeQuery().next();
    }

    @Override
    public List<Order> findAll(int page, int size) throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM `order` LIMIT ? OFFSET ?;");
        stm.setObject(1, size);
        stm.setObject(2, size * (page - 1));

        ResultSet rst = stm.executeQuery();
        List<Order> orderList = new ArrayList<>();

        while (rst.next()) {
            orderList.add(new Order(rst.getString("id"), rst.getDate("date"), rst.getString("customer_id")));
        }
        return orderList;
    }

    @Override
    public String getLastOrderId() throws Exception {
        ResultSet rst = connection.createStatement().executeQuery("SELECT id FROM `order` ORDER BY id DESC LIMIT 1;");
        return rst.next() ? rst.getString("id") : null;
    }
}
