package lk.ijse.dep7.pos.service.custom;

import lk.ijse.dep7.pos.dto.CustomerDTO;
import lk.ijse.dep7.pos.service.SuperService;

import java.util.List;

public interface CustomerService extends SuperService {

    void saveCustomer(CustomerDTO customer) throws Exception;

    long getCustomersCount() throws Exception;

    boolean existCustomer(String id) throws Exception;

    void updateCustomer(CustomerDTO customer) throws Exception;

    void deleteCustomer(String id) throws Exception;

    CustomerDTO findCustomer(String id) throws Exception;

    List<CustomerDTO> findAllCustomers() throws Exception;

    List<CustomerDTO> findAllCustomers(int page, int size) throws Exception;

    String generateNewCustomerId() throws Exception;

}
