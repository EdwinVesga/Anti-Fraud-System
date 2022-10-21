package antifraud.repository;

import antifraud.entity.api.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAll();

    List<Transaction> findAllByNumber(String number);

    @Query("select count(distinct t.region) from Transaction t where t.number = :cardNumber and t.region <> :region and t.date between :fromDate and :toDate")
    Long countByNumberAfterTime(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate, @Param("cardNumber") String number, @Param("region") String region);

    @Query("select count(distinct t.ip) from Transaction t where t.number = :cardNumber and t.ip <> :ip and t.date between :fromDate and :toDate")
    Long countByIpAfterTime(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate, @Param("cardNumber") String number, @Param("ip") String ip);
}
