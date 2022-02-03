package lk.ijse.dep7.pos.service.custom.impl;

import lk.ijse.dep7.pos.dao.DAOFactory;
import lk.ijse.dep7.pos.dao.DAOType;
import lk.ijse.dep7.pos.dao.custom.CustomerDAO;
import lk.ijse.dep7.pos.dao.custom.OrderDAO;
import lk.ijse.dep7.pos.dao.custom.OrderDetailDAO;
import lk.ijse.dep7.pos.dao.custom.QueryDAO;
import lk.ijse.dep7.pos.db.DBConnection;
import lk.ijse.dep7.pos.dto.ItemDTO;
import lk.ijse.dep7.pos.dto.OrderDTO;
import lk.ijse.dep7.pos.dto.OrderDetailDTO;
import lk.ijse.dep7.pos.entity.Customer;
import lk.ijse.dep7.pos.entity.Order;
import lk.ijse.dep7.pos.entity.OrderDetail;
import lk.ijse.dep7.pos.service.ServiceFactory;
import lk.ijse.dep7.pos.service.ServiceType;
import lk.ijse.dep7.pos.service.custom.CustomerService;
import lk.ijse.dep7.pos.service.custom.ItemService;
import lk.ijse.dep7.pos.service.custom.OrderService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static lk.ijse.dep7.pos.service.util.EntityDTOMapper.*;

public class OrderServiceImpl implements OrderService {

    private final CustomerDAO customerDAO;
    private final OrderDAO orderDAO;
    private final OrderDetailDAO orderDetailDAO;
    private final QueryDAO queryDAO;

    public OrderServiceImpl() {
        this.orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
        this.orderDetailDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER_DETAIL);
        this.queryDAO = DAOFactory.getInstance().getDAO(DAOType.QUERY);
        this.customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    }

    @Override
    public void saveOrder(OrderDTO order) throws Exception {

        final Connection connection = DBConnection.getConnection();
        final CustomerService customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
        final ItemService itemService = ServiceFactory.getInstance().getService(ServiceType.ITEM);
        final String orderId = order.getOrderId();
        final String customerId = order.getCustomerId();

        try {
            connection.setAutoCommit(false);

            if (orderDAO.existsById(orderId)) {
                throw new RuntimeException("Invalid Order ID." + orderId + " already exists");
            }

            if (!customerService.existCustomer(customerId)) {
                throw new RuntimeException("Invalid Customer ID." + customerId + " doesn't exist");
            }

            orderDAO.save(fromOrderDTO(order));

            for (OrderDetailDTO detail : order.getOrderDetails()) {
                orderDetailDAO.save(fromOrderDetailDTO(orderId, detail));

                ItemDTO item = itemService.findItem(detail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());
                itemService.updateItem(item);
            }

            connection.commit();

        } catch (SQLException e) {
            failedOperationExecutionContext(connection::rollback);
            throw e;
        } catch (Throwable t) {
            failedOperationExecutionContext(connection::rollback);
            throw t;
        } finally {
            failedOperationExecutionContext(() -> connection.setAutoCommit(true));
        }

    }

    @Override
    public long getSearchOrdersCount(String query) throws Exception {
        return queryDAO.countOrders(query);
    }

    @Override
    public List<OrderDTO> searchOrders(String query, int page, int size) throws Exception {
        // CustomEntity => OrderDTO
        // List<CustomEntity> => List<OrderDTO>
        return toOrderDTO2(queryDAO.findOrders(query, page, size));
    }

    @Override
    public OrderDTO searchOrder(String orderId) throws Exception {
        Order order = orderDAO.findById(orderId).orElseThrow(() -> new RuntimeException("Invalid Order ID: " + orderId));
        Customer customer = customerDAO.findById(order.getCustomerId()).get();
        BigDecimal orderTotal = orderDetailDAO.findOrderTotal(orderId).get();
        List<OrderDetail> orderDetails = orderDetailDAO.findOrderDetailsByOrderId(orderId);

        return toOrderDTO(order, customer, orderTotal, orderDetails);
    }

    @Override
    public String generateNewOrderId() throws Exception {
        String id = orderDAO.getLastOrderId();
        if (id != null) {
            return String.format("OD%03d", (Integer.parseInt(id.replace("OD", "")) + 1));
        } else {
            return "OD001";
        }

    }

    private void failedOperationExecutionContext(ExecutionContext context) {
        try {
            context.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save the order", e);
        }
    }

    @FunctionalInterface
    interface ExecutionContext {
        void execute() throws SQLException;
    }

}
