package antifraud.repository;

import antifraud.entity.declined.StolenCard;
import org.springframework.data.repository.CrudRepository;

public interface StolenCardRepository extends CrudRepository<StolenCard, Long> {
}
