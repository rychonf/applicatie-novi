package com.example.turnitup.Repository;

import com.example.turnitup.Model.DJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DJRepository extends JpaRepository<DJ, Long> {
    Optional<DJ> findByDjName(String djName);

}
