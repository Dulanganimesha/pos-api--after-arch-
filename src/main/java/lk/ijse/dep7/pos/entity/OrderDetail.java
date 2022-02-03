package lk.ijse.dep7.pos.entity;

import java.math.BigDecimal;

public class OrderDetail implements SuperEntity {
    private OrderDetailPK orderDetailPK;
    private BigDecimal unitPrice;
    private int qty;

    public OrderDetail() {
    }

    public OrderDetail(OrderDetailPK orderDetailPK, BigDecimal unitPrice, int qty) {
        this.orderDetailPK = orderDetailPK;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public OrderDetail(String orderId, String itemCode, BigDecimal unitPrice, int qty) {
        this.orderDetailPK = new OrderDetailPK(orderId, itemCode);
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public OrderDetailPK getOrderDetailPK() {
        return orderDetailPK;
    }

    public void setOrderDetailPK(OrderDetailPK orderDetailPK) {
        this.orderDetailPK = orderDetailPK;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailPK=" + orderDetailPK +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                '}';
    }
}
