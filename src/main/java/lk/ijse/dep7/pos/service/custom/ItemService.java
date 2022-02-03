package lk.ijse.dep7.pos.service.custom;

import lk.ijse.dep7.pos.dto.ItemDTO;
import lk.ijse.dep7.pos.service.SuperService;

import java.util.List;

public interface ItemService extends SuperService {

    void saveItem(ItemDTO item) throws Exception;

    boolean existItem(String code) throws Exception;

    void updateItem(ItemDTO item) throws Exception;

    void deleteItem(String code) throws Exception;

    ItemDTO findItem(String code) throws Exception;

    List<ItemDTO> findAllItems() throws Exception;

    List<ItemDTO> findAllItems(int page, int size) throws Exception;

    String generateNewItemCode() throws Exception;
}
