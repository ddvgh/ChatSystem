package tw.dd.spring123.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "messages")
public class Message {
	
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name ="sender_id")
    private Long  senderId;
    
    @Column(name ="session_id")
    private String  sessionId;
    
    @Column(name ="room_id")
    private Long roomId;
    
    @Column(name ="content")
    private String content;
    
    @Column(name = "sent_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    // getters/setters
    

    public Message() {}  // JPA 需要無參構造

    public Message(Long senderId, String  sessionId, Long roomId, String content) {
        this.senderId = senderId;
        this.sessionId = sessionId;
        this.roomId = roomId;
        this.content = content;
    }
}