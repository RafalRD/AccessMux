package com.sggw.accessmux.repository;

import com.sggw.accessmux.domain.HR;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HR entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HRRepository extends JpaRepository<HR, Long> {

}
