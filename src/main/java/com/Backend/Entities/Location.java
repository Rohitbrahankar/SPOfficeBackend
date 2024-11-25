package com.Backend.Entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String locationName;

    @ManyToOne
    @JoinColumn(name = "head_id")
    private Police head;

    @ManyToMany
    @JoinTable(
        name = "police_location",
        joinColumns = @JoinColumn(name = "police_id"),
        inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private Set<Police> polices = new HashSet<>();

    @Column
    private List<String> equipments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id")
    @JsonIgnore
    private Sector sector;

    // @ElementCollection
    // @CollectionTable(name = "policeAtLocation", joinColumns = @JoinColumn(name = "location_id"))
    // @Column(name = "police")
    // private Set<Police> polices = new HashSet<>();

}
