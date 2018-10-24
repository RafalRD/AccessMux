package com.sggw.accessmux.repository;

import com.sggw.accessmux.domain.MARKETING;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MARKETING entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MARKETINGRepository extends JpaRepository<MARKETING, Long> {

}
