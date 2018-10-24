package com.sggw.accessmux.repository;

import com.sggw.accessmux.domain.FINANCES;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FINANCES entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FINANCESRepository extends JpaRepository<FINANCES, Long> {

}
