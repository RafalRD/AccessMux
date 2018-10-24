package com.sggw.accessmux.service.mapper;

import com.sggw.accessmux.domain.*;
import com.sggw.accessmux.service.dto.FINANCESDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FINANCES and its DTO FINANCESDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FINANCESMapper extends EntityMapper<FINANCESDTO, FINANCES> {



    default FINANCES fromId(Long id) {
        if (id == null) {
            return null;
        }
        FINANCES fINANCES = new FINANCES();
        fINANCES.setId(id);
        return fINANCES;
    }
}
