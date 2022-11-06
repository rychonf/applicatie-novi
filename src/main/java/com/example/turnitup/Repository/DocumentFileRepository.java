package com.example.turnitup.Repository;

import com.example.turnitup.Model.Mixtape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentFileRepository extends JpaRepository<Mixtape, Long> {

    Mixtape findByFileName(String fileName);
}
