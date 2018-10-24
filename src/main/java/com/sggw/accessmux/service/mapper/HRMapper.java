package com.sggw.accessmux.service.mapper;

import com.sggw.accessmux.domain.*;
import com.sggw.accessmux.service.dto.HRDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HR and its DTO HRDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HRMapper extends EntityMapper<HRDTO, HR> {



    default HR fromId(Long id) {
        if (id == null) {
            return null;
        }
        HR hR = new HR();
        hR.setId(id);
        return hR;
    }
}
