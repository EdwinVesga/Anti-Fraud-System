package antifraud.repository;

import antifraud.entity.UserDetail;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    UserDetail save(UserDetail userDetail);

    int deleteByUsernameIgnoreCase(String username);

    List<UserDetail> findAll(Sort sort);

    Optional<UserDetail> findByUsernameIgnoreCase(String username);

    @Query("select u from UserDetail u where u.id = 1")
    Optional<UserDetail> findFirstUser();
}
