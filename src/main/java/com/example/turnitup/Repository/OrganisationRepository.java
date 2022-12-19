package com.example.turnitup.Repository;

import com.example.turnitup.Model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    Optional <Organisation> findOrganisationByName(String organisationName);
}
