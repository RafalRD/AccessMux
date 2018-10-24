package com.sggw.accessmux.repository;

import com.sggw.accessmux.domain.OTHER;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OTHER entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OTHERRepository extends JpaRepository<OTHER, Long> {

}
