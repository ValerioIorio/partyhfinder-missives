package com.personal.app.specifications;

import com.partyh.finder.common.specifications.SpecificationFactory;
import com.personal.app.models.entities.Chat;
import com.personal.app.models.filters.ChatFilter;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChatSpecification extends SpecificationFactory<Chat, ChatFilter> {
    @Override
    protected List<Predicate> buildPredicates(Root<Chat> root, CriteriaQuery<?> query, CriteriaBuilder builder, ChatFilter filter) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getName() != null) {
            predicates.add(builder.like(root.get("name"), "%" + filter.getName() + "%"));
        }
        return predicates;
    }

    @Override
    protected Example<Chat> buildExample(ChatFilter filter) {
        Chat chat = Chat.builder()
                .name(filter.getName())
                .build();
        return Example.of(chat, getExampleMatcherContainingStringAndIgnoreNull());
    }
}
