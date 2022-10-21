package antifraud.repository;

import antifraud.entity.api.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LimitRepository extends CrudRepository<Limit, Long> {

    Optional<Limit> findByType(String type);
}
