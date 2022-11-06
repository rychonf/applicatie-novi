package com.example.turnitup.Service;

import com.example.turnitup.DTO.DJDto;
import com.example.turnitup.Exception.RecordNotFoundException;
import com.example.turnitup.Model.DJ;
import com.example.turnitup.Repository.DJRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        djId(id);
        if(djId(id).isEmpty()){
            throw new RecordNotFoundException("Dj does not exist, try another id");
        } else {
            DJ dj = djId(id).get();
            DJDto dto = fromDJToDto(dj);
            return dto;
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
            updateDJ.setId(dj.getId());

            dj.setName(updateDJ.getName());
            dj.setMusicSpecialty(updateDJ.getMusicSpecialty());
            dj.setPricePerHour(updateDJ.getPricePerHour());
            dj.setTypeOfSubscription(updateDJ.getTypeOfSubscription());

            djRepository.save(dj);
            return fromDJToDto(dj);
        } else {
            throw new RecordNotFoundException("No DJ was found with this id");
        }
    }


    public boolean deleteDJById(Long id){
        if(djIdCheck(id)){
            djRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // A method that gets the dj id and can be called whenever you need a dj id
    public Optional<DJ> djId (Long id){
        Optional<DJ> djId = djRepository.findById(id);
        return djId;
    }

    public boolean djIdCheck (Long id){
        if (djId(id).isPresent()){
            return true;
        } else {
            throw new RecordNotFoundException("DJ id is not found");
        }
    }


    private DJDto fromDJToDto (DJ dj){
        DJDto dto = new DJDto();

        dto.setId(dj.getId());
        dto.setName(dj.getName());
        dto.setMusicSpecialty(dj.getMusicSpecialty());
        dto.setPricePerHour(dj.getPricePerHour());
//        dto.setTypeOfSubscription(dj.getTypeOfSubscription());

        return dto;
    }

    private DJ toDJFromDto(DJDto dto){
        DJ dj = new DJ();

        dj.setId(dto.getId());
        dj.setName(dto.getName());
        dj.setMusicSpecialty(dto.getMusicSpecialty());
        dj.setPricePerHour(dto.getPricePerHour());
//        dj.setTypeOfSubscription(dto.getTypeOfSubscription());

        return dj;
    }

}
