package tw.dd.spring123.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.dd.spring123.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
