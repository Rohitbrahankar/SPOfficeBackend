package com.Backend.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "polices")
public class Police {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private Long policeId;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String designation;

    // private Long subadminId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subadmin_id")
    @JsonIgnore
    private Subadmin subadmin;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Area> areas = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Sector> sectors = new ArrayList<>();
    
    @ManyToMany(mappedBy = "polices")
    @JsonIgnore
    private List<Location> locations = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Patrolling> patrollings = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<SubPatrolling> subPatrollings = new ArrayList<>();

    

}
