package com.example.turnitup.Service;

import com.example.turnitup.DTO.RatingDto;
import com.example.turnitup.Exception.*;
import com.example.turnitup.Model.Rating;
import com.example.turnitup.Repository.BookingRepository;
import com.example.turnitup.Repository.DJRepository;
import com.example.turnitup.Repository.OrganisationRepository;
import com.example.turnitup.Repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    RatingRepository ratingRepository;
    DJRepository djRepository;
    OrganisationRepository organisationRepository;
    private final BookingRepository bookingRepository;

    BookingService bookingService;

    public RatingService(RatingRepository ratingRepository, DJRepository djRepository, OrganisationRepository organisationRepository,
                         BookingRepository bookingRepository) {
        this.ratingRepository = ratingRepository;
        this.djRepository = djRepository;
        this.organisationRepository = organisationRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<RatingDto> getAllRatings() {
        List<Rating> ratingList = ratingRepository.findAll();
        List<RatingDto> ratingDtoList = new ArrayList<>();
        for (Rating rating : ratingList) {
            ratingDtoList.add(fromRating(rating));
        }
        return ratingDtoList;
    }

    public RatingDto getRatingById(Long id) {
        Optional<Rating> oneRating = ratingRepository.findById(id);
        if (oneRating.isEmpty()) {
            throw new RecordNotFoundException("Rating does not exist, try another id");
        } else {
            Rating rating = oneRating.get();
            RatingDto dto = fromRating(rating);
            return dto;
        }
    }

    public RatingDto createRating(RatingDto ratingDto, String dj, Long bookingId) {
        Rating rating = toRating(ratingDto);

        if (bookingRepository.findById(bookingId).isPresent()) {

            if (djRepository.findByDjName(dj).isPresent()) {
                rating.setDj(djRepository.findByDjName(dj).get());

                LocalDate bookingDate = bookingRepository.findById(bookingId).get().getBookingDate();
                LocalDate dateToday = LocalDate.now();

                int ratingForDj = rating.getRating();

                if (ratingForDj >= 1 && ratingForDj <= 10) {


                    if (dateToday.isAfter(bookingDate)) {

                        Rating newRating = ratingRepository.save(rating);
                        RatingDto dto = fromRating(newRating);

                        return dto;
                    } else throw new RatingToEarlyException("The booking has not taken place yet, " +
                            "So its impossible to rate the dj his performance ");

                } else throw new RatingNotFoundException("The rating must be between 1 and 10");

            } else throw new DJNotFoundException("The dj with this name doesn't exist");

        } else throw new BookingNotFoundException("The booking with this id doesn't exist");
    }




    public boolean deleteRatingById(Long id){
        if(ratingRepository.findById(id).isPresent()){
            ratingRepository.deleteById(id);
            return true;
        } else {
            throw new RatingNotFoundException("The rating with this id doesn't exist");
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
