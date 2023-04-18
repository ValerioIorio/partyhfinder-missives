package com.personal.app.models.filters;

import com.partyh.finder.common.models.AbstractFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatFilter extends AbstractFilter {
    private String name;
    public ChatFilter(Integer pageSize, Integer numberOfPage, Boolean descending, String sortBy, String name) {
        super(pageSize, numberOfPage, descending, sortBy);
        this.name = name;
    }
}
