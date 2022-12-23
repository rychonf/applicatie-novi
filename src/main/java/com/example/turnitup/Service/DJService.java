package com.example.turnitup.Service;

import com.example.turnitup.DTO.DJDto;
import com.example.turnitup.Exception.DJNotFoundException;
import com.example.turnitup.Model.DJ;
import com.example.turnitup.Repository.DJRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DJService {


    private final DJRepository djRepository;


    public DJService(DJRepository djRepository) {
        this.djRepository = djRepository;

    }

    public List<DJDto> getAllDJs(){
        List<DJ> djList = djRepository.findAll();
        List<DJDto> djDtoList = new ArrayList<>();
        for (DJ dj : djList){
            djDtoList.add(fromDJToDto(dj));
        }
        return djDtoList;
    }

    public DJDto getDJById(Long id){
        djRepository.findById(id).get();
        if(djRepository.findById(id).isPresent()){
            DJ dj = djRepository.findById(id).get();
            DJDto dto = fromDJToDto(dj);
            return dto;
        } else {

            throw new DJNotFoundException("Dj does not exist, try another id");
        }
    }

    public DJDto createDJ(DJDto djDto){
        DJ dj = toDJFromDto(djDto);

        DJ newDJ = djRepository.save(dj);
        DJDto dto = fromDJToDto(newDJ);
        return dto;
    }


    public DJDto updateDJ(Long id, DJDto djDto){
        if(djRepository.findById(id).isPresent()){
            DJ dj = djRepository.findById(id).get();

            DJ updateDJ = toDJFromDto(djDto);

            dj.setDjName(updateDJ.getDjName());
            dj.setMusicSpecialty(updateDJ.getMusicSpecialty());
            dj.setPricePerHour(updateDJ.getPricePerHour());


            djRepository.save(dj);
            return fromDJToDto(dj);
        } else {
            throw new DJNotFoundException("The dj with this id doesn't exist");
        }
    }


    public boolean deleteDJById(Long id){
        if(djRepository.findById(id).isPresent()){
            djRepository.deleteById(id);
            return true;
        } else {
            throw new DJNotFoundException("The dj with this id doesn't exist");
        }
    }

    private DJDto fromDJToDto (DJ dj){
        DJDto dto = new DJDto();

        dto.setDjName(dj.getDjName());
        dto.setMusicSpecialty(dj.getMusicSpecialty());
        dto.setPricePerHour(dj.getPricePerHour());

        return dto;
    }

    private DJ toDJFromDto(DJDto dto){
        DJ dj = new DJ();

        dj.setDjName(dto.getDjName());
        dj.setMusicSpecialty(dto.getMusicSpecialty());
        dj.setPricePerHour(dto.getPricePerHour());

        return dj;
    }

}
