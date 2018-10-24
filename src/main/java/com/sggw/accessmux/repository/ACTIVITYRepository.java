package com.sggw.accessmux.repository;

import com.sggw.accessmux.domain.ACTIVITY;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ACTIVITY entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ACTIVITYRepository extends JpaRepository<ACTIVITY, Long> {

}
