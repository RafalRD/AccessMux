package com.sggw.accessmux.service.mapper;

import com.sggw.accessmux.domain.*;
import com.sggw.accessmux.service.dto.ACTIVITYDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ACTIVITY and its DTO ACTIVITYDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ACTIVITYMapper extends EntityMapper<ACTIVITYDTO, ACTIVITY> {



    default ACTIVITY fromId(Long id) {
        if (id == null) {
            return null;
        }
        ACTIVITY aCTIVITY = new ACTIVITY();
        aCTIVITY.setId(id);
        return aCTIVITY;
    }
}
