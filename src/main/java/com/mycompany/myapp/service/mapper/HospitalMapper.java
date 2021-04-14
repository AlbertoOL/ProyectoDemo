package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.HospitalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hospital} and its DTO {@link HospitalDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HospitalMapper extends EntityMapper<HospitalDTO, Hospital> {
    @Mapping(target = "doctors", ignore = true)
    @Mapping(target = "removeDoctor", ignore = true)
    @Mapping(target = "pacientes", ignore = true)
    @Mapping(target = "removePaciente", ignore = true)
    Hospital toEntity(HospitalDTO hospitalDTO);

    default Hospital fromId(Long id) {
        if (id == null) {
            return null;
        }
        Hospital hospital = new Hospital();
        hospital.setId(id);
        return hospital;
    }
}
