package com.sggw.accessmux.service.mapper;

import com.sggw.accessmux.domain.*;
import com.sggw.accessmux.service.dto.MARKETINGDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MARKETING and its DTO MARKETINGDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MARKETINGMapper extends EntityMapper<MARKETINGDTO, MARKETING> {



    default MARKETING fromId(Long id) {
        if (id == null) {
            return null;
        }
        MARKETING mARKETING = new MARKETING();
        mARKETING.setId(id);
        return mARKETING;
    }
}
