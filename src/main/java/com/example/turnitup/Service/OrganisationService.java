package com.example.turnitup.Service;

import com.example.turnitup.DTO.OrganisationDto;
import com.example.turnitup.Exception.OrganisationNotFoundException;
import com.example.turnitup.Model.Organisation;
import com.example.turnitup.Repository.OrganisationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    public List<OrganisationDto> getAllOrganisations(){
        List<Organisation> organisationList = organisationRepository.findAll();
        List<OrganisationDto> organisationDtoList = new ArrayList<>();
        for (Organisation organisation : organisationList){
            organisationDtoList.add(fromOrganisationToDto(organisation));
        }
        return organisationDtoList;
    }

    public OrganisationDto getOrganisationById(Long id){
        if(organisationRepository.findById(id).isPresent()){
            Organisation organisation = organisationRepository.findById(id).get();
            OrganisationDto dto = fromOrganisationToDto(organisation);
            return dto;
        } else {
            throw new OrganisationNotFoundException("The organisation with this id doesn't exist");
        }
    }

    public OrganisationDto createOrganisation(OrganisationDto organisationDto){
        Organisation organisation = toOrganisationFromDto(organisationDto);

        Organisation newOrganisation = organisationRepository.save(organisation);
        OrganisationDto dto = fromOrganisationToDto(newOrganisation);
        return dto;
    }

    public OrganisationDto updateOrganisation(Long id, OrganisationDto organisationDto){
        if(organisationRepository.findById(id).isPresent()){
            Organisation organisation = organisationRepository.findById(id).get();

            Organisation updateOrganisation = toOrganisationFromDto(organisationDto);

            organisation.setName(updateOrganisation.getName());
            organisation.setOrganizedEvents(updateOrganisation.getOrganizedEvents());

            organisationRepository.save(organisation);
            return fromOrganisationToDto(organisation);
        } else {
            throw new OrganisationNotFoundException("The organisation with this id doesn't exist");
        }
    }

    public boolean deleteOrganisationById (Long id){
        if(organisationRepository.findById(id).isPresent()){
            organisationRepository.deleteById(id);
            return true;
        } else {
            throw new OrganisationNotFoundException("The organisation with this id doesn't exist");
        }
    }

    private OrganisationDto fromOrganisationToDto (Organisation organisation){
        OrganisationDto dto = new OrganisationDto();

        dto.setName(organisation.getName());
        dto.setOrganizedEvents(organisation.getOrganizedEvents());
        return dto;
    }

    private Organisation toOrganisationFromDto (OrganisationDto dto){
        Organisation organisation = new Organisation();

        organisation.setName(dto.getName());
        organisation.setOrganizedEvents(dto.getOrganizedEvents());
        return organisation;
    }
}
