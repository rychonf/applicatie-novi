package com.example.turnitup.Service;

import com.example.turnitup.DTO.OrganisationDto;
import com.example.turnitup.Exception.RecordNotFoundException;
import com.example.turnitup.Model.Organisation;
import com.example.turnitup.Repository.OrganisationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if(organisationId(id).isEmpty()){
            throw new RecordNotFoundException("Organisation does not exist, try another id");
        } else {
            Organisation organisation = organisationId(id).get();
            OrganisationDto dto = fromOrganisationToDto(organisation);
            return dto;
        }
    }

    public OrganisationDto createOrganisation(OrganisationDto organisationDto){
        Organisation organisation = toOrganisationFromDto(organisationDto);

        Organisation newOrganisation = organisationRepository.save(organisation);
        OrganisationDto dto = fromOrganisationToDto(newOrganisation);
        return dto;
    }

    public OrganisationDto updateOrganisation(Long id, OrganisationDto organisationDto){
        if(organisationId(id).isPresent()){
            Organisation organisation = organisationRepository.findById(id).get();

            Organisation updateOrganisation = toOrganisationFromDto(organisationDto);
            updateOrganisation.setId(organisation.getId());

            organisation.setName(updateOrganisation.getName());
            organisation.setOrganizedEvents(updateOrganisation.getOrganizedEvents());
            organisation.setTypeOfSubscription(updateOrganisation.getTypeOfSubscription());

            organisationRepository.save(organisation);
            return fromOrganisationToDto(organisation);
        } else {
            throw new RecordNotFoundException("An organisation with this id doesn't exists");
        }
    }

    public boolean deleteOrganisationById (Long id){
        if(organisationIdCheck(id)){
            organisationRepository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("An organisation with this id doesn't exists");
        }
    }

    public Optional<Organisation> organisationId (Long id){
        Optional<Organisation> organisationId = organisationRepository.findById(id);
        return organisationId;
    }

    public boolean organisationIdCheck (Long id){
        if (organisationId(id).isPresent()){
            return true;
        } else {
            throw new RecordNotFoundException("Organisation with this id doesn't exist");
        }
    }

    private OrganisationDto fromOrganisationToDto (Organisation organisation){
        OrganisationDto dto = new OrganisationDto();

        dto.setId(organisation.getId());
        dto.setName(organisation.getName());
        dto.setOrganizedEvents(organisation.getOrganizedEvents());
//        dto.setTypeOfSubscription(organisation.getTypeOfSubscription());
        return dto;
    }

    private Organisation toOrganisationFromDto (OrganisationDto dto){
        Organisation organisation = new Organisation();

        organisation.setId(dto.getId());
        organisation.setName(dto.getName());
        organisation.setOrganizedEvents(dto.getOrganizedEvents());
//        organisation.setTypeOfSubscription(dto.getTypeOfSubscription());
        return organisation;
    }
}
