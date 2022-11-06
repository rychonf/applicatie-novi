package com.example.turnitup.Service;

import com.example.turnitup.DTO.RatingDto;
import com.example.turnitup.Exception.RecordNotFoundException;
import com.example.turnitup.Model.Rating;
import com.example.turnitup.Repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<RatingDto> getAllRatings(){
        List<Rating> ratingList = ratingRepository.findAll();
        List<RatingDto> ratingDtoList = new ArrayList<>();
        for(Rating rating : ratingList){
            ratingDtoList.add(fromRating(rating));
        }
        return ratingDtoList;
    }

    public RatingDto getRatingById(Long id){
        Optional<Rating> oneRating = ratingRepository.findById(id);
        if(oneRating.isEmpty()){
            throw new RecordNotFoundException("Rating does not exist, try another id");
        } else {
            Rating rating = oneRating.get();
            RatingDto dto = fromRating(rating);
            return dto;
        }
    }

    public RatingDto createRating(RatingDto ratingDto){
        Rating rating = toRating(ratingDto);

        Rating newRating = ratingRepository.save(rating);
        RatingDto dto = fromRating(newRating);
        return dto;
    }

    private RatingDto fromRating(Rating rating){
        RatingDto dto = new RatingDto();

        dto.setId(rating.getId());
        dto.setDateRated(rating.getDateRated());
        dto.setRating(rating.getRating());
        return dto;
    }

    private Rating toRating(RatingDto dto){
        Rating rating = new Rating();

        rating.setId(dto.getId());
        rating.setDateRated(dto.getDateRated());
        rating.setRating(dto.getRating());
        return rating;
    }

}
