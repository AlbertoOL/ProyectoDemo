package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.ProyectoPracticaApp;
import com.mycompany.myapp.domain.Doctor;
import com.mycompany.myapp.domain.Hospital;
import com.mycompany.myapp.domain.Paciente;
import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import com.mycompany.myapp.repository.HospitalRepository;
import com.mycompany.myapp.service.HospitalQueryService;
import com.mycompany.myapp.service.HospitalService;
import com.mycompany.myapp.service.dto.HospitalCriteria;
import com.mycompany.myapp.service.dto.HospitalDTO;
import com.mycompany.myapp.service.mapper.HospitalMapper;
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
 * Integration tests for the {@link HospitalResource} REST controller.
 */
@SpringBootTest(classes = ProyectoPracticaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HospitalResourceIT {
    private static final String DEFAULT_ID_HOSPITAL = "AAAAAAAAAA";
    private static final String UPDATED_ID_HOSPITAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_PISOS = "AAAAAAAAAA";
    private static final String UPDATED_NUM_PISOS = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_CAMAS = "AAAAAAAAAA";
    private static final String UPDATED_NUM_CAMAS = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_CUARTOS = "AAAAAAAAAA";
    private static final String UPDATED_NUM_CUARTOS = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_CREACION = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_CREACION = "BBBBBBBBBB";

    private static final EstatusRegistro DEFAULT_ESTATUS = EstatusRegistro.ACTIVO;
    private static final EstatusRegistro UPDATED_ESTATUS = EstatusRegistro.DESACTIVADO;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalMapper hospitalMapper;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalQueryService hospitalQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHospitalMockMvc;

    private Hospital hospital;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hospital createEntity(EntityManager em) {
        Hospital hospital = new Hospital()
            .idHospital(DEFAULT_ID_HOSPITAL)
            .nombre(DEFAULT_NOMBRE)
            .numPisos(DEFAULT_NUM_PISOS)
            .numCamas(DEFAULT_NUM_CAMAS)
            .numCuartos(DEFAULT_NUM_CUARTOS)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .estatus(DEFAULT_ESTATUS);
        return hospital;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hospital createUpdatedEntity(EntityManager em) {
        Hospital hospital = new Hospital()
            .idHospital(UPDATED_ID_HOSPITAL)
            .nombre(UPDATED_NOMBRE)
            .numPisos(UPDATED_NUM_PISOS)
            .numCamas(UPDATED_NUM_CAMAS)
            .numCuartos(UPDATED_NUM_CUARTOS)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .estatus(UPDATED_ESTATUS);
        return hospital;
    }

    @BeforeEach
    public void initTest() {
        hospital = createEntity(em);
    }

    @Test
    @Transactional
    public void createHospital() throws Exception {
        int databaseSizeBeforeCreate = hospitalRepository.findAll().size();
        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);
        restHospitalMockMvc
            .perform(post("/api/hospitals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isCreated());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeCreate + 1);
        Hospital testHospital = hospitalList.get(hospitalList.size() - 1);
        assertThat(testHospital.getIdHospital()).isEqualTo(DEFAULT_ID_HOSPITAL);
        assertThat(testHospital.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testHospital.getNumPisos()).isEqualTo(DEFAULT_NUM_PISOS);
        assertThat(testHospital.getNumCamas()).isEqualTo(DEFAULT_NUM_CAMAS);
        assertThat(testHospital.getNumCuartos()).isEqualTo(DEFAULT_NUM_CUARTOS);
        assertThat(testHospital.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testHospital.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    public void createHospitalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hospitalRepository.findAll().size();

        // Create the Hospital with an existing ID
        hospital.setId(1L);
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHospitalMockMvc
            .perform(post("/api/hospitals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdHospitalIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setIdHospital(null);

        // Create the Hospital, which fails.
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        restHospitalMockMvc
            .perform(post("/api/hospitals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setNombre(null);

        // Create the Hospital, which fails.
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        restHospitalMockMvc
            .perform(post("/api/hospitals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumPisosIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setNumPisos(null);

        // Create the Hospital, which fails.
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        restHospitalMockMvc
            .perform(post("/api/hospitals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumCamasIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setNumCamas(null);

        // Create the Hospital, which fails.
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        restHospitalMockMvc
            .perform(post("/api/hospitals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumCuartosIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setNumCuartos(null);

        // Create the Hospital, which fails.
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        restHospitalMockMvc
            .perform(post("/api/hospitals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setEstatus(null);

        // Create the Hospital, which fails.
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        restHospitalMockMvc
            .perform(post("/api/hospitals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHospitals() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList
        restHospitalMockMvc
            .perform(get("/api/hospitals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospital.getId().intValue())))
            .andExpect(jsonPath("$.[*].idHospital").value(hasItem(DEFAULT_ID_HOSPITAL)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].numPisos").value(hasItem(DEFAULT_NUM_PISOS)))
            .andExpect(jsonPath("$.[*].numCamas").value(hasItem(DEFAULT_NUM_CAMAS)))
            .andExpect(jsonPath("$.[*].numCuartos").value(hasItem(DEFAULT_NUM_CUARTOS)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));
    }

    @Test
    @Transactional
    public void getHospital() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get the hospital
        restHospitalMockMvc
            .perform(get("/api/hospitals/{id}", hospital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hospital.getId().intValue()))
            .andExpect(jsonPath("$.idHospital").value(DEFAULT_ID_HOSPITAL))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.numPisos").value(DEFAULT_NUM_PISOS))
            .andExpect(jsonPath("$.numCamas").value(DEFAULT_NUM_CAMAS))
            .andExpect(jsonPath("$.numCuartos").value(DEFAULT_NUM_CUARTOS))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()));
    }

    @Test
    @Transactional
    public void getHospitalsByIdFiltering() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        Long id = hospital.getId();

        defaultHospitalShouldBeFound("id.equals=" + id);
        defaultHospitalShouldNotBeFound("id.notEquals=" + id);

        defaultHospitalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHospitalShouldNotBeFound("id.greaterThan=" + id);

        defaultHospitalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHospitalShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllHospitalsByIdHospitalIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where idHospital equals to DEFAULT_ID_HOSPITAL
        defaultHospitalShouldBeFound("idHospital.equals=" + DEFAULT_ID_HOSPITAL);

        // Get all the hospitalList where idHospital equals to UPDATED_ID_HOSPITAL
        defaultHospitalShouldNotBeFound("idHospital.equals=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllHospitalsByIdHospitalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where idHospital not equals to DEFAULT_ID_HOSPITAL
        defaultHospitalShouldNotBeFound("idHospital.notEquals=" + DEFAULT_ID_HOSPITAL);

        // Get all the hospitalList where idHospital not equals to UPDATED_ID_HOSPITAL
        defaultHospitalShouldBeFound("idHospital.notEquals=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllHospitalsByIdHospitalIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where idHospital in DEFAULT_ID_HOSPITAL or UPDATED_ID_HOSPITAL
        defaultHospitalShouldBeFound("idHospital.in=" + DEFAULT_ID_HOSPITAL + "," + UPDATED_ID_HOSPITAL);

        // Get all the hospitalList where idHospital equals to UPDATED_ID_HOSPITAL
        defaultHospitalShouldNotBeFound("idHospital.in=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllHospitalsByIdHospitalIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where idHospital is not null
        defaultHospitalShouldBeFound("idHospital.specified=true");

        // Get all the hospitalList where idHospital is null
        defaultHospitalShouldNotBeFound("idHospital.specified=false");
    }

    @Test
    @Transactional
    public void getAllHospitalsByIdHospitalContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where idHospital contains DEFAULT_ID_HOSPITAL
        defaultHospitalShouldBeFound("idHospital.contains=" + DEFAULT_ID_HOSPITAL);

        // Get all the hospitalList where idHospital contains UPDATED_ID_HOSPITAL
        defaultHospitalShouldNotBeFound("idHospital.contains=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllHospitalsByIdHospitalNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where idHospital does not contain DEFAULT_ID_HOSPITAL
        defaultHospitalShouldNotBeFound("idHospital.doesNotContain=" + DEFAULT_ID_HOSPITAL);

        // Get all the hospitalList where idHospital does not contain UPDATED_ID_HOSPITAL
        defaultHospitalShouldBeFound("idHospital.doesNotContain=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where nombre equals to DEFAULT_NOMBRE
        defaultHospitalShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the hospitalList where nombre equals to UPDATED_NOMBRE
        defaultHospitalShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where nombre not equals to DEFAULT_NOMBRE
        defaultHospitalShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the hospitalList where nombre not equals to UPDATED_NOMBRE
        defaultHospitalShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultHospitalShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the hospitalList where nombre equals to UPDATED_NOMBRE
        defaultHospitalShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where nombre is not null
        defaultHospitalShouldBeFound("nombre.specified=true");

        // Get all the hospitalList where nombre is null
        defaultHospitalShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllHospitalsByNombreContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where nombre contains DEFAULT_NOMBRE
        defaultHospitalShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the hospitalList where nombre contains UPDATED_NOMBRE
        defaultHospitalShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where nombre does not contain DEFAULT_NOMBRE
        defaultHospitalShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the hospitalList where nombre does not contain UPDATED_NOMBRE
        defaultHospitalShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumPisosIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numPisos equals to DEFAULT_NUM_PISOS
        defaultHospitalShouldBeFound("numPisos.equals=" + DEFAULT_NUM_PISOS);

        // Get all the hospitalList where numPisos equals to UPDATED_NUM_PISOS
        defaultHospitalShouldNotBeFound("numPisos.equals=" + UPDATED_NUM_PISOS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumPisosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numPisos not equals to DEFAULT_NUM_PISOS
        defaultHospitalShouldNotBeFound("numPisos.notEquals=" + DEFAULT_NUM_PISOS);

        // Get all the hospitalList where numPisos not equals to UPDATED_NUM_PISOS
        defaultHospitalShouldBeFound("numPisos.notEquals=" + UPDATED_NUM_PISOS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumPisosIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numPisos in DEFAULT_NUM_PISOS or UPDATED_NUM_PISOS
        defaultHospitalShouldBeFound("numPisos.in=" + DEFAULT_NUM_PISOS + "," + UPDATED_NUM_PISOS);

        // Get all the hospitalList where numPisos equals to UPDATED_NUM_PISOS
        defaultHospitalShouldNotBeFound("numPisos.in=" + UPDATED_NUM_PISOS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumPisosIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numPisos is not null
        defaultHospitalShouldBeFound("numPisos.specified=true");

        // Get all the hospitalList where numPisos is null
        defaultHospitalShouldNotBeFound("numPisos.specified=false");
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumPisosContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numPisos contains DEFAULT_NUM_PISOS
        defaultHospitalShouldBeFound("numPisos.contains=" + DEFAULT_NUM_PISOS);

        // Get all the hospitalList where numPisos contains UPDATED_NUM_PISOS
        defaultHospitalShouldNotBeFound("numPisos.contains=" + UPDATED_NUM_PISOS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumPisosNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numPisos does not contain DEFAULT_NUM_PISOS
        defaultHospitalShouldNotBeFound("numPisos.doesNotContain=" + DEFAULT_NUM_PISOS);

        // Get all the hospitalList where numPisos does not contain UPDATED_NUM_PISOS
        defaultHospitalShouldBeFound("numPisos.doesNotContain=" + UPDATED_NUM_PISOS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumCamasIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numCamas equals to DEFAULT_NUM_CAMAS
        defaultHospitalShouldBeFound("numCamas.equals=" + DEFAULT_NUM_CAMAS);

        // Get all the hospitalList where numCamas equals to UPDATED_NUM_CAMAS
        defaultHospitalShouldNotBeFound("numCamas.equals=" + UPDATED_NUM_CAMAS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumCamasIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numCamas not equals to DEFAULT_NUM_CAMAS
        defaultHospitalShouldNotBeFound("numCamas.notEquals=" + DEFAULT_NUM_CAMAS);

        // Get all the hospitalList where numCamas not equals to UPDATED_NUM_CAMAS
        defaultHospitalShouldBeFound("numCamas.notEquals=" + UPDATED_NUM_CAMAS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumCamasIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numCamas in DEFAULT_NUM_CAMAS or UPDATED_NUM_CAMAS
        defaultHospitalShouldBeFound("numCamas.in=" + DEFAULT_NUM_CAMAS + "," + UPDATED_NUM_CAMAS);

        // Get all the hospitalList where numCamas equals to UPDATED_NUM_CAMAS
        defaultHospitalShouldNotBeFound("numCamas.in=" + UPDATED_NUM_CAMAS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumCamasIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numCamas is not null
        defaultHospitalShouldBeFound("numCamas.specified=true");

        // Get all the hospitalList where numCamas is null
        defaultHospitalShouldNotBeFound("numCamas.specified=false");
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumCamasContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numCamas contains DEFAULT_NUM_CAMAS
        defaultHospitalShouldBeFound("numCamas.contains=" + DEFAULT_NUM_CAMAS);

        // Get all the hospitalList where numCamas contains UPDATED_NUM_CAMAS
        defaultHospitalShouldNotBeFound("numCamas.contains=" + UPDATED_NUM_CAMAS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumCamasNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numCamas does not contain DEFAULT_NUM_CAMAS
        defaultHospitalShouldNotBeFound("numCamas.doesNotContain=" + DEFAULT_NUM_CAMAS);

        // Get all the hospitalList where numCamas does not contain UPDATED_NUM_CAMAS
        defaultHospitalShouldBeFound("numCamas.doesNotContain=" + UPDATED_NUM_CAMAS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumCuartosIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numCuartos equals to DEFAULT_NUM_CUARTOS
        defaultHospitalShouldBeFound("numCuartos.equals=" + DEFAULT_NUM_CUARTOS);

        // Get all the hospitalList where numCuartos equals to UPDATED_NUM_CUARTOS
        defaultHospitalShouldNotBeFound("numCuartos.equals=" + UPDATED_NUM_CUARTOS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumCuartosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numCuartos not equals to DEFAULT_NUM_CUARTOS
        defaultHospitalShouldNotBeFound("numCuartos.notEquals=" + DEFAULT_NUM_CUARTOS);

        // Get all the hospitalList where numCuartos not equals to UPDATED_NUM_CUARTOS
        defaultHospitalShouldBeFound("numCuartos.notEquals=" + UPDATED_NUM_CUARTOS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumCuartosIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numCuartos in DEFAULT_NUM_CUARTOS or UPDATED_NUM_CUARTOS
        defaultHospitalShouldBeFound("numCuartos.in=" + DEFAULT_NUM_CUARTOS + "," + UPDATED_NUM_CUARTOS);

        // Get all the hospitalList where numCuartos equals to UPDATED_NUM_CUARTOS
        defaultHospitalShouldNotBeFound("numCuartos.in=" + UPDATED_NUM_CUARTOS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumCuartosIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numCuartos is not null
        defaultHospitalShouldBeFound("numCuartos.specified=true");

        // Get all the hospitalList where numCuartos is null
        defaultHospitalShouldNotBeFound("numCuartos.specified=false");
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumCuartosContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numCuartos contains DEFAULT_NUM_CUARTOS
        defaultHospitalShouldBeFound("numCuartos.contains=" + DEFAULT_NUM_CUARTOS);

        // Get all the hospitalList where numCuartos contains UPDATED_NUM_CUARTOS
        defaultHospitalShouldNotBeFound("numCuartos.contains=" + UPDATED_NUM_CUARTOS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByNumCuartosNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where numCuartos does not contain DEFAULT_NUM_CUARTOS
        defaultHospitalShouldNotBeFound("numCuartos.doesNotContain=" + DEFAULT_NUM_CUARTOS);

        // Get all the hospitalList where numCuartos does not contain UPDATED_NUM_CUARTOS
        defaultHospitalShouldBeFound("numCuartos.doesNotContain=" + UPDATED_NUM_CUARTOS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByFechaCreacionIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where fechaCreacion equals to DEFAULT_FECHA_CREACION
        defaultHospitalShouldBeFound("fechaCreacion.equals=" + DEFAULT_FECHA_CREACION);

        // Get all the hospitalList where fechaCreacion equals to UPDATED_FECHA_CREACION
        defaultHospitalShouldNotBeFound("fechaCreacion.equals=" + UPDATED_FECHA_CREACION);
    }

    @Test
    @Transactional
    public void getAllHospitalsByFechaCreacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where fechaCreacion not equals to DEFAULT_FECHA_CREACION
        defaultHospitalShouldNotBeFound("fechaCreacion.notEquals=" + DEFAULT_FECHA_CREACION);

        // Get all the hospitalList where fechaCreacion not equals to UPDATED_FECHA_CREACION
        defaultHospitalShouldBeFound("fechaCreacion.notEquals=" + UPDATED_FECHA_CREACION);
    }

    @Test
    @Transactional
    public void getAllHospitalsByFechaCreacionIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where fechaCreacion in DEFAULT_FECHA_CREACION or UPDATED_FECHA_CREACION
        defaultHospitalShouldBeFound("fechaCreacion.in=" + DEFAULT_FECHA_CREACION + "," + UPDATED_FECHA_CREACION);

        // Get all the hospitalList where fechaCreacion equals to UPDATED_FECHA_CREACION
        defaultHospitalShouldNotBeFound("fechaCreacion.in=" + UPDATED_FECHA_CREACION);
    }

    @Test
    @Transactional
    public void getAllHospitalsByFechaCreacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where fechaCreacion is not null
        defaultHospitalShouldBeFound("fechaCreacion.specified=true");

        // Get all the hospitalList where fechaCreacion is null
        defaultHospitalShouldNotBeFound("fechaCreacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllHospitalsByFechaCreacionContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where fechaCreacion contains DEFAULT_FECHA_CREACION
        defaultHospitalShouldBeFound("fechaCreacion.contains=" + DEFAULT_FECHA_CREACION);

        // Get all the hospitalList where fechaCreacion contains UPDATED_FECHA_CREACION
        defaultHospitalShouldNotBeFound("fechaCreacion.contains=" + UPDATED_FECHA_CREACION);
    }

    @Test
    @Transactional
    public void getAllHospitalsByFechaCreacionNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where fechaCreacion does not contain DEFAULT_FECHA_CREACION
        defaultHospitalShouldNotBeFound("fechaCreacion.doesNotContain=" + DEFAULT_FECHA_CREACION);

        // Get all the hospitalList where fechaCreacion does not contain UPDATED_FECHA_CREACION
        defaultHospitalShouldBeFound("fechaCreacion.doesNotContain=" + UPDATED_FECHA_CREACION);
    }

    @Test
    @Transactional
    public void getAllHospitalsByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where estatus equals to DEFAULT_ESTATUS
        defaultHospitalShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the hospitalList where estatus equals to UPDATED_ESTATUS
        defaultHospitalShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByEstatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where estatus not equals to DEFAULT_ESTATUS
        defaultHospitalShouldNotBeFound("estatus.notEquals=" + DEFAULT_ESTATUS);

        // Get all the hospitalList where estatus not equals to UPDATED_ESTATUS
        defaultHospitalShouldBeFound("estatus.notEquals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultHospitalShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the hospitalList where estatus equals to UPDATED_ESTATUS
        defaultHospitalShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllHospitalsByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where estatus is not null
        defaultHospitalShouldBeFound("estatus.specified=true");

        // Get all the hospitalList where estatus is null
        defaultHospitalShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllHospitalsByDoctorIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);
        Doctor doctor = DoctorResourceIT.createEntity(em);
        em.persist(doctor);
        em.flush();
        hospital.addDoctor(doctor);
        hospitalRepository.saveAndFlush(hospital);
        Long doctorId = doctor.getId();

        // Get all the hospitalList where doctor equals to doctorId
        defaultHospitalShouldBeFound("doctorId.equals=" + doctorId);

        // Get all the hospitalList where doctor equals to doctorId + 1
        defaultHospitalShouldNotBeFound("doctorId.equals=" + (doctorId + 1));
    }

    @Test
    @Transactional
    public void getAllHospitalsByPacienteIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);
        Paciente paciente = PacienteResourceIT.createEntity(em);
        em.persist(paciente);
        em.flush();
        hospital.addPaciente(paciente);
        hospitalRepository.saveAndFlush(hospital);
        Long pacienteId = paciente.getId();

        // Get all the hospitalList where paciente equals to pacienteId
        defaultHospitalShouldBeFound("pacienteId.equals=" + pacienteId);

        // Get all the hospitalList where paciente equals to pacienteId + 1
        defaultHospitalShouldNotBeFound("pacienteId.equals=" + (pacienteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHospitalShouldBeFound(String filter) throws Exception {
        restHospitalMockMvc
            .perform(get("/api/hospitals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospital.getId().intValue())))
            .andExpect(jsonPath("$.[*].idHospital").value(hasItem(DEFAULT_ID_HOSPITAL)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].numPisos").value(hasItem(DEFAULT_NUM_PISOS)))
            .andExpect(jsonPath("$.[*].numCamas").value(hasItem(DEFAULT_NUM_CAMAS)))
            .andExpect(jsonPath("$.[*].numCuartos").value(hasItem(DEFAULT_NUM_CUARTOS)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));

        // Check, that the count call also returns 1
        restHospitalMockMvc
            .perform(get("/api/hospitals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHospitalShouldNotBeFound(String filter) throws Exception {
        restHospitalMockMvc
            .perform(get("/api/hospitals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHospitalMockMvc
            .perform(get("/api/hospitals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingHospital() throws Exception {
        // Get the hospital
        restHospitalMockMvc.perform(get("/api/hospitals/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHospital() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();

        // Update the hospital
        Hospital updatedHospital = hospitalRepository.findById(hospital.getId()).get();
        // Disconnect from session so that the updates on updatedHospital are not directly saved in db
        em.detach(updatedHospital);
        updatedHospital
            .idHospital(UPDATED_ID_HOSPITAL)
            .nombre(UPDATED_NOMBRE)
            .numPisos(UPDATED_NUM_PISOS)
            .numCamas(UPDATED_NUM_CAMAS)
            .numCuartos(UPDATED_NUM_CUARTOS)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .estatus(UPDATED_ESTATUS);
        HospitalDTO hospitalDTO = hospitalMapper.toDto(updatedHospital);

        restHospitalMockMvc
            .perform(put("/api/hospitals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isOk());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
        Hospital testHospital = hospitalList.get(hospitalList.size() - 1);
        assertThat(testHospital.getIdHospital()).isEqualTo(UPDATED_ID_HOSPITAL);
        assertThat(testHospital.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testHospital.getNumPisos()).isEqualTo(UPDATED_NUM_PISOS);
        assertThat(testHospital.getNumCamas()).isEqualTo(UPDATED_NUM_CAMAS);
        assertThat(testHospital.getNumCuartos()).isEqualTo(UPDATED_NUM_CUARTOS);
        assertThat(testHospital.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testHospital.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingHospital() throws Exception {
        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHospitalMockMvc
            .perform(put("/api/hospitals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHospital() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        int databaseSizeBeforeDelete = hospitalRepository.findAll().size();

        // Delete the hospital
        restHospitalMockMvc
            .perform(delete("/api/hospitals/{id}", hospital.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
