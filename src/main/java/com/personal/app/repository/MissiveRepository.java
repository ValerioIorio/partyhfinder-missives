package com.personal.app.repository;

import com.personal.app.models.entities.Missive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissiveRepository extends JpaRepository<Missive, Long>, JpaSpecificationExecutor<Missive> {
}
