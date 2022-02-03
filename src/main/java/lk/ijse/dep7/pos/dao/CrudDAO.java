package lk.ijse.dep7.pos.dao;

import lk.ijse.dep7.pos.entity.SuperEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudDAO<T extends SuperEntity, ID extends Serializable> extends SuperDAO {

    void save(T entity) throws Exception;

    void update(T entity) throws Exception;

    void deleteById(ID key) throws Exception;

    Optional<T> findById(ID key) throws Exception;

    List<T> findAll() throws Exception;

    long count() throws Exception;

    boolean existsById(ID key) throws Exception;

    List<T> findAll(int page, int size) throws Exception;

}
