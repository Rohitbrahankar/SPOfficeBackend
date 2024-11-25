package com.Backend.Entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subadmins")
@Getter
@Setter
public class Subadmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String phone;

    @Column
    private String email;

    @Column(nullable = false)
    private String station;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    @JsonIgnore
    private Admin admin;

    @ManyToMany(mappedBy = "subadmins", cascade = CascadeType.ALL)
    // @JsonIgnore
    private Set<Attendance> attendances = new HashSet<>();

    @OneToMany(mappedBy = "subadmin", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Police> polices = new HashSet<>();
    

    @Column
    private String status = "NOT_APPROVED";

    public String subadminString() {
        return "Subadmin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", station='" + station + '\'' +
                ", admin_id='" + admin.getId() + '\'' +
                '}';
    }

}
