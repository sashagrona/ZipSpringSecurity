package net.bigmir.repositories;

import net.bigmir.model.SimpleUser;
import net.bigmir.model.Zip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZipRepository extends JpaRepository<Zip, Long> {

    @Query("SELECT z FROM Zip z WHERE z.user=:user")
    List<Zip> findMyZips(@Param("user")SimpleUser user);

    @Modifying
    @Query("DELETE FROM Zip z WHERE z.user=:user")
    void deleteAllByUser(@Param("user") SimpleUser user);
}
