package com.personal.app.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "pf_chats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chat {


    @Id
    @Column(name = "pf_chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pf_name")
    private String name;

    @Column(name = "pf_users_ids")
    private String userStringIds;

    @Column(name = "pf_created_at")
    private LocalDateTime createdAt;

    @Column(name = "pf_updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "chat_id")
    private List<Missive> missives = new ArrayList<>();

}
