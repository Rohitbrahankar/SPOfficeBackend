package com.Backend.Entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "areas")
@Getter
@Setter
@ToString
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String areaName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "head_id")
    private Police head;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cohead_id")
    private Police cohead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subPatrolling_id")
    @JsonIgnore
    private SubPatrolling subPatrolling;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Sector> sectors = new HashSet<>();

}
