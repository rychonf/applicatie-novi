package com.example.turnitup.Service;

import com.example.turnitup.DTO.RatingDto;
import com.example.turnitup.Exception.RecordNotFoundException;
import com.example.turnitup.Model.DJ;
import com.example.turnitup.Model.Rating;
import com.example.turnitup.Repository.DJRepository;
import com.example.turnitup.Repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    RatingRepository ratingRepository;
    DJRepository djRepository;

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

    public RatingDto createRating(RatingDto ratingDto, String djName){
        Rating rating = toRating(ratingDto);

        Rating newRating = ratingRepository.save(rating);
        RatingDto dto = fromRating(newRating);

        if(djRepository.findByDjName(djName).isPresent()) {
            DJ dj = djRepository.findByDjName(djName).get();

            dj.setRating(rating);
            djRepository.save(dj);
            return dto;
        } else throw new RecordNotFoundException("This DJ does not exist");
    }

    public boolean deleteRatingById(Long id){
        if(ratingIdCheck(id)){
            ratingRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public RatingDto updateRating(Long id, RatingDto ratingDto){
        if(ratingRepository.findById(id).isPresent()){
            Rating rating = ratingRepository.findById(id).get();

            Rating updateRating = toRating(ratingDto);

            rating.setRating(updateRating.getRating());

            ratingRepository.save(rating);
            return fromRating(rating);
        } else {
            throw new RecordNotFoundException("This rating does not exist");
        }
    }

    public boolean ratingIdCheck (Long id){
        if (ratingRepository.findById(id).isPresent()){
            return true;
        } else {
            throw new RecordNotFoundException("Rating id is not found");
        }
    }

    private RatingDto fromRating(Rating rating){
        RatingDto dto = new RatingDto();

        dto.setDateRated(rating.getDateRated());
        dto.setRating(rating.getRating());
        return dto;
    }

    private Rating toRating(RatingDto dto){
        Rating rating = new Rating();

        rating.setDateRated(dto.getDateRated());
        rating.setRating(dto.getRating());
        return rating;
    }

}
