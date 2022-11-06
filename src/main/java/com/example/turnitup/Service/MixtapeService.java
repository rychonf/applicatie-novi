package com.example.turnitup.Service;

import com.example.turnitup.DTO.MixtapeDto;
import com.example.turnitup.Exception.RecordNotFoundException;
import com.example.turnitup.Model.Mixtape;
import com.example.turnitup.Repository.DocumentFileRepository;
import com.example.turnitup.Repository.MixtapeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MixtapeService {

    private final MixtapeRepository mixtapeRepository;
    private final DocumentFileRepository doc;

    public MixtapeService(MixtapeRepository mixtapeRepository, DocumentFileRepository doc) {
        this.mixtapeRepository = mixtapeRepository;
        this.doc = doc;
    }

    public List<MixtapeDto> getAllMixtapes(){
        List<Mixtape> mixtapeList = mixtapeRepository.findAll();
        List<MixtapeDto> mixtapeDtoList = new ArrayList<>();
        for (Mixtape mixtape : mixtapeList){
            mixtapeDtoList.add(fromMixtapeToDto(mixtape));
        }
        return mixtapeDtoList;
    }

    public MixtapeDto getMixtapeById(Long id){
        Optional<Mixtape> oneMixtape = mixtapeRepository.findById(id);
        if(oneMixtape.isEmpty()){
            throw new RecordNotFoundException("Mixtape does not exist, try another id");
        } else {
            Mixtape mixtape = oneMixtape.get();
            MixtapeDto dto = fromMixtapeToDto(mixtape);
            return dto;
        }
    }

    public MixtapeDto createMixtape(MixtapeDto mixtapeDto){
        Mixtape mixtape = toMixtapeFromDto(mixtapeDto);

        Mixtape newMixtape = mixtapeRepository.save(mixtape);
        MixtapeDto dto = fromMixtapeToDto(newMixtape);
        return dto;
    }

    public Mixtape playMixtape(Long id, MixtapeDto mixtapeDto){
        if(doc.findById(id).isPresent()){
            Mixtape mixtape = doc.findById(id).get();

            Mixtape updateTimesPlayed = toMixtapeFromDto(mixtapeDto);

            updateTimesPlayed.setId(mixtape.getId());

            mixtape.setTimesPlayed(timesMixtapePlayed(mixtapeDto));

            return doc.save(mixtape);

        } else {
            throw new RecordNotFoundException("No mixtape found with this id");
        }
    }

    public int timesMixtapePlayed(MixtapeDto mixtapeDto){
        int timesPlayed = mixtapeDto.getTimesPlayed();
        mixtapeDto.setTimesPlayed(10);

        return timesPlayed;
    }

    private MixtapeDto fromMixtapeToDto(Mixtape mixtape){
        MixtapeDto dto = new MixtapeDto();

        dto.setId(mixtape.getId());
        dto.setFileName(mixtape.getFileName());
        dto.setDateUploaded(mixtape.getDateUploaded());
        dto.setTimesPlayed(mixtape.getTimesPlayed());
        return dto;
    }

    private Mixtape toMixtapeFromDto (MixtapeDto dto){
        Mixtape mixtape = new Mixtape();

        mixtape.setId(dto.getId());
        mixtape.setDateUploaded(dto.getDateUploaded());
        mixtape.setTimesPlayed(mixtape.getTimesPlayed());
        return mixtape;
    }
}
