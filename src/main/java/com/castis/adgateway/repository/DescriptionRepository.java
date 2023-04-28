package com.castis.adgateway.repository;

import com.castis.adgateway.model.Description;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionRepository extends JpaRepository<Description, Long> {

    Description findTopByVodRequestId(String vodRequestId);

}