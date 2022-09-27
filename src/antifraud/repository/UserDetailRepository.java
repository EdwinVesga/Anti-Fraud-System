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

    @Query("delete from UserDetail u where u.username=:username")
    Optional<UserDetail> deleteByUsername(@Param("username") String username);

    List<UserDetail> findAll(Sort sort);

    @Query("select * from UserDetail u where u.username=:username")
    Optional<UserDetail> findByUsername(@Param("username") String username);
}
