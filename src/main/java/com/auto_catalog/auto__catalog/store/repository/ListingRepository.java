package com.auto_catalog.auto__catalog.store.repository;

import com.auto_catalog.auto__catalog.store.entity.Listing;
import com.auto_catalog.auto__catalog.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByUser(User user);
}
