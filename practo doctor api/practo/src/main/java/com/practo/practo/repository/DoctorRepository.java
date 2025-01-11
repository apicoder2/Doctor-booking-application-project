package com.practo.practo.repository;

import com.practo.practo.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Combined search with custom query
    @Query("SELECT d FROM Doctor d WHERE " +
            "(:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:specialization IS NULL OR LOWER(d.specialization) LIKE LOWER(CONCAT('%', :specialization, '%'))) AND " +
            "(:hospital IS NULL OR LOWER(d.hospital) LIKE LOWER(CONCAT('%', :hospital, '%'))) AND " +
            "(:experience IS NULL OR d.experience >= :experience)")
    List<Doctor> searchDoctors(@Param("name") String name,
                               @Param("specialization") String specialization,
                               @Param("hospital") String hospital,
                               @Param("experience") Integer experience);
}
