package com.progweb.frota.repository;

import com.progweb.frota.model.TripRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TripRequestRepository extends JpaRepository<TripRequest, Long> {
    List<TripRequest> findByStatus(TripRequest.TripStatus status);
}
