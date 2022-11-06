package com.example.turnitup.Repository;

import com.example.turnitup.Model.MixtapeReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MixtapeReactionRepository extends JpaRepository<MixtapeReaction, Long> {

}
