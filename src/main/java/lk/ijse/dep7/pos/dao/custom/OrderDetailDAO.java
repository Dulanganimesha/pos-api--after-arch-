package lk.ijse.dep7.pos.dao.custom;

import lk.ijse.dep7.pos.dao.CrudDAO;
import lk.ijse.dep7.pos.entity.OrderDetail;
import lk.ijse.dep7.pos.entity.OrderDetailPK;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderDetailDAO extends CrudDAO<OrderDetail, OrderDetailPK> {

    Optional<BigDecimal> findOrderTotal(String orderId) throws Exception;

    List<OrderDetail> findOrderDetailsByOrderId(String orderId) throws Exception;
}
