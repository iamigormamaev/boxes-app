package tech.mamaev.boxes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.mamaev.boxes.entity.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {

}