package de.szut.lf8_starter.projekt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjektRepository extends JpaRepository<ProjektEntity, Long> {

    @Query("SELECT p FROM ProjektEntity p WHERE :employeeId MEMBER OF p.mitarbeiterIds")
    List<ProjektEntity> findAllByEmployeeId(@Param("employeeId") Long employeeId);
}
