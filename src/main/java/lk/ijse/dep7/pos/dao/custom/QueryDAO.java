package lk.ijse.dep7.pos.dao.custom;

import lk.ijse.dep7.pos.dao.SuperDAO;
import lk.ijse.dep7.pos.entity.CustomEntity;

import java.util.HashMap;
import java.util.List;

public interface QueryDAO extends SuperDAO {

    List<HashMap<String, Object>> findOrders(String query) throws Exception;

    long countOrders(String query) throws Exception;

    List<CustomEntity> findOrders(String query, int page, int size) throws Exception;
}
