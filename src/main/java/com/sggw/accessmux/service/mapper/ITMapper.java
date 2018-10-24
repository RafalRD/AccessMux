package com.sggw.accessmux.service.mapper;

import com.sggw.accessmux.domain.*;
import com.sggw.accessmux.service.dto.ITDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IT and its DTO ITDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ITMapper extends EntityMapper<ITDTO, IT> {



    default IT fromId(Long id) {
        if (id == null) {
            return null;
        }
        IT iT = new IT();
        iT.setId(id);
        return iT;
    }
}
