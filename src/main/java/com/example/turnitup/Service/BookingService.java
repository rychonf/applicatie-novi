package com.example.turnitup.Service;

import com.example.turnitup.DTO.BookingDto;
import com.example.turnitup.DTO.MessageDto;
import com.example.turnitup.DTO.OrganisationDto;
import com.example.turnitup.Exception.RecordNotFoundException;
import com.example.turnitup.Model.Booking;
import com.example.turnitup.Model.DJ;
import com.example.turnitup.Model.Message;
import com.example.turnitup.Model.Organisation;
import com.example.turnitup.Repository.BookingRepository;
import com.example.turnitup.Repository.DJRepository;
import com.example.turnitup.Repository.OrganisationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DJRepository djRepository;
    private final OrganisationRepository organisationRepository;

    public BookingService(BookingRepository bookingRepository, DJRepository djRepository, OrganisationRepository organisationRepository) {
        this.bookingRepository = bookingRepository;
        this.djRepository = djRepository;
        this.organisationRepository = organisationRepository;
    }


    public List<BookingDto> getAllBookings(){
        List<Booking> bookingList = bookingRepository.findAll();
        List<BookingDto> bookingDtoList = new ArrayList<>();
        for (Booking booking : bookingList){
            bookingDtoList.add(fromBookingToDto(booking));
        }
        return bookingDtoList;
    }

    public BookingDto getBookingById(Long id){
        bookingRepository.findById(id).get();
        if(bookingRepository.findById(id).isPresent()){
            Booking booking = bookingRepository.findById(id).get();
            BookingDto dto = fromBookingToDto(booking);
            return dto;
        } else {
            throw new RecordNotFoundException("The booking with this ID doesn't exist");
        }
    }

    public BookingDto createBooking(BookingDto bookingDto, String dj, String organisation){
        Booking booking = fromDtoToBooking(bookingDto);
        if (djRepository.findByDjName(dj).isPresent()){
            booking.setDj(djRepository.findByDjName(dj).get());
        } else {
            throw new RecordNotFoundException("this DJ does not exist");
        }

        if (organisationRepository.findOrganisationByName(organisation).isPresent()){
            booking.setOrganisation(organisationRepository.findOrganisationByName(organisation).get());
        } else {
            throw new RecordNotFoundException("this Organisation does not exist");
        }

        Booking newBooking = bookingRepository.save(booking);
        BookingDto dto = fromBookingToDto(newBooking);
        return dto;
    }

    public BookingDto updateBooking(Long id, BookingDto bookingDto){
        if(bookingRepository.findById(id).isPresent()){
            Booking booking = bookingRepository.findById(id).get();

            Booking updateBooking = fromDtoToBooking(bookingDto);

            booking.setHoursBooked(updateBooking.getHoursBooked());
            booking.setDj(updateBooking.getDj());
            booking.setOrganisation(updateBooking.getOrganisation());
            booking.setTotalPrice(updateBooking.getTotalPrice());

            bookingRepository.save(booking);
            return fromBookingToDto(booking);
        } else {
            throw new RecordNotFoundException("The booking with this ID doesn't exist");
        }
    }



    public boolean deleteBookingById(Long id){
        if (bookingRepository.findById(id).isPresent()){
            bookingRepository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("This booking does not exist try another id");
        }
    }

    private BookingDto fromBookingToDto (Booking booking){
        BookingDto dto = new BookingDto();

        dto.setBookingDate(booking.getBookingDate());
        dto.setHoursBooked(booking.getHoursBooked());
        dto.setTotalPrice(booking.getTotalPrice());

        return dto;
    }

    private Booking fromDtoToBooking (BookingDto dto) {
        Booking booking = new Booking();

        booking.setBookingDate(dto.getBookingDate());
        booking.setHoursBooked(dto.getHoursBooked());
        booking.setTotalPrice(dto.getTotalPrice());

        return booking;
    }
}
