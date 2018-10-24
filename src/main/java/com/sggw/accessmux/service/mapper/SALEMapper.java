package com.sggw.accessmux.service.mapper;

import com.sggw.accessmux.domain.*;
import com.sggw.accessmux.service.dto.SALEDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SALE and its DTO SALEDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SALEMapper extends EntityMapper<SALEDTO, SALE> {



    default SALE fromId(Long id) {
        if (id == null) {
            return null;
        }
        SALE sALE = new SALE();
        sALE.setId(id);
        return sALE;
    }
}
