package tw.dd.spring123.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.dd.spring123.dto.MessageDTO;
import tw.dd.spring123.entity.Message;
import tw.dd.spring123.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<MessageDTO> getHistoryMessages(Long roomId) {
        List<Message> messages = messageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);

        List<MessageDTO> dtoList = new ArrayList<>();
        for (Message msg : messages) {
            dtoList.add(new MessageDTO(
                    msg.getSender().getUsername(),
                    msg.getContent(),
                    msg.getCreatedAt()));
        }
        return dtoList;
    }
}
