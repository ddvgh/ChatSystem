package tw.dd.spring123.dto;

import java.time.LocalDateTime;

public record MessageDTO(String senderName, String content, LocalDateTime createdAt) {
    
}