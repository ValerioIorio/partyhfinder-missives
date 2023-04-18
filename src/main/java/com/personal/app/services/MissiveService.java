package com.personal.app.services;

import com.personal.app.model.MissiveDTO;
import com.personal.app.model.MissivePageDTO;
import com.personal.app.models.filters.MissiveFilter;

import javax.ws.rs.client.ClientBuilder;

public interface MissiveService {
    MissiveDTO addMissive(MissiveDTO missiveDTO);

    MissivePageDTO findAllMissive(MissiveFilter filter);

    MissiveDTO getMissive(Long id);

    MissiveDTO updateMissive(MissiveDTO missiveDTO);

    void deleteMissive(Long id);
}
