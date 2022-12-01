package com.example.turnitup.Controller;

import com.example.turnitup.DTO.MessageDto;
import com.example.turnitup.Service.MessageService;
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
@RequestMapping(value = "/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<MessageDto>> getAllBookings(){
        List<MessageDto> messageDtoList = messageService.getAllMessages();
        if(messageDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MessageDto> getBookingById(@PathVariable Long id){
        Optional<MessageDto> bookAnDJDto = Optional.ofNullable(messageService.getMessageById(id));
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
            MessageDto newBookingDto = messageService.createMessage(messageDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newBookingDto.getMessage()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<MessageDto> updateMessage(@PathVariable Long id, @RequestBody MessageDto messageDto){
        MessageDto dto = messageService.updateMessage(id, messageDto);
        return ResponseEntity.ok().body(dto);
    }


    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<MessageDto> deleteBooking(@PathVariable Long id){
        if (messageService.deleteMessageById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
