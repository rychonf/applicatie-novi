package com.example.turnitup.Repository;

import com.example.turnitup.Model.DJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DJRepository extends JpaRepository<DJ, Long> {

}
