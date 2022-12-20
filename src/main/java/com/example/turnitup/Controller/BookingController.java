package com.example.turnitup.Controller;

import com.example.turnitup.DTO.BookingDto;
import com.example.turnitup.DTO.MessageDto;
import com.example.turnitup.DTO.OrganisationDto;
import com.example.turnitup.Model.DJ;
import com.example.turnitup.Model.Organisation;
import com.example.turnitup.Service.BookingService;
import com.example.turnitup.Service.DJService;
import com.example.turnitup.Service.OrganisationService;
import org.springframework.data.repository.query.Param;
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
@RequestMapping(name = "/booking")
public class BookingController {

    private final DJService djService;
    private final OrganisationService organisationService;
    private final BookingService bookingService;

    public BookingController(DJService djService, OrganisationService organisationService, BookingService bookingService) {
        this.djService = djService;
        this.organisationService = organisationService;
        this.bookingService = bookingService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<BookingDto>> getAllBookings(){
        List<BookingDto> bookingDtoList = bookingService.getAllBookings();

            return new ResponseEntity<>(bookingDtoList, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id){
        Optional<BookingDto> bookingDto = Optional.ofNullable(bookingService.getBookingById(id));

        return new ResponseEntity<>(bookingDto.get(), HttpStatus.OK);
    }


    @PostMapping(value = "")
    public ResponseEntity<Object> createBooking(@Valid @RequestBody BookingDto bookingDto,
                                                @RequestParam("djName") String djName ,
                                                @RequestParam("organisationName") String organisationName, BindingResult br) {
        StringBuilder sb = new StringBuilder();
        if (br.hasErrors()) {
            for (FieldError fieldError : br.getFieldErrors()) {
                sb.append(fieldError.getField()).append(": ");
                sb.append(fieldError.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            BookingDto newBookingDto = bookingService.createBooking(bookingDto, djName, organisationName);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newBookingDto.getClass()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id, @RequestBody BookingDto bookingDto){

        BookingDto dto = bookingService.updateBooking(id, bookingDto);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BookingDto> deleteBooking(@PathVariable Long id){
        if(bookingService.deleteBookingById(id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
