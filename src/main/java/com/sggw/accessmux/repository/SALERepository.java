package com.sggw.accessmux.repository;

import com.sggw.accessmux.domain.SALE;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SALE entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SALERepository extends JpaRepository<SALE, Long> {

}
