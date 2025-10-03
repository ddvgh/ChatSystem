package tw.dd.spring123.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.dd.spring123.dto.MessageDTO;
import tw.dd.spring123.service.MessageService;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/messages/{roomId}")
    public ResponseEntity<List<MessageDTO>> loadHistory(@PathVariable Long roomId) {
        List<MessageDTO> history = messageService.getHistoryMessages(roomId);
        return ResponseEntity.ok(history);
    }
}
