package com.castis.adgateway.repository;

import com.castis.adgateway.model.Description;
import com.castis.adgateway.model.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRepository extends JpaRepository<Tracking, String> {


}