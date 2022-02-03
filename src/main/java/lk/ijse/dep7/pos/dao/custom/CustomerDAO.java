package lk.ijse.dep7.pos.dao.custom;

import lk.ijse.dep7.pos.dao.CrudDAO;
import lk.ijse.dep7.pos.entity.Customer;

public interface CustomerDAO extends CrudDAO<Customer, String> {

    String getLastCustomerId() throws Exception;
}
