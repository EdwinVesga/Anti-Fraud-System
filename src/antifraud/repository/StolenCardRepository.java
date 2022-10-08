package antifraud.repository;

import antifraud.entity.declined.StolenCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StolenCardRepository extends CrudRepository<StolenCard, Long> {
    Optional<StolenCard> findByCardNumber(String cardNumber);

    List<StolenCard> findAll();
}
