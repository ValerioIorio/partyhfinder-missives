package com.personal.app.services;

import com.personal.app.model.ChatDTO;
import com.personal.app.model.ChatPageDTO;
import com.personal.app.models.filters.ChatFilter;

public interface ChatService {
    ChatDTO updateChat(ChatDTO chatDTO);

    ChatDTO getChat(Long id);

    ChatPageDTO findAllChat(ChatFilter chatFilter);

    void deleteChat(Long id);

    ChatDTO addChat(ChatDTO chatDTO);
}
