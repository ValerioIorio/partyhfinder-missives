package com.personal.app.services.impl;

import com.partyh.finder.common.exception.impl.PFIdNullException;
import com.partyh.finder.common.exception.impl.PFNotFoundException;
import com.partyh.finder.common.utils.FilterUtil;
import com.partyh.finder.common.validators.commons.ValidationUtils;
import com.personal.app.model.ChatDTO;
import com.personal.app.model.ChatPageDTO;
import com.personal.app.model.MissiveDTO;
import com.personal.app.models.entities.Chat;
import com.personal.app.models.filters.ChatFilter;
import com.personal.app.repository.ChatRepository;
import com.personal.app.services.ChatService;
import com.personal.app.specifications.ChatSpecification;
import com.personal.app.validators.ChatValidator;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService { //TODO: implement chat admin
    @Override
    public ChatDTO updateChat(ChatDTO chatDTO) {
        //validate Id
        chatValidator.validateId(chatDTO.getId(), HttpMethod.PUT);
        //get the optional object of the chat
        Optional<Chat> optionalChat = chatRepository.findById(chatDTO.getId());
        //validate
        chatValidator.validate(chatDTO, HttpMethod.PUT, new LinkedHashMap<>(Map.of("chat", optionalChat.isPresent())));
        //get the previous chat
        Chat previousChat = optionalChat.get();
        //get the new chat
        Chat chat = mapper.map(chatDTO, Chat.class);
        //set the new values
        chat.setUpdatedAt(LocalDateTime.now());
        chat.setName(chatDTO.getName());
        //users and missives of this cat cannot be updated with this method
        chat.setUserStringIds(previousChat.getUserStringIds());
        chat.setMissives(previousChat.getMissives());
        chat.setCreatedAt(previousChat.getCreatedAt());
        //save the chat
        return mapper.map(chatRepository.save(chat), ChatDTO.class);
    }

    @Override
    public ChatDTO getChat(Long id) {
        Optional<Chat> chatOptional = chatRepository.findById(id);
        if(chatOptional.isPresent()){
            return mapper.map(chatOptional.get(), ChatDTO.class);
        }else {
            throw new NoSuchElementException("Chat not found");
        }
    }

    @Override
    public ChatPageDTO findAllChat(ChatFilter chatFilter) {
        //set default values for sorting
        if(!chatFilter.checkValidSortBy(ALLOWED_SORT_BY)){
            chatFilter.setSortBy(DEFAULT_SORT_BY);
        }
        if(ValidationUtils.isNull(chatFilter.getDescending())){
            chatFilter.setDescending(DEFAULT_DESCENDING_SORT_ORDER);
        }
        //get the page of chats
        Page<Chat> chatPage = chatRepository.findAll(
                chatSpecification.buildSpecification(chatFilter),
                PageRequest.of(FilterUtil.getNumberOfPage(chatFilter.getNumberOfPage()), FilterUtil.getPageSize(chatFilter.getPageSize()))
                        .withSort(FilterUtil.getDirection(chatFilter.getDescending()), FilterUtil.getSortBy(chatFilter.getSortBy()))
        );

        //map the page of chats to a page of chatDTOs
        ChatPageDTO pageDTO = new ChatPageDTO();
        pageDTO.setItems(chatPage.getContent().stream().map(chat -> mapper.map(chat, ChatDTO.class)).collect(Collectors.toList()));
        pageDTO.setTotalItems(chatPage.getTotalElements());
        pageDTO.setItemsPerPage(chatPage.getSize());
        pageDTO.setCurrentPage(chatPage.getNumber());
        pageDTO.setTotalPages(chatPage.getTotalPages());
        return pageDTO;
    }

    @Override
    public void deleteChat(Long id) {
        Optional<Chat> chatOptional = chatRepository.findById(id);
        if(chatOptional.isPresent()){
            chatRepository.deleteById(id);
        }else {
            throw new NoSuchElementException("Chat not found");
        }
    }

    @Override
    public ChatDTO addChat(ChatDTO chatDTO) {
        //validate Id
        chatValidator.validateId(chatDTO.getId(), HttpMethod.POST);
        //validate
        chatValidator.validate(chatDTO, HttpMethod.POST);
        //la validazione sugli user è fatta da partyhfinder-users
        Chat chat = mapper.map(chatDTO, Chat.class);

        //set default value
        chat.setCreatedAt(LocalDateTime.now());
        chat.setUpdatedAt(LocalDateTime.now());
        chat.setMissives(null);

        //save chat
        return mapper.map(chatRepository.save(chat), ChatDTO.class);
    }

    @Value("${chat.default.sort.by}")
    private String DEFAULT_SORT_BY;

    @Value("${chat.allowed.sort.by}")
    private String[] ALLOWED_SORT_BY;

    @Value("${chat.default.descending.sort.order}")
    private boolean DEFAULT_DESCENDING_SORT_ORDER;

    private final ModelMapper mapper;
    private final ChatRepository chatRepository;
    private final ChatValidator chatValidator;
    private final ChatSpecification chatSpecification;
}
