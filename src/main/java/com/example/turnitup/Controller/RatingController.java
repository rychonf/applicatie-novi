package com.example.turnitup.Controller;

import com.example.turnitup.DTO.RatingDto;
import com.example.turnitup.Service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/rating")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<RatingDto>> getAllRatings(){
        List<RatingDto> ratingDtoList = ratingService.getAllRatings();
        if(ratingDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ratingDtoList, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createRating(@Valid @RequestBody RatingDto ratingDto, @RequestParam(name = "djName") String djName, BindingResult br) {
        StringBuilder sb = new StringBuilder();
        if (br.hasErrors()){
            for (FieldError fieldError : br.getFieldErrors()) {
                sb.append(fieldError.getField()).append(": ");
                sb.append(fieldError.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            RatingDto newRatingDto = ratingService.createRating(ratingDto, djName);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{djName}")
                    .buildAndExpand(newRatingDto.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @PutMapping(value = "update/{djName}")
    public ResponseEntity<RatingDto> updateRating(@PathVariable Long id, @RequestBody RatingDto ratingDto) {

        RatingDto dto = ratingService.updateRating(id, ratingDto);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<RatingDto> deleteRating(@PathVariable Long id){
        if(ratingService.deleteRatingById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
