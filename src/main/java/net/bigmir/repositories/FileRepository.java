package net.bigmir.repositories;

import net.bigmir.model.DBFile;
import net.bigmir.model.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<DBFile, Long> {

    @Query("SELECT f FROM DBFile f WHERE f.zip IN (SELECT z.id FROM Zip z WHERE z.user=:user)")
    List<DBFile> findAllFilesByUser(@Param("user") SimpleUser user);

    @Modifying
    @Query("delete FROM DBFile f WHERE f.zip IN (SELECT z.id FROM Zip z WHERE z.user=:user)")
    void deleteAllByZip(@Param("user") SimpleUser user);
}
