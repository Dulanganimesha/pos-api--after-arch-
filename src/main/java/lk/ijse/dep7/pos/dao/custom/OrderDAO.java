package lk.ijse.dep7.pos.dao.custom;

import lk.ijse.dep7.pos.dao.CrudDAO;
import lk.ijse.dep7.pos.entity.Order;

public interface OrderDAO extends CrudDAO<Order, String> {

    String getLastOrderId() throws Exception;
}
