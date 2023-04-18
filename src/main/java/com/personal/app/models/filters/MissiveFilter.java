package com.personal.app.models.filters;

import com.partyh.finder.common.models.AbstractFilter;
import lombok.*;

@Getter
@Setter
public class MissiveFilter extends AbstractFilter {
    private Long senderId;
    private String text;

    public MissiveFilter(Integer pageSize, Integer numberOfPage, Boolean descending, String sortBy, Long senderId, Long receiverId, String text) {
        super(pageSize, numberOfPage, descending, sortBy);
        this.senderId = senderId;
        this.text = text;
    }
}
