package com.auto_catalog.auto__catalog.store.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
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

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true/*, nullable = false*/)
    private String email;

   /* @Column(name = "password", nullable = false)
    private String password;*/

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Listing> listings;

    @Column(name = "listing_count")
    private Integer listingCount = 0;

    public void incrementListingCount() {
        if (this.listingCount == null) {
            this.listingCount = 0;
        }
        this.listingCount++;
    }

    public void decrementListingCount() {
        if (this.listingCount != null && this.listingCount > 0) {
            this.listingCount--;
        }
    }
}
