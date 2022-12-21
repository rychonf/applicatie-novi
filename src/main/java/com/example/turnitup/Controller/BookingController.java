package com.example.turnitup.Controller;

import com.example.turnitup.DTO.BookingDto;
import com.example.turnitup.Model.DJ;
import com.example.turnitup.Model.Organisation;
import com.example.turnitup.Service.BookingService;
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
@RequestMapping(value = "/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
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
                                                @RequestParam("dj") String dj,
                                                @RequestParam("organisation") String organisation,
                                                BindingResult br){
        StringBuilder sb = new StringBuilder();
        if (br.hasErrors()){
            for (FieldError fieldError : br.getFieldErrors()) {
                sb.append(fieldError.getField()).append(": ");
                sb.append(fieldError.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            BookingDto newBookingDto = bookingService.createBooking(bookingDto, dj, organisation);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newBookingDto.getClass()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id, @RequestBody BookingDto bookingDto,
                                                    String djName, String organisationName){

        BookingDto dto = bookingService.updateBooking(id, bookingDto, djName, organisationName);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BookingDto> deleteBooking(@PathVariable Long id){
        bookingService.deleteBookingById(id);
        return ResponseEntity.noContent().build();
    }



}
