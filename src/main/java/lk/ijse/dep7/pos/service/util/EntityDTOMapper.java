package lk.ijse.dep7.pos.service.util;

import lk.ijse.dep7.pos.dto.CustomerDTO;
import lk.ijse.dep7.pos.dto.ItemDTO;
import lk.ijse.dep7.pos.dto.OrderDTO;
import lk.ijse.dep7.pos.dto.OrderDetailDTO;
import lk.ijse.dep7.pos.entity.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class EntityDTOMapper {

    public static CustomerDTO toCustomerDTO(Customer c) {
        return new CustomerDTO(c.getId(), c.getName(), c.getAddress());
    }

    public static Customer fromCustomerDTO(CustomerDTO c) {
        return new Customer(c.getId(), c.getName(), c.getAddress());
    }

    public static List<CustomerDTO> toCustomerDTOList(List<Customer> customers) {
        return customers.stream().map(EntityDTOMapper::toCustomerDTO).collect(Collectors.toList());
    }

    public static List<Customer> fromCustomerDTOList(List<CustomerDTO> customers) {
        return customers.stream().map(EntityDTOMapper::fromCustomerDTO).collect(Collectors.toList());
    }

    public static ItemDTO toItemDTO(Item i) {
        return new ItemDTO(i.getCode(), i.getDescription(), i.getUnitPrice(), i.getQtyOnHand());
    }

    public static Item fromItemDTO(ItemDTO i) {
        return new Item(i.getCode(), i.getDescription(), i.getUnitPrice(), i.getQtyOnHand());
    }

    public static List<ItemDTO> toItemDTOList(List<Item> items) {
        return items.stream().map(EntityDTOMapper::toItemDTO).collect(Collectors.toList());
    }

    public static List<Item> fromItemDTOList(List<ItemDTO> items) {
        return items.stream().map(EntityDTOMapper::fromItemDTO).collect(Collectors.toList());
    }

    public static Order fromOrderDTO(OrderDTO o) {
        return new Order(o.getOrderId(), Date.valueOf(o.getOrderDate()), o.getCustomerId());
    }

    public static OrderDetail fromOrderDetailDTO(String orderId, OrderDetailDTO od) {
        return new OrderDetail(orderId, od.getItemCode(), od.getUnitPrice(), od.getQty());
    }

    public static OrderDTO toOrderDTO(HashMap<String, Object> or) {
        return new OrderDTO(or.get("id").toString(),
                ((Date) or.get("date")).toLocalDate(),
                or.get("customer_id").toString(),
                or.get("name").toString(),
                (BigDecimal) or.get("total"));
    }

    public static OrderDTO toOrderDTO(CustomEntity ce) {
        return new OrderDTO(ce.getOrderId(),
                ce.getOrderDate().toLocalDate(),
                ce.getCustomerId(),
                ce.getCustomerName(),
                ce.getOrderTotal());
    }

    public static OrderDetailDTO toOrderDetailDTO(OrderDetail orderDetail) {
        return new OrderDetailDTO(orderDetail.getOrderDetailPK().getItemCode(),
                orderDetail.getQty(),
                orderDetail.getUnitPrice());
    }

    public static List<OrderDetailDTO> toOrderDetailDTOList(List<OrderDetail> orderDetails) {
        return orderDetails.stream().map(EntityDTOMapper::toOrderDetailDTO).collect(Collectors.toList());
    }

    public static OrderDTO toOrderDTO(Order order, Customer customer, BigDecimal orderTotal, List<OrderDetail> orderDetails) {
        return new OrderDTO(order.getId(),
                order.getDate().toLocalDate(),
                order.getCustomerId(),
                customer.getName(),
                orderTotal,
                // OrderDetail => OrderDetailDTO
                // List<OrderDetail> => List<OrderDetailDTO>
                toOrderDetailDTOList(orderDetails));
    }

    public static List<OrderDTO> toOrderDTO1(List<HashMap<String, Object>> orderRecords) {
        return orderRecords.stream().map(EntityDTOMapper::toOrderDTO).collect(Collectors.toList());
    }

    public static List<OrderDTO> toOrderDTO2(List<CustomEntity> orderRecords) {
        return orderRecords.stream().map(EntityDTOMapper::toOrderDTO).collect(Collectors.toList());
    }
}
