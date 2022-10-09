package antifraud.repository;

import antifraud.constant.UserRoleType;
import antifraud.entity.auth.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    Optional<UserRole> findByName(UserRoleType name);
}
