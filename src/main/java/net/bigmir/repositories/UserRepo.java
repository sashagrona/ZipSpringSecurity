package net.bigmir.repositories;

import net.bigmir.model.SimpleUser;
import net.bigmir.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<SimpleUser,Long> {

    SimpleUser findByLogin(String login);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false " +
            "END FROM SimpleUser u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);

    List<SimpleUser> findAll();

    @Query("SELECT u.id FROM SimpleUser u WHERE u.role=:admin")
    List<Long> findByRole(@Param(value = "admin")UserRole role);
}
