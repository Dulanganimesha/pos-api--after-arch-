package lk.ijse.dep7.pos.dao;

import lk.ijse.dep7.pos.dao.custom.impl.*;

import java.sql.Connection;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DAOType dao) {
        switch (dao) {
            case CUSTOMER:
                return (T) new CustomerDAOImpl();
            case ITEM:
                return (T) new ItemDAOImpl();
            case ORDER:
                return (T) new OrderDAOImpl();
            case ORDER_DETAIL:
                return (T) new OrderDetailDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();
            default:
                throw new RuntimeException("Invalid DAO");
        }
    }

//    public CustomerDAO getCustomerDAO(Connection connection){
//        return new CustomerDAOImpl(connection);
//    }
//
//    public ItemDAO getItemDAO(Connection connection){
//        return new ItemDAOImpl(connection);
//    }
//
//    public OrderDAO getOrderDAO(Connection connection){
//        return new OrderDAOImpl(connection);
//    }
//
//    public OrderDetailDAO getOrderDetailDAO(Connection connection){
//        return new OrderDetailDAOImpl(connection);
//    }
//
//    public QueryDAO getQueryDAO(Connection connection){
//        return new QueryDAOImpl(connection);
//    }
}
