package com.example.turnitup.Controller;

import com.example.turnitup.DTO.OrganisationDto;
import com.example.turnitup.Service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/organisation")
public class OrganisationController {

    private final OrganisationService organisationService;

    @Autowired
    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<OrganisationDto>> getAllOrganisations(){
        List<OrganisationDto> organisationDtoList = organisationService.getAllOrganisations();
        if(organisationDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(organisationDtoList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrganisationDto> getOrganisationById(@PathVariable Long id){
        Optional<OrganisationDto> organisationDto = Optional.ofNullable(organisationService.getOrganisationById(id));
        if (organisationDto.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(organisationDto.get(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createOrganisation(@Valid @RequestBody OrganisationDto organisationDto, BindingResult br){
        StringBuilder sb = new StringBuilder();
        if (br.hasErrors()){
            for (FieldError fieldError : br.getFieldErrors()) {
                sb.append(fieldError.getField()).append(": ");
                sb.append(fieldError.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            OrganisationDto newOrganisationDto = organisationService.createOrganisation(organisationDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newOrganisationDto.getName()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<OrganisationDto> updateOrganisation(@PathVariable Long id, @RequestBody OrganisationDto organisationDto){

        OrganisationDto dto = organisationService.updateOrganisation(id, organisationDto);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<OrganisationDto> deleteOrganisation(@PathVariable Long id){
        if(organisationService.deleteOrganisationById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
