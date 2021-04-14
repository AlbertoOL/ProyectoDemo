package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PacienteMapperTest {
    private PacienteMapper pacienteMapper;

    @BeforeEach
    public void setUp() {
        pacienteMapper = new PacienteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(pacienteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pacienteMapper.fromId(null)).isNull();
    }
}
