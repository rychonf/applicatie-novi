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
public class MessageService {

    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageDto> getAllMessages(){
        List<Message> bookingList = messageRepository.findAll();
        List<MessageDto> bookingDtoList = new ArrayList<>();
        for (Message message : bookingList){
            bookingDtoList.add(fromMessageToDto(message));
        }
        return bookingDtoList;
    }

    public MessageDto getMessageById(Long id){
        bookingId(id);
        if(bookingId(id).isEmpty()){
            throw new RecordNotFoundException("Booking does not exist, try another id");
        } else {
            Message message = bookingId(id).get();
            MessageDto dto = fromMessageToDto(message);
            return dto;
        }
    }
    
    public MessageDto createMessage(MessageDto messageDto){
        Message message = toMessageFromDto(messageDto);
        
        Message newBooking = messageRepository.save(message);
        MessageDto dto = fromMessageToDto(newBooking);
        return dto;
    }

    public MessageDto updateMessage(Long id, MessageDto messageDto){
        if(messageRepository.findById(id).isPresent()){
            Message message = messageRepository.findById(id).get();

            Message updateMessage = toMessageFromDto(messageDto);

            message.setFinalDate(updateMessage.getFinalDate());

            messageRepository.save(message);
            return fromMessageToDto(message);
        } else {
            throw new RecordNotFoundException("Message with this id does not exist");
        }
    }

    public boolean deleteMessageById(Long id){
        if(bookingIdCheck(id)) {
            messageRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    private MessageDto fromMessageToDto(Message message){
        MessageDto dto = new MessageDto();
        dto.setMessage(message.getMessage());
        dto.setFinalDate(message.getFinalDate());
        return dto;
    }

    private Message toMessageFromDto(MessageDto dto){
        Message message = new Message();
        message.setMessage(dto.getMessage());
        message.setFinalDate(dto.getFinalDate());
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
