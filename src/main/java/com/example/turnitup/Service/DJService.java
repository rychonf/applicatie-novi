package com.example.turnitup.Service;

import com.example.turnitup.DTO.DJDto;
import com.example.turnitup.Exception.RecordNotFoundException;
import com.example.turnitup.Model.DJ;
import com.example.turnitup.Repository.DJRepository;
import com.example.turnitup.Repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DJService {


    private final DJRepository djRepository;

    private final RatingRepository ratingRepository;

    public DJService(DJRepository djRepository, RatingRepository ratingRepository) {
        this.djRepository = djRepository;
        this.ratingRepository = ratingRepository;
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

            dj.setDjName(updateDJ.getDjName());
            dj.setMusicSpecialty(updateDJ.getMusicSpecialty());
            dj.setPricePerHour(updateDJ.getPricePerHour());


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
