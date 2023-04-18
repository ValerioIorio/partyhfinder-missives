package com.personal.app.resources;

import com.personal.app.model.ChatDTO;
import com.personal.app.model.ChatPageDTO;
import com.personal.app.models.filters.ChatFilter;
import com.personal.app.services.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@Log4j2
public class ChatController implements ChatApi{

    @Override
    public ResponseEntity<ChatDTO> addChat(ChatDTO chatDTO) {
        return ResponseEntity.ok(chatService.addChat(chatDTO));
    }

    @Override
    public ResponseEntity<Void> deleteChat(Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ChatPageDTO> findAllChat(Integer pageSize, Integer numberOfPage, Boolean descending, String sortBy, String name) {
        return ResponseEntity.ok(chatService.findAllChat(new ChatFilter(pageSize, numberOfPage, descending, sortBy, name)));
    }

    @Override
    public ResponseEntity<ChatDTO> getChat(Long id) {
        return ResponseEntity.ok(chatService.getChat(id));
    }

    @Override
    public ResponseEntity<ChatDTO>updateChat(ChatDTO chatDTO) {
        return ResponseEntity.ok(chatService.updateChat(chatDTO));
    }

    private final ChatService chatService;
}
