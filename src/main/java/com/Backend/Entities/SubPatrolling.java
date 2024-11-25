
package com.Backend.Entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subPatrollings")
@Getter
@Setter
public class SubPatrolling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "head_id")
    // @JsonIgnore
    private Police head;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cohead_id")
    // @JsonIgnore
    private Police cohead;

    @Column
    private String subpatrollingname;

    @Column
    private String description;

    @Column
    private String instructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patrolling_id")
    @JsonIgnore
    private Patrolling patrolling;

    @OneToMany(mappedBy = "subPatrolling", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Area> areas;

}
