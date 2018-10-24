package com.sggw.accessmux.service.mapper;

import com.sggw.accessmux.domain.*;
import com.sggw.accessmux.service.dto.OTHERDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OTHER and its DTO OTHERDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OTHERMapper extends EntityMapper<OTHERDTO, OTHER> {



    default OTHER fromId(Long id) {
        if (id == null) {
            return null;
        }
        OTHER oTHER = new OTHER();
        oTHER.setId(id);
        return oTHER;
    }
}
