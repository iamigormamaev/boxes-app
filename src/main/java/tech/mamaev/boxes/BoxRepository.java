package tech.mamaev.boxes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.mamaev.boxes.entity.Box;

@Repository
public interface BoxRepository extends CrudRepository<Box, Integer> {

}