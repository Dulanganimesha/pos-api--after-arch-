package lk.ijse.dep7.pos.service.custom;

import lk.ijse.dep7.pos.dto.OrderDTO;
import lk.ijse.dep7.pos.service.SuperService;

import java.util.List;

public interface OrderService extends SuperService {

    void saveOrder(OrderDTO order) throws Exception;

    long getSearchOrdersCount(String query) throws Exception;

    List<OrderDTO> searchOrders(String query, int page, int size) throws Exception;

    OrderDTO searchOrder(String orderId) throws Exception;

    String generateNewOrderId() throws Exception;
}
