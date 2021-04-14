package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.ProyectoPracticaApp;
import com.mycompany.myapp.domain.Hospital;
import com.mycompany.myapp.domain.Paciente;
import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import com.mycompany.myapp.repository.PacienteRepository;
import com.mycompany.myapp.service.PacienteQueryService;
import com.mycompany.myapp.service.PacienteService;
import com.mycompany.myapp.service.dto.PacienteCriteria;
import com.mycompany.myapp.service.dto.PacienteDTO;
import com.mycompany.myapp.service.mapper.PacienteMapper;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PacienteResource} REST controller.
 */
@SpringBootTest(classes = ProyectoPracticaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PacienteResourceIT {
    private static final String DEFAULT_NSS = "AAAAAAAAAA";
    private static final String UPDATED_NSS = "BBBBBBBBBB";

    private static final String DEFAULT_ID_HOSPITAL = "AAAAAAAAAA";
    private static final String UPDATED_ID_HOSPITAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_AP_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_AP_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_AP_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_AP_MATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_NAC = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_NAC = "BBBBBBBBBB";

    private static final EstatusRegistro DEFAULT_ESTATUS = EstatusRegistro.ACTIVO;
    private static final EstatusRegistro UPDATED_ESTATUS = EstatusRegistro.DESACTIVADO;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PacienteQueryService pacienteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPacienteMockMvc;

    private Paciente paciente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paciente createEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .nss(DEFAULT_NSS)
            .idHospital(DEFAULT_ID_HOSPITAL)
            .nombre(DEFAULT_NOMBRE)
            .apPaterno(DEFAULT_AP_PATERNO)
            .apMaterno(DEFAULT_AP_MATERNO)
            .fechaNac(DEFAULT_FECHA_NAC)
            .estatus(DEFAULT_ESTATUS);
        return paciente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paciente createUpdatedEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .nss(UPDATED_NSS)
            .idHospital(UPDATED_ID_HOSPITAL)
            .nombre(UPDATED_NOMBRE)
            .apPaterno(UPDATED_AP_PATERNO)
            .apMaterno(UPDATED_AP_MATERNO)
            .fechaNac(UPDATED_FECHA_NAC)
            .estatus(UPDATED_ESTATUS);
        return paciente;
    }

    @BeforeEach
    public void initTest() {
        paciente = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaciente() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();
        // Create the Paciente
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);
        restPacienteMockMvc
            .perform(post("/api/pacientes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate + 1);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNss()).isEqualTo(DEFAULT_NSS);
        assertThat(testPaciente.getIdHospital()).isEqualTo(DEFAULT_ID_HOSPITAL);
        assertThat(testPaciente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPaciente.getApPaterno()).isEqualTo(DEFAULT_AP_PATERNO);
        assertThat(testPaciente.getApMaterno()).isEqualTo(DEFAULT_AP_MATERNO);
        assertThat(testPaciente.getFechaNac()).isEqualTo(DEFAULT_FECHA_NAC);
        assertThat(testPaciente.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    public void createPacienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente with an existing ID
        paciente.setId(1L);
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacienteMockMvc
            .perform(post("/api/pacientes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNssIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setNss(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc
            .perform(post("/api/pacientes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdHospitalIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setIdHospital(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc
            .perform(post("/api/pacientes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setNombre(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc
            .perform(post("/api/pacientes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApPaternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setApPaterno(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc
            .perform(post("/api/pacientes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApMaternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setApMaterno(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc
            .perform(post("/api/pacientes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaNacIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setFechaNac(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc
            .perform(post("/api/pacientes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setEstatus(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc
            .perform(post("/api/pacientes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPacientes() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList
        restPacienteMockMvc
            .perform(get("/api/pacientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nss").value(hasItem(DEFAULT_NSS)))
            .andExpect(jsonPath("$.[*].idHospital").value(hasItem(DEFAULT_ID_HOSPITAL)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apPaterno").value(hasItem(DEFAULT_AP_PATERNO)))
            .andExpect(jsonPath("$.[*].apMaterno").value(hasItem(DEFAULT_AP_MATERNO)))
            .andExpect(jsonPath("$.[*].fechaNac").value(hasItem(DEFAULT_FECHA_NAC)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));
    }

    @Test
    @Transactional
    public void getPaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get the paciente
        restPacienteMockMvc
            .perform(get("/api/pacientes/{id}", paciente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paciente.getId().intValue()))
            .andExpect(jsonPath("$.nss").value(DEFAULT_NSS))
            .andExpect(jsonPath("$.idHospital").value(DEFAULT_ID_HOSPITAL))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apPaterno").value(DEFAULT_AP_PATERNO))
            .andExpect(jsonPath("$.apMaterno").value(DEFAULT_AP_MATERNO))
            .andExpect(jsonPath("$.fechaNac").value(DEFAULT_FECHA_NAC))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()));
    }

    @Test
    @Transactional
    public void getPacientesByIdFiltering() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        Long id = paciente.getId();

        defaultPacienteShouldBeFound("id.equals=" + id);
        defaultPacienteShouldNotBeFound("id.notEquals=" + id);

        defaultPacienteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPacienteShouldNotBeFound("id.greaterThan=" + id);

        defaultPacienteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPacienteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllPacientesByNssIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nss equals to DEFAULT_NSS
        defaultPacienteShouldBeFound("nss.equals=" + DEFAULT_NSS);

        // Get all the pacienteList where nss equals to UPDATED_NSS
        defaultPacienteShouldNotBeFound("nss.equals=" + UPDATED_NSS);
    }

    @Test
    @Transactional
    public void getAllPacientesByNssIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nss not equals to DEFAULT_NSS
        defaultPacienteShouldNotBeFound("nss.notEquals=" + DEFAULT_NSS);

        // Get all the pacienteList where nss not equals to UPDATED_NSS
        defaultPacienteShouldBeFound("nss.notEquals=" + UPDATED_NSS);
    }

    @Test
    @Transactional
    public void getAllPacientesByNssIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nss in DEFAULT_NSS or UPDATED_NSS
        defaultPacienteShouldBeFound("nss.in=" + DEFAULT_NSS + "," + UPDATED_NSS);

        // Get all the pacienteList where nss equals to UPDATED_NSS
        defaultPacienteShouldNotBeFound("nss.in=" + UPDATED_NSS);
    }

    @Test
    @Transactional
    public void getAllPacientesByNssIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nss is not null
        defaultPacienteShouldBeFound("nss.specified=true");

        // Get all the pacienteList where nss is null
        defaultPacienteShouldNotBeFound("nss.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByNssContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nss contains DEFAULT_NSS
        defaultPacienteShouldBeFound("nss.contains=" + DEFAULT_NSS);

        // Get all the pacienteList where nss contains UPDATED_NSS
        defaultPacienteShouldNotBeFound("nss.contains=" + UPDATED_NSS);
    }

    @Test
    @Transactional
    public void getAllPacientesByNssNotContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nss does not contain DEFAULT_NSS
        defaultPacienteShouldNotBeFound("nss.doesNotContain=" + DEFAULT_NSS);

        // Get all the pacienteList where nss does not contain UPDATED_NSS
        defaultPacienteShouldBeFound("nss.doesNotContain=" + UPDATED_NSS);
    }

    @Test
    @Transactional
    public void getAllPacientesByIdHospitalIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where idHospital equals to DEFAULT_ID_HOSPITAL
        defaultPacienteShouldBeFound("idHospital.equals=" + DEFAULT_ID_HOSPITAL);

        // Get all the pacienteList where idHospital equals to UPDATED_ID_HOSPITAL
        defaultPacienteShouldNotBeFound("idHospital.equals=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllPacientesByIdHospitalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where idHospital not equals to DEFAULT_ID_HOSPITAL
        defaultPacienteShouldNotBeFound("idHospital.notEquals=" + DEFAULT_ID_HOSPITAL);

        // Get all the pacienteList where idHospital not equals to UPDATED_ID_HOSPITAL
        defaultPacienteShouldBeFound("idHospital.notEquals=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllPacientesByIdHospitalIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where idHospital in DEFAULT_ID_HOSPITAL or UPDATED_ID_HOSPITAL
        defaultPacienteShouldBeFound("idHospital.in=" + DEFAULT_ID_HOSPITAL + "," + UPDATED_ID_HOSPITAL);

        // Get all the pacienteList where idHospital equals to UPDATED_ID_HOSPITAL
        defaultPacienteShouldNotBeFound("idHospital.in=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllPacientesByIdHospitalIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where idHospital is not null
        defaultPacienteShouldBeFound("idHospital.specified=true");

        // Get all the pacienteList where idHospital is null
        defaultPacienteShouldNotBeFound("idHospital.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByIdHospitalContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where idHospital contains DEFAULT_ID_HOSPITAL
        defaultPacienteShouldBeFound("idHospital.contains=" + DEFAULT_ID_HOSPITAL);

        // Get all the pacienteList where idHospital contains UPDATED_ID_HOSPITAL
        defaultPacienteShouldNotBeFound("idHospital.contains=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllPacientesByIdHospitalNotContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where idHospital does not contain DEFAULT_ID_HOSPITAL
        defaultPacienteShouldNotBeFound("idHospital.doesNotContain=" + DEFAULT_ID_HOSPITAL);

        // Get all the pacienteList where idHospital does not contain UPDATED_ID_HOSPITAL
        defaultPacienteShouldBeFound("idHospital.doesNotContain=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllPacientesByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nombre equals to DEFAULT_NOMBRE
        defaultPacienteShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the pacienteList where nombre equals to UPDATED_NOMBRE
        defaultPacienteShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPacientesByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nombre not equals to DEFAULT_NOMBRE
        defaultPacienteShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the pacienteList where nombre not equals to UPDATED_NOMBRE
        defaultPacienteShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPacientesByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultPacienteShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the pacienteList where nombre equals to UPDATED_NOMBRE
        defaultPacienteShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPacientesByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nombre is not null
        defaultPacienteShouldBeFound("nombre.specified=true");

        // Get all the pacienteList where nombre is null
        defaultPacienteShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByNombreContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nombre contains DEFAULT_NOMBRE
        defaultPacienteShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the pacienteList where nombre contains UPDATED_NOMBRE
        defaultPacienteShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPacientesByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nombre does not contain DEFAULT_NOMBRE
        defaultPacienteShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the pacienteList where nombre does not contain UPDATED_NOMBRE
        defaultPacienteShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPacientesByApPaternoIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apPaterno equals to DEFAULT_AP_PATERNO
        defaultPacienteShouldBeFound("apPaterno.equals=" + DEFAULT_AP_PATERNO);

        // Get all the pacienteList where apPaterno equals to UPDATED_AP_PATERNO
        defaultPacienteShouldNotBeFound("apPaterno.equals=" + UPDATED_AP_PATERNO);
    }

    @Test
    @Transactional
    public void getAllPacientesByApPaternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apPaterno not equals to DEFAULT_AP_PATERNO
        defaultPacienteShouldNotBeFound("apPaterno.notEquals=" + DEFAULT_AP_PATERNO);

        // Get all the pacienteList where apPaterno not equals to UPDATED_AP_PATERNO
        defaultPacienteShouldBeFound("apPaterno.notEquals=" + UPDATED_AP_PATERNO);
    }

    @Test
    @Transactional
    public void getAllPacientesByApPaternoIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apPaterno in DEFAULT_AP_PATERNO or UPDATED_AP_PATERNO
        defaultPacienteShouldBeFound("apPaterno.in=" + DEFAULT_AP_PATERNO + "," + UPDATED_AP_PATERNO);

        // Get all the pacienteList where apPaterno equals to UPDATED_AP_PATERNO
        defaultPacienteShouldNotBeFound("apPaterno.in=" + UPDATED_AP_PATERNO);
    }

    @Test
    @Transactional
    public void getAllPacientesByApPaternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apPaterno is not null
        defaultPacienteShouldBeFound("apPaterno.specified=true");

        // Get all the pacienteList where apPaterno is null
        defaultPacienteShouldNotBeFound("apPaterno.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByApPaternoContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apPaterno contains DEFAULT_AP_PATERNO
        defaultPacienteShouldBeFound("apPaterno.contains=" + DEFAULT_AP_PATERNO);

        // Get all the pacienteList where apPaterno contains UPDATED_AP_PATERNO
        defaultPacienteShouldNotBeFound("apPaterno.contains=" + UPDATED_AP_PATERNO);
    }

    @Test
    @Transactional
    public void getAllPacientesByApPaternoNotContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apPaterno does not contain DEFAULT_AP_PATERNO
        defaultPacienteShouldNotBeFound("apPaterno.doesNotContain=" + DEFAULT_AP_PATERNO);

        // Get all the pacienteList where apPaterno does not contain UPDATED_AP_PATERNO
        defaultPacienteShouldBeFound("apPaterno.doesNotContain=" + UPDATED_AP_PATERNO);
    }

    @Test
    @Transactional
    public void getAllPacientesByApMaternoIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apMaterno equals to DEFAULT_AP_MATERNO
        defaultPacienteShouldBeFound("apMaterno.equals=" + DEFAULT_AP_MATERNO);

        // Get all the pacienteList where apMaterno equals to UPDATED_AP_MATERNO
        defaultPacienteShouldNotBeFound("apMaterno.equals=" + UPDATED_AP_MATERNO);
    }

    @Test
    @Transactional
    public void getAllPacientesByApMaternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apMaterno not equals to DEFAULT_AP_MATERNO
        defaultPacienteShouldNotBeFound("apMaterno.notEquals=" + DEFAULT_AP_MATERNO);

        // Get all the pacienteList where apMaterno not equals to UPDATED_AP_MATERNO
        defaultPacienteShouldBeFound("apMaterno.notEquals=" + UPDATED_AP_MATERNO);
    }

    @Test
    @Transactional
    public void getAllPacientesByApMaternoIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apMaterno in DEFAULT_AP_MATERNO or UPDATED_AP_MATERNO
        defaultPacienteShouldBeFound("apMaterno.in=" + DEFAULT_AP_MATERNO + "," + UPDATED_AP_MATERNO);

        // Get all the pacienteList where apMaterno equals to UPDATED_AP_MATERNO
        defaultPacienteShouldNotBeFound("apMaterno.in=" + UPDATED_AP_MATERNO);
    }

    @Test
    @Transactional
    public void getAllPacientesByApMaternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apMaterno is not null
        defaultPacienteShouldBeFound("apMaterno.specified=true");

        // Get all the pacienteList where apMaterno is null
        defaultPacienteShouldNotBeFound("apMaterno.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByApMaternoContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apMaterno contains DEFAULT_AP_MATERNO
        defaultPacienteShouldBeFound("apMaterno.contains=" + DEFAULT_AP_MATERNO);

        // Get all the pacienteList where apMaterno contains UPDATED_AP_MATERNO
        defaultPacienteShouldNotBeFound("apMaterno.contains=" + UPDATED_AP_MATERNO);
    }

    @Test
    @Transactional
    public void getAllPacientesByApMaternoNotContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apMaterno does not contain DEFAULT_AP_MATERNO
        defaultPacienteShouldNotBeFound("apMaterno.doesNotContain=" + DEFAULT_AP_MATERNO);

        // Get all the pacienteList where apMaterno does not contain UPDATED_AP_MATERNO
        defaultPacienteShouldBeFound("apMaterno.doesNotContain=" + UPDATED_AP_MATERNO);
    }

    @Test
    @Transactional
    public void getAllPacientesByFechaNacIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNac equals to DEFAULT_FECHA_NAC
        defaultPacienteShouldBeFound("fechaNac.equals=" + DEFAULT_FECHA_NAC);

        // Get all the pacienteList where fechaNac equals to UPDATED_FECHA_NAC
        defaultPacienteShouldNotBeFound("fechaNac.equals=" + UPDATED_FECHA_NAC);
    }

    @Test
    @Transactional
    public void getAllPacientesByFechaNacIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNac not equals to DEFAULT_FECHA_NAC
        defaultPacienteShouldNotBeFound("fechaNac.notEquals=" + DEFAULT_FECHA_NAC);

        // Get all the pacienteList where fechaNac not equals to UPDATED_FECHA_NAC
        defaultPacienteShouldBeFound("fechaNac.notEquals=" + UPDATED_FECHA_NAC);
    }

    @Test
    @Transactional
    public void getAllPacientesByFechaNacIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNac in DEFAULT_FECHA_NAC or UPDATED_FECHA_NAC
        defaultPacienteShouldBeFound("fechaNac.in=" + DEFAULT_FECHA_NAC + "," + UPDATED_FECHA_NAC);

        // Get all the pacienteList where fechaNac equals to UPDATED_FECHA_NAC
        defaultPacienteShouldNotBeFound("fechaNac.in=" + UPDATED_FECHA_NAC);
    }

    @Test
    @Transactional
    public void getAllPacientesByFechaNacIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNac is not null
        defaultPacienteShouldBeFound("fechaNac.specified=true");

        // Get all the pacienteList where fechaNac is null
        defaultPacienteShouldNotBeFound("fechaNac.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByFechaNacContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNac contains DEFAULT_FECHA_NAC
        defaultPacienteShouldBeFound("fechaNac.contains=" + DEFAULT_FECHA_NAC);

        // Get all the pacienteList where fechaNac contains UPDATED_FECHA_NAC
        defaultPacienteShouldNotBeFound("fechaNac.contains=" + UPDATED_FECHA_NAC);
    }

    @Test
    @Transactional
    public void getAllPacientesByFechaNacNotContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNac does not contain DEFAULT_FECHA_NAC
        defaultPacienteShouldNotBeFound("fechaNac.doesNotContain=" + DEFAULT_FECHA_NAC);

        // Get all the pacienteList where fechaNac does not contain UPDATED_FECHA_NAC
        defaultPacienteShouldBeFound("fechaNac.doesNotContain=" + UPDATED_FECHA_NAC);
    }

    @Test
    @Transactional
    public void getAllPacientesByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where estatus equals to DEFAULT_ESTATUS
        defaultPacienteShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the pacienteList where estatus equals to UPDATED_ESTATUS
        defaultPacienteShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllPacientesByEstatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where estatus not equals to DEFAULT_ESTATUS
        defaultPacienteShouldNotBeFound("estatus.notEquals=" + DEFAULT_ESTATUS);

        // Get all the pacienteList where estatus not equals to UPDATED_ESTATUS
        defaultPacienteShouldBeFound("estatus.notEquals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllPacientesByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultPacienteShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the pacienteList where estatus equals to UPDATED_ESTATUS
        defaultPacienteShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllPacientesByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where estatus is not null
        defaultPacienteShouldBeFound("estatus.specified=true");

        // Get all the pacienteList where estatus is null
        defaultPacienteShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByHospitalIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);
        Hospital hospital = HospitalResourceIT.createEntity(em);
        em.persist(hospital);
        em.flush();
        paciente.setHospital(hospital);
        pacienteRepository.saveAndFlush(paciente);
        Long hospitalId = hospital.getId();

        // Get all the pacienteList where hospital equals to hospitalId
        defaultPacienteShouldBeFound("hospitalId.equals=" + hospitalId);

        // Get all the pacienteList where hospital equals to hospitalId + 1
        defaultPacienteShouldNotBeFound("hospitalId.equals=" + (hospitalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPacienteShouldBeFound(String filter) throws Exception {
        restPacienteMockMvc
            .perform(get("/api/pacientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nss").value(hasItem(DEFAULT_NSS)))
            .andExpect(jsonPath("$.[*].idHospital").value(hasItem(DEFAULT_ID_HOSPITAL)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apPaterno").value(hasItem(DEFAULT_AP_PATERNO)))
            .andExpect(jsonPath("$.[*].apMaterno").value(hasItem(DEFAULT_AP_MATERNO)))
            .andExpect(jsonPath("$.[*].fechaNac").value(hasItem(DEFAULT_FECHA_NAC)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));

        // Check, that the count call also returns 1
        restPacienteMockMvc
            .perform(get("/api/pacientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPacienteShouldNotBeFound(String filter) throws Exception {
        restPacienteMockMvc
            .perform(get("/api/pacientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPacienteMockMvc
            .perform(get("/api/pacientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPaciente() throws Exception {
        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente
        Paciente updatedPaciente = pacienteRepository.findById(paciente.getId()).get();
        // Disconnect from session so that the updates on updatedPaciente are not directly saved in db
        em.detach(updatedPaciente);
        updatedPaciente
            .nss(UPDATED_NSS)
            .idHospital(UPDATED_ID_HOSPITAL)
            .nombre(UPDATED_NOMBRE)
            .apPaterno(UPDATED_AP_PATERNO)
            .apMaterno(UPDATED_AP_MATERNO)
            .fechaNac(UPDATED_FECHA_NAC)
            .estatus(UPDATED_ESTATUS);
        PacienteDTO pacienteDTO = pacienteMapper.toDto(updatedPaciente);

        restPacienteMockMvc
            .perform(put("/api/pacientes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNss()).isEqualTo(UPDATED_NSS);
        assertThat(testPaciente.getIdHospital()).isEqualTo(UPDATED_ID_HOSPITAL);
        assertThat(testPaciente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPaciente.getApPaterno()).isEqualTo(UPDATED_AP_PATERNO);
        assertThat(testPaciente.getApMaterno()).isEqualTo(UPDATED_AP_MATERNO);
        assertThat(testPaciente.getFechaNac()).isEqualTo(UPDATED_FECHA_NAC);
        assertThat(testPaciente.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Create the Paciente
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacienteMockMvc
            .perform(put("/api/pacientes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeDelete = pacienteRepository.findAll().size();

        // Delete the paciente
        restPacienteMockMvc
            .perform(delete("/api/pacientes/{id}", paciente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
