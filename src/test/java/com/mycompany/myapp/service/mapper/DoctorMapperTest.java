package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DoctorMapperTest {
    private DoctorMapper doctorMapper;

    @BeforeEach
    public void setUp() {
        doctorMapper = new DoctorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(doctorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(doctorMapper.fromId(null)).isNull();
    }
}
