package antifraud.repository;

import antifraud.entity.declined.SuspiciousIp;
import org.springframework.data.repository.CrudRepository;

public interface SuspiciousIpRepository extends CrudRepository<SuspiciousIp, Long> {
}
