package com.auto_catalog.auto__catalog.store.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator="project_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="project_seq",sequenceName="project_seq", allocationSize=1)
    private Long userId;

    /*@Column(name = "login", unique = true, nullable = false)
    private String login;*/

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true/*, nullable = false*/)
    private String email;

   /* @Column(name = "password", nullable = false)
    private String password;*/

   @Formula("(select count(l.listing_id) from listings l where l.user_id = user_id)")
   @Column(name = "listing_count")
   private int listingCount;

    @OneToMany(mappedBy = "user")
    @Column(name = "listings")
    private List<Listing> listings;

}
