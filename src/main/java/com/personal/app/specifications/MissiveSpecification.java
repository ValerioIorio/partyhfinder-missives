package com.personal.app.specifications;

import com.partyh.finder.common.specifications.SpecificationFactory;
import com.personal.app.models.entities.Missive;
import com.personal.app.models.filters.MissiveFilter;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
@Component
public class MissiveSpecification extends SpecificationFactory<Missive, MissiveFilter> {
    @Override
    protected List<Predicate> buildPredicates(Root<Missive> root, CriteriaQuery<?> query, CriteriaBuilder builder, MissiveFilter filter) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getSenderId() != null) {
            predicates.add(builder.equal(root.get("senderId"), filter.getSenderId()));
        }
        if (filter.getText() != null) {
            predicates.add(builder.like(root.get("text"), "%" + filter.getText() + "%"));
        }
        return predicates;
    }

    @Override
    protected Example<Missive> buildExample(MissiveFilter filter) {
        Missive missive = Missive.builder()
                .senderId(filter.getSenderId())
                .text(filter.getText())
                .build();
        return Example.of(missive, getExampleMatcherContainingStringAndIgnoreNull());
    }
}
