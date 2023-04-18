package com.personal.app.resources;

import com.personal.app.model.MissiveDTO;
import com.personal.app.model.MissivePageDTO;
import com.personal.app.models.filters.MissiveFilter;
import com.personal.app.services.MissiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MissiveController implements MissiveApi{

    private final MissiveService missiveService;

    @Override
    public ResponseEntity<MissiveDTO> addMissive(MissiveDTO missiveDTO) {
        return ResponseEntity.ok(this.missiveService.addMissive(missiveDTO));
    }

    @Override
    public ResponseEntity<Void> deleteMissive(Long id) {
        this.missiveService.deleteMissive(id);
       return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<MissivePageDTO> findAllMissive(Integer pageSize, Integer numberOfPage, Boolean descending, String sortBy, Long senderId, Long reciverId, String text) {
        return ResponseEntity.ok(this.missiveService.findAllMissive(
                new MissiveFilter(pageSize, numberOfPage, descending, sortBy, senderId, reciverId, text)
        ));
    }

    @Override
    public ResponseEntity<MissiveDTO> getMissive(Long id) {
        return ResponseEntity.ok(this.missiveService.getMissive(id));
    }

    @Override
    public ResponseEntity<MissiveDTO> updateMissive(MissiveDTO missiveDTO) {
        return ResponseEntity.ok(this.missiveService.updateMissive(missiveDTO));
    }


}
