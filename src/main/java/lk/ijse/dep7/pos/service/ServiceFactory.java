package lk.ijse.dep7.pos.service;

import lk.ijse.dep7.pos.service.custom.impl.CustomerServiceImpl;
import lk.ijse.dep7.pos.service.custom.impl.ItemServiceImpl;
import lk.ijse.dep7.pos.service.custom.impl.OrderServiceImpl;

public class ServiceFactory {

    private static ServiceFactory serviceFactory;

    private ServiceFactory(){

    }

    public static ServiceFactory getInstance() {
        return (serviceFactory == null)? (serviceFactory = new ServiceFactory()): serviceFactory;
    }

    public <T extends SuperService> T getService(ServiceType serviceType){
        switch (serviceType){
            case CUSTOMER:
                return (T) new CustomerServiceImpl();
            case ITEM:
                return (T) new ItemServiceImpl();
            case ORDER:
                return (T) new OrderServiceImpl();
            default:
                throw new RuntimeException("Invalid service");
        }
    }

}
