package com.example.school.address;

import com.example.school.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(
    name = "_addressForMembers"
)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "street",
        nullable = false
    )
    private String street;

    @Column(
        name = "city",
        nullable = false
    )
    private String city;

    @Column(
        name = "state",
        nullable = false
    )
    private String state;

    @Column(
        name = "zip",
        nullable= false
    )
    private String zip;

    @Column(
        name = "country",
        nullable = false
    )
    private String country;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // @Enumerated(EnumType.STRING)
    // private AddressFor addressFor;
}
