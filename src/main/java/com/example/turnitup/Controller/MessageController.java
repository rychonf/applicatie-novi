package com.example.turnitup.Controller;

import com.example.turnitup.DTO.MessageDto;
import com.example.turnitup.Service.BookAnDJService;
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
@RequestMapping(value = "/booking")
public class MessageController {

    private final BookAnDJService bookAnDJService;

    @Autowired
    public MessageController(BookAnDJService bookAnDJService) {
        this.bookAnDJService = bookAnDJService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<MessageDto>> getAllBookings(){
        List<MessageDto> messageDtoList = bookAnDJService.getAllBookings();
        if(messageDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MessageDto> getBookingById(@PathVariable Long id){
        Optional<MessageDto> bookAnDJDto = Optional.ofNullable(bookAnDJService.getBookingById(id));
        if (bookAnDJDto.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookAnDJDto.get(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createBooking(@Valid @RequestBody MessageDto messageDto, BindingResult br) {
        StringBuilder sb = new StringBuilder();
        if (br.hasErrors()){
            for (FieldError fieldError : br.getFieldErrors()) {
                sb.append(fieldError.getField()).append(": ");
                sb.append(fieldError.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            MessageDto newBookingDto = bookAnDJService.createBooking(messageDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newBookingDto.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<MessageDto> deleteBooking(@PathVariable Long id){
        if (bookAnDJService.deleteBookingById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
