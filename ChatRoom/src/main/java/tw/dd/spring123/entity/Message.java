package tw.dd.spring123.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "messages")
public class Message {
	
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)  // 外鍵
    private User sender;
    
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

    public Message(User sender, String  sessionId, Long roomId, String content) {
        this.sender = sender;
        this.sessionId = sessionId;
        this.roomId = roomId;
        this.content = content;
    }

}