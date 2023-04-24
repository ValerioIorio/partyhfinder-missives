package com.personal.app.services.impl;

import com.partyh.finder.common.exception.impl.PFIdNullException;
import com.partyh.finder.common.utils.FilterUtil;
import com.partyh.finder.common.validators.commons.ValidationUtils;
import com.personal.app.models.entities.Missive;
import com.personal.app.model.MissiveDTO;
import com.personal.app.model.MissivePageDTO;
import com.personal.app.models.filters.MissiveFilter;
import com.personal.app.repository.MissiveRepository;
import com.personal.app.services.MissiveService;
import com.personal.app.specifications.MissiveSpecification;
import com.personal.app.validators.MissiveValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissiveServiceImpl implements MissiveService {
    @Override
    public MissiveDTO addMissive(MissiveDTO missiveDTO) {
        //check if the dto is null
        missiveValidator.validateId(missiveDTO.getId(), HttpMethod.POST);
        //check if the sender id and the chat id are not null
        if (!ValidationUtils.isNull(missiveDTO.getChatId()) && !ValidationUtils.isNull(missiveDTO.getSenderId())){

            //validate
            missiveValidator.validate(
                    missiveDTO,
                    HttpMethod.POST
            );

            //set default values
            Missive missive = mapper.map(missiveDTO, Missive.class);
            missive.setCreatedAt(LocalDateTime.now());
            missive.setUpdatedAt(LocalDateTime.now());
            missive.setSentAt(LocalDateTime.now());
            missive.setReadAt(null);
            missive.setDeletedAt(null);
            missive.setRead(false);
            //save missive
            return mapper.map(this.missiveRepository.save(missive), MissiveDTO.class);
        } else {
            throw new PFIdNullException("Chat and Sender User Id cannot be null");
        }

    }

    @Override
    public MissiveDTO updateMissive(MissiveDTO missiveDTO) {
        //check if the dto is null
        missiveValidator.validateId(missiveDTO.getId(), HttpMethod.PUT);

        //skip validation on sender and chat ids because they are not changeable

        //validate
        Optional<Missive> missiveOptional = missiveRepository.findById(missiveDTO.getId());
        missiveValidator.validate(
                missiveDTO,
                HttpMethod.PUT,
                new LinkedHashMap<>(
                        Map.of("missive", missiveOptional.isPresent())
                )
        );

        //get the previous missive
        Missive previousMissive = missiveOptional.get();
        //map the new missive dto to an entity
        Missive missive = mapper.map(missiveDTO, Missive.class);
        //set not changeable values
        missive.setSenderId(previousMissive.getSenderId());
        missive.setChatId(previousMissive.getChatId());
        missive.setCreatedAt(previousMissive.getCreatedAt());
        missive.setSentAt(previousMissive.getSentAt());
        missive.setReadAt(previousMissive.getReadAt());
        missive.setDeletedAt(previousMissive.getDeletedAt());
        missive.setRead(previousMissive.isRead());
        //set default values
        missive.setUpdatedAt(LocalDateTime.now());
        //save missive
        return mapper.map(this.missiveRepository.save(missive), MissiveDTO.class);
    }

    @Override
    public MissivePageDTO findAllMissive(MissiveFilter filter) {
        //set default values of Descending and SortBy
        if(!filter.checkValidSortBy(ALLOWED_SORT_BY)){
            filter.setSortBy(DEFAULT_SORT_BY);
        }
        if(ValidationUtils.isNull(filter.getDescending())){
            filter.setDescending(DEFAULT_DESCENDING_SORT_ORDER);
        }
        //get the page of missive
        Page<Missive> missivePage = missiveRepository.findAll(
                missiveSpecification.buildSpecification(filter),
                PageRequest.of(FilterUtil.getNumberOfPage(filter.getNumberOfPage()), FilterUtil.getPageSize(filter.getPageSize()))
                        .withSort(FilterUtil.getDirection(filter.getDescending()), FilterUtil.getSortBy(filter.getSortBy())
                )
        );
        //map the page of missive to page of missiveDTO
        MissivePageDTO pageDTO = new MissivePageDTO();
        pageDTO.setItems(missivePage.stream().map(missive -> mapper.map(missive, MissiveDTO.class)).collect(Collectors.toList()));
        pageDTO.setTotalItems(missivePage.getTotalElements());
        pageDTO.setItemsPerPage(missivePage.getSize());
        pageDTO.setCurrentPage(missivePage.getNumber());
        pageDTO.setTotalPages(missivePage.getTotalPages());
        return pageDTO;
    }

    @Override
    public MissiveDTO getMissive(Long id) {
        Optional<Missive> missiveOptional = missiveRepository.findById(id);
        if(missiveOptional.isPresent()){
            return mapper.map(missiveOptional.get(), MissiveDTO.class);
        }else {
            throw new NoSuchElementException("Missive not found");
        }
    }


    @Override
    public void deleteMissive(Long id) {
        Optional<Missive> missiveOptional = missiveRepository.findById(id);
        if(missiveOptional.isPresent()){
            missiveRepository.delete(missiveOptional.get());
        }else {
            throw new NoSuchElementException("Missive not found");
        }
    }

    @Value("${missive.default.sort.by}")
    private String DEFAULT_SORT_BY;

    @Value("${missive.allowed.sort.by}")
    private String[] ALLOWED_SORT_BY;

    @Value("${missive.default.descending.sort.order}")
    private boolean DEFAULT_DESCENDING_SORT_ORDER;


    private final ModelMapper mapper;
    private final MissiveValidator missiveValidator;
    private final MissiveRepository missiveRepository;
    private final MissiveSpecification missiveSpecification;
}
