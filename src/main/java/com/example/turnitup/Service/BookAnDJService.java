package com.example.turnitup.Service;

import com.example.turnitup.DTO.MessageDto;
import com.example.turnitup.Exception.RecordNotFoundException;
import com.example.turnitup.Model.Message;
import com.example.turnitup.Repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookAnDJService {

    MessageRepository messageRepository;

    public BookAnDJService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageDto> getAllBookings(){
        List<Message> bookingList = messageRepository.findAll();
        List<MessageDto> bookingDtoList = new ArrayList<>();
        for (Message message : bookingList){
            bookingDtoList.add(fromBooking(message));
        }
        return bookingDtoList;
    }

    public MessageDto getBookingById(Long id){
        bookingId(id);
        if(bookingId(id).isEmpty()){
            throw new RecordNotFoundException("Booking does not exist, try another id");
        } else {
            Message message = bookingId(id).get();
            MessageDto dto = fromBooking(message);
            return dto;
        }
    }
    
    public MessageDto createBooking(MessageDto messageDto){
        Message message = toBooking(messageDto);
        
        Message newBooking = messageRepository.save(message);
        MessageDto dto = fromBooking(newBooking);
        return dto;
    }

    public boolean deleteBookingById(Long id){
        if(bookingIdCheck(id)) {
            messageRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    private MessageDto fromBooking(Message message){
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setMessage(message.getMessage());
        dto.setBookingDate(message.getDate());
        return dto;
    }

    private Message toBooking (MessageDto dto){
        Message message = new Message();
        message.setId(dto.getId());
        message.setMessage(dto.getMessage());
        message.setDate(dto.getBookingDate());
        return message;
    }

    // A method that checks if the id is in the database
    public boolean bookingIdCheck (long id){
        if (bookingId(id).isPresent()){
            return true;
        } else {
            throw new RecordNotFoundException("Booking id is not found");
        }
    }

    // A method that gets the booking id and can be called whenever you need a booking id
    public Optional<Message> bookingId (Long id){
        Optional<Message> booking = messageRepository.findById(id);
        return booking;
    }

}
