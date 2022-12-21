package com.example.turnitup.Controller;

import com.example.turnitup.DTO.DJDto;
import com.example.turnitup.Service.BookingService;
import com.example.turnitup.Service.DJService;
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
@RequestMapping(value = "/dj")
public class DJController {

    private final DJService djService;

    private final BookingService bookingService;

    @Autowired
    public DJController(DJService djService, BookingService bookingService) {
        this.djService = djService;
        this.bookingService = bookingService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<DJDto>> getAllDJs(){
        List<DJDto> djDtoList = djService.getAllDJs();
        if(djDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(djDtoList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DJDto> getDJById(@PathVariable Long id){
        Optional<DJDto> djDto = Optional.ofNullable(djService.getDJById(id));
        if (djDto.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(djDto.get(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createDJ(@Valid @RequestBody DJDto djDto, BindingResult br) {
        StringBuilder sb = new StringBuilder();
        if (br.hasErrors()){
            for (FieldError fieldError : br.getFieldErrors()) {
                sb.append(fieldError.getField()).append(": ");
                sb.append(fieldError.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            DJDto newDJDto = djService.createDJ(djDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newDJDto.getDjName()).toUri();
            return ResponseEntity.created(location).build();
        }
    }



    @PutMapping(value = "/{id}")
    public ResponseEntity<DJDto> updateDJ(@PathVariable Long id, @RequestBody DJDto djDto){

        DJDto dto = djService.updateDJ(id, djDto);

        return ResponseEntity.ok().body(dto);

        }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DJDto> deleteDJ(@PathVariable Long id){
        if(djService.deleteDJById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
