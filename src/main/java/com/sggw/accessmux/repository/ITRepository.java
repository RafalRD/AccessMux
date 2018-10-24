package com.sggw.accessmux.repository;

import com.sggw.accessmux.domain.IT;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ITRepository extends JpaRepository<IT, Long> {

}
