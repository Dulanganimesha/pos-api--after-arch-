package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.custom.ItemDAO;
import lk.ijse.dep7.pos.db.DBConnection;
import lk.ijse.dep7.pos.entity.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAOImpl implements ItemDAO {

    private final Connection connection;

    public ItemDAOImpl() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void save(Item item) throws Exception {
        PreparedStatement stm = connection.prepareStatement("INSERT INTO item (code, description, unit_price, qty_on_hand) VALUES (?,?,?,?)");
        stm.setString(1, item.getCode());
        stm.setString(2, item.getDescription());
        stm.setBigDecimal(3, item.getUnitPrice());
        stm.setInt(4, item.getQtyOnHand());
        stm.executeUpdate();
    }

    @Override
    public void update(Item item) throws Exception {
        PreparedStatement stm = connection.prepareStatement("UPDATE item SET description=?, unit_price=?, qty_on_hand=? WHERE code=?");
        stm.setString(1, item.getDescription());
        stm.setBigDecimal(2, item.getUnitPrice());
        stm.setInt(3, item.getQtyOnHand());
        stm.setString(4, item.getCode());
        stm.executeUpdate();
    }

    @Override
    public void deleteById(String itemCode) throws Exception {
        PreparedStatement stm = connection.prepareStatement("DELETE FROM item WHERE code=?");
        stm.setString(1, itemCode);
        stm.executeUpdate();
    }

    @Override
    public Optional<Item> findById(String itemCode) throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM item WHERE code=?");
        stm.setString(1, itemCode);
        ResultSet rst = stm.executeQuery();

        return (rst.next()) ? Optional.of(new Item(itemCode, rst.getString("description"), rst.getBigDecimal("unit_price"), rst.getInt("qty_on_hand"))) : Optional.empty();
    }

    @Override
    public List<Item> findAll() throws Exception {
        List<Item> itemList = new ArrayList<>();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM item");

        while (rst.next()) {
            itemList.add(new Item(rst.getString("code"), rst.getString("description"), rst.getBigDecimal("unit_price"), rst.getInt("qty_on_hand")));
        }

        return itemList;
    }

    @Override
    public long count() throws Exception {
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT COUNT(*) FROM item");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean existsById(String itemCode) throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT code FROM item WHERE code=?");
        stm.setString(1, itemCode);
        return stm.executeQuery().next();
    }

    @Override
    public List<Item> findAll(int page, int size) throws Exception {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM item LIMIT ? OFFSET ?;");
            stm.setObject(1, size);
            stm.setObject(2, size * (page - 1));

            ResultSet rst = stm.executeQuery();
            List<Item> itemList = new ArrayList<>();

            while (rst.next()) {
                itemList.add(new Item(rst.getString("code"),
                        rst.getString("description"),
                        rst.getBigDecimal("unit_price"),
                        rst.getInt("qty_on_hand")));
            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch Items", e);
        }
    }

    @Override
    public String getLastItemCode() throws Exception {
        ResultSet rst = connection.createStatement().executeQuery("SELECT code FROM item ORDER BY code DESC LIMIT 1;");
        return rst.next() ? rst.getString("code") : null;
    }

}
