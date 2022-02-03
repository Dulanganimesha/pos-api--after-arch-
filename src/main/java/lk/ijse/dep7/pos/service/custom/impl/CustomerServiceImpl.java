package lk.ijse.dep7.pos.service.custom.impl;

import lk.ijse.dep7.pos.dao.DAOFactory;
import lk.ijse.dep7.pos.dao.DAOType;
import lk.ijse.dep7.pos.dao.custom.CustomerDAO;
import lk.ijse.dep7.pos.dto.CustomerDTO;
import lk.ijse.dep7.pos.service.custom.CustomerService;

import java.util.List;

import static lk.ijse.dep7.pos.service.util.EntityDTOMapper.*;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerServiceImpl() {
        customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    }

    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {
        if (existCustomer(customer.getId())) {
            throw new RuntimeException(customer.getId() + " already exists");
        }
        customerDAO.save(fromCustomerDTO(customer));
    }

    @Override
    public long getCustomersCount() throws Exception {
        return customerDAO.count();
    }

    @Override
    public boolean existCustomer(String id) throws Exception {
        return customerDAO.existsById(id);
    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
        if (!existCustomer(customer.getId())) {
            throw new RuntimeException("There is no such customer associated with the id " + customer.getId());
        }
        customerDAO.update(fromCustomerDTO(customer));
    }

    @Override
    public void deleteCustomer(String id) throws Exception {
        if (!existCustomer(id)) {
            throw new RuntimeException("There is no such customer associated with the id " + id);
        }
        customerDAO.deleteById(id);
    }

    @Override
    public CustomerDTO findCustomer(String id) throws Exception {
        return toCustomerDTO(customerDAO.findById(id).orElseThrow(() -> new RuntimeException("There is no such customer associated with the id " + id)));
    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        return toCustomerDTOList(customerDAO.findAll());
    }

    @Override
    public List<CustomerDTO> findAllCustomers(int page, int size) throws Exception {
        return toCustomerDTOList(customerDAO.findAll(page, size));
    }

    @Override
    public String generateNewCustomerId() throws Exception {
        String id = customerDAO.getLastCustomerId();
        if (id != null) {
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        } else {
            return "C001";
        }

    }

}
