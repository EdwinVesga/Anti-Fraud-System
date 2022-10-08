package antifraud.repository;

import antifraud.entity.declined.SuspiciousIp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuspiciousIpRepository extends CrudRepository<SuspiciousIp, Long> {
    Optional<SuspiciousIp> findByIp(String ipAddress);

    List<SuspiciousIp> findAll();
}
