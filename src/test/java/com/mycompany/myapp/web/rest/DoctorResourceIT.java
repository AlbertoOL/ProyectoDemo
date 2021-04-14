package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.ProyectoPracticaApp;
import com.mycompany.myapp.domain.Doctor;
import com.mycompany.myapp.domain.Hospital;
import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import com.mycompany.myapp.repository.DoctorRepository;
import com.mycompany.myapp.service.DoctorQueryService;
import com.mycompany.myapp.service.DoctorService;
import com.mycompany.myapp.service.dto.DoctorCriteria;
import com.mycompany.myapp.service.dto.DoctorDTO;
import com.mycompany.myapp.service.mapper.DoctorMapper;
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
 * Integration tests for the {@link DoctorResource} REST controller.
 */
@SpringBootTest(classes = ProyectoPracticaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DoctorResourceIT {
    private static final String DEFAULT_CLAVE_RFC = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE_RFC = "BBBBBBBBBB";

    private static final String DEFAULT_ID_HOSPITAL = "AAAAAAAAAA";
    private static final String UPDATED_ID_HOSPITAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_ESPECIALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final EstatusRegistro DEFAULT_ESTATUS = EstatusRegistro.ACTIVO;
    private static final EstatusRegistro UPDATED_ESTATUS = EstatusRegistro.DESACTIVADO;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorQueryService doctorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoctorMockMvc;

    private Doctor doctor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctor createEntity(EntityManager em) {
        Doctor doctor = new Doctor()
            .claveRfc(DEFAULT_CLAVE_RFC)
            .idHospital(DEFAULT_ID_HOSPITAL)
            .nombre(DEFAULT_NOMBRE)
            .telefono(DEFAULT_TELEFONO)
            .especialidad(DEFAULT_ESPECIALIDAD)
            .email(DEFAULT_EMAIL)
            .estatus(DEFAULT_ESTATUS);
        return doctor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctor createUpdatedEntity(EntityManager em) {
        Doctor doctor = new Doctor()
            .claveRfc(UPDATED_CLAVE_RFC)
            .idHospital(UPDATED_ID_HOSPITAL)
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .especialidad(UPDATED_ESPECIALIDAD)
            .email(UPDATED_EMAIL)
            .estatus(UPDATED_ESTATUS);
        return doctor;
    }

    @BeforeEach
    public void initTest() {
        doctor = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctor() throws Exception {
        int databaseSizeBeforeCreate = doctorRepository.findAll().size();
        // Create the Doctor
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);
        restDoctorMockMvc
            .perform(post("/api/doctors").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isCreated());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeCreate + 1);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getClaveRfc()).isEqualTo(DEFAULT_CLAVE_RFC);
        assertThat(testDoctor.getIdHospital()).isEqualTo(DEFAULT_ID_HOSPITAL);
        assertThat(testDoctor.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testDoctor.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testDoctor.getEspecialidad()).isEqualTo(DEFAULT_ESPECIALIDAD);
        assertThat(testDoctor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDoctor.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    public void createDoctorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorRepository.findAll().size();

        // Create the Doctor with an existing ID
        doctor.setId(1L);
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorMockMvc
            .perform(post("/api/doctors").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkClaveRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setClaveRfc(null);

        // Create the Doctor, which fails.
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        restDoctorMockMvc
            .perform(post("/api/doctors").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdHospitalIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setIdHospital(null);

        // Create the Doctor, which fails.
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        restDoctorMockMvc
            .perform(post("/api/doctors").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setNombre(null);

        // Create the Doctor, which fails.
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        restDoctorMockMvc
            .perform(post("/api/doctors").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setTelefono(null);

        // Create the Doctor, which fails.
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        restDoctorMockMvc
            .perform(post("/api/doctors").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEspecialidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setEspecialidad(null);

        // Create the Doctor, which fails.
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        restDoctorMockMvc
            .perform(post("/api/doctors").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setEstatus(null);

        // Create the Doctor, which fails.
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        restDoctorMockMvc
            .perform(post("/api/doctors").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDoctors() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList
        restDoctorMockMvc
            .perform(get("/api/doctors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].claveRfc").value(hasItem(DEFAULT_CLAVE_RFC)))
            .andExpect(jsonPath("$.[*].idHospital").value(hasItem(DEFAULT_ID_HOSPITAL)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].especialidad").value(hasItem(DEFAULT_ESPECIALIDAD)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));
    }

    @Test
    @Transactional
    public void getDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get the doctor
        restDoctorMockMvc
            .perform(get("/api/doctors/{id}", doctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doctor.getId().intValue()))
            .andExpect(jsonPath("$.claveRfc").value(DEFAULT_CLAVE_RFC))
            .andExpect(jsonPath("$.idHospital").value(DEFAULT_ID_HOSPITAL))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.especialidad").value(DEFAULT_ESPECIALIDAD))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()));
    }

    @Test
    @Transactional
    public void getDoctorsByIdFiltering() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        Long id = doctor.getId();

        defaultDoctorShouldBeFound("id.equals=" + id);
        defaultDoctorShouldNotBeFound("id.notEquals=" + id);

        defaultDoctorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDoctorShouldNotBeFound("id.greaterThan=" + id);

        defaultDoctorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDoctorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllDoctorsByClaveRfcIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where claveRfc equals to DEFAULT_CLAVE_RFC
        defaultDoctorShouldBeFound("claveRfc.equals=" + DEFAULT_CLAVE_RFC);

        // Get all the doctorList where claveRfc equals to UPDATED_CLAVE_RFC
        defaultDoctorShouldNotBeFound("claveRfc.equals=" + UPDATED_CLAVE_RFC);
    }

    @Test
    @Transactional
    public void getAllDoctorsByClaveRfcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where claveRfc not equals to DEFAULT_CLAVE_RFC
        defaultDoctorShouldNotBeFound("claveRfc.notEquals=" + DEFAULT_CLAVE_RFC);

        // Get all the doctorList where claveRfc not equals to UPDATED_CLAVE_RFC
        defaultDoctorShouldBeFound("claveRfc.notEquals=" + UPDATED_CLAVE_RFC);
    }

    @Test
    @Transactional
    public void getAllDoctorsByClaveRfcIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where claveRfc in DEFAULT_CLAVE_RFC or UPDATED_CLAVE_RFC
        defaultDoctorShouldBeFound("claveRfc.in=" + DEFAULT_CLAVE_RFC + "," + UPDATED_CLAVE_RFC);

        // Get all the doctorList where claveRfc equals to UPDATED_CLAVE_RFC
        defaultDoctorShouldNotBeFound("claveRfc.in=" + UPDATED_CLAVE_RFC);
    }

    @Test
    @Transactional
    public void getAllDoctorsByClaveRfcIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where claveRfc is not null
        defaultDoctorShouldBeFound("claveRfc.specified=true");

        // Get all the doctorList where claveRfc is null
        defaultDoctorShouldNotBeFound("claveRfc.specified=false");
    }

    @Test
    @Transactional
    public void getAllDoctorsByClaveRfcContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where claveRfc contains DEFAULT_CLAVE_RFC
        defaultDoctorShouldBeFound("claveRfc.contains=" + DEFAULT_CLAVE_RFC);

        // Get all the doctorList where claveRfc contains UPDATED_CLAVE_RFC
        defaultDoctorShouldNotBeFound("claveRfc.contains=" + UPDATED_CLAVE_RFC);
    }

    @Test
    @Transactional
    public void getAllDoctorsByClaveRfcNotContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where claveRfc does not contain DEFAULT_CLAVE_RFC
        defaultDoctorShouldNotBeFound("claveRfc.doesNotContain=" + DEFAULT_CLAVE_RFC);

        // Get all the doctorList where claveRfc does not contain UPDATED_CLAVE_RFC
        defaultDoctorShouldBeFound("claveRfc.doesNotContain=" + UPDATED_CLAVE_RFC);
    }

    @Test
    @Transactional
    public void getAllDoctorsByIdHospitalIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where idHospital equals to DEFAULT_ID_HOSPITAL
        defaultDoctorShouldBeFound("idHospital.equals=" + DEFAULT_ID_HOSPITAL);

        // Get all the doctorList where idHospital equals to UPDATED_ID_HOSPITAL
        defaultDoctorShouldNotBeFound("idHospital.equals=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllDoctorsByIdHospitalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where idHospital not equals to DEFAULT_ID_HOSPITAL
        defaultDoctorShouldNotBeFound("idHospital.notEquals=" + DEFAULT_ID_HOSPITAL);

        // Get all the doctorList where idHospital not equals to UPDATED_ID_HOSPITAL
        defaultDoctorShouldBeFound("idHospital.notEquals=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllDoctorsByIdHospitalIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where idHospital in DEFAULT_ID_HOSPITAL or UPDATED_ID_HOSPITAL
        defaultDoctorShouldBeFound("idHospital.in=" + DEFAULT_ID_HOSPITAL + "," + UPDATED_ID_HOSPITAL);

        // Get all the doctorList where idHospital equals to UPDATED_ID_HOSPITAL
        defaultDoctorShouldNotBeFound("idHospital.in=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllDoctorsByIdHospitalIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where idHospital is not null
        defaultDoctorShouldBeFound("idHospital.specified=true");

        // Get all the doctorList where idHospital is null
        defaultDoctorShouldNotBeFound("idHospital.specified=false");
    }

    @Test
    @Transactional
    public void getAllDoctorsByIdHospitalContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where idHospital contains DEFAULT_ID_HOSPITAL
        defaultDoctorShouldBeFound("idHospital.contains=" + DEFAULT_ID_HOSPITAL);

        // Get all the doctorList where idHospital contains UPDATED_ID_HOSPITAL
        defaultDoctorShouldNotBeFound("idHospital.contains=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllDoctorsByIdHospitalNotContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where idHospital does not contain DEFAULT_ID_HOSPITAL
        defaultDoctorShouldNotBeFound("idHospital.doesNotContain=" + DEFAULT_ID_HOSPITAL);

        // Get all the doctorList where idHospital does not contain UPDATED_ID_HOSPITAL
        defaultDoctorShouldBeFound("idHospital.doesNotContain=" + UPDATED_ID_HOSPITAL);
    }

    @Test
    @Transactional
    public void getAllDoctorsByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where nombre equals to DEFAULT_NOMBRE
        defaultDoctorShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the doctorList where nombre equals to UPDATED_NOMBRE
        defaultDoctorShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllDoctorsByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where nombre not equals to DEFAULT_NOMBRE
        defaultDoctorShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the doctorList where nombre not equals to UPDATED_NOMBRE
        defaultDoctorShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllDoctorsByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultDoctorShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the doctorList where nombre equals to UPDATED_NOMBRE
        defaultDoctorShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllDoctorsByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where nombre is not null
        defaultDoctorShouldBeFound("nombre.specified=true");

        // Get all the doctorList where nombre is null
        defaultDoctorShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllDoctorsByNombreContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where nombre contains DEFAULT_NOMBRE
        defaultDoctorShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the doctorList where nombre contains UPDATED_NOMBRE
        defaultDoctorShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllDoctorsByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where nombre does not contain DEFAULT_NOMBRE
        defaultDoctorShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the doctorList where nombre does not contain UPDATED_NOMBRE
        defaultDoctorShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllDoctorsByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where telefono equals to DEFAULT_TELEFONO
        defaultDoctorShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the doctorList where telefono equals to UPDATED_TELEFONO
        defaultDoctorShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllDoctorsByTelefonoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where telefono not equals to DEFAULT_TELEFONO
        defaultDoctorShouldNotBeFound("telefono.notEquals=" + DEFAULT_TELEFONO);

        // Get all the doctorList where telefono not equals to UPDATED_TELEFONO
        defaultDoctorShouldBeFound("telefono.notEquals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllDoctorsByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultDoctorShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the doctorList where telefono equals to UPDATED_TELEFONO
        defaultDoctorShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllDoctorsByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where telefono is not null
        defaultDoctorShouldBeFound("telefono.specified=true");

        // Get all the doctorList where telefono is null
        defaultDoctorShouldNotBeFound("telefono.specified=false");
    }

    @Test
    @Transactional
    public void getAllDoctorsByTelefonoContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where telefono contains DEFAULT_TELEFONO
        defaultDoctorShouldBeFound("telefono.contains=" + DEFAULT_TELEFONO);

        // Get all the doctorList where telefono contains UPDATED_TELEFONO
        defaultDoctorShouldNotBeFound("telefono.contains=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllDoctorsByTelefonoNotContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where telefono does not contain DEFAULT_TELEFONO
        defaultDoctorShouldNotBeFound("telefono.doesNotContain=" + DEFAULT_TELEFONO);

        // Get all the doctorList where telefono does not contain UPDATED_TELEFONO
        defaultDoctorShouldBeFound("telefono.doesNotContain=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEspecialidadIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where especialidad equals to DEFAULT_ESPECIALIDAD
        defaultDoctorShouldBeFound("especialidad.equals=" + DEFAULT_ESPECIALIDAD);

        // Get all the doctorList where especialidad equals to UPDATED_ESPECIALIDAD
        defaultDoctorShouldNotBeFound("especialidad.equals=" + UPDATED_ESPECIALIDAD);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEspecialidadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where especialidad not equals to DEFAULT_ESPECIALIDAD
        defaultDoctorShouldNotBeFound("especialidad.notEquals=" + DEFAULT_ESPECIALIDAD);

        // Get all the doctorList where especialidad not equals to UPDATED_ESPECIALIDAD
        defaultDoctorShouldBeFound("especialidad.notEquals=" + UPDATED_ESPECIALIDAD);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEspecialidadIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where especialidad in DEFAULT_ESPECIALIDAD or UPDATED_ESPECIALIDAD
        defaultDoctorShouldBeFound("especialidad.in=" + DEFAULT_ESPECIALIDAD + "," + UPDATED_ESPECIALIDAD);

        // Get all the doctorList where especialidad equals to UPDATED_ESPECIALIDAD
        defaultDoctorShouldNotBeFound("especialidad.in=" + UPDATED_ESPECIALIDAD);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEspecialidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where especialidad is not null
        defaultDoctorShouldBeFound("especialidad.specified=true");

        // Get all the doctorList where especialidad is null
        defaultDoctorShouldNotBeFound("especialidad.specified=false");
    }

    @Test
    @Transactional
    public void getAllDoctorsByEspecialidadContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where especialidad contains DEFAULT_ESPECIALIDAD
        defaultDoctorShouldBeFound("especialidad.contains=" + DEFAULT_ESPECIALIDAD);

        // Get all the doctorList where especialidad contains UPDATED_ESPECIALIDAD
        defaultDoctorShouldNotBeFound("especialidad.contains=" + UPDATED_ESPECIALIDAD);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEspecialidadNotContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where especialidad does not contain DEFAULT_ESPECIALIDAD
        defaultDoctorShouldNotBeFound("especialidad.doesNotContain=" + DEFAULT_ESPECIALIDAD);

        // Get all the doctorList where especialidad does not contain UPDATED_ESPECIALIDAD
        defaultDoctorShouldBeFound("especialidad.doesNotContain=" + UPDATED_ESPECIALIDAD);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where email equals to DEFAULT_EMAIL
        defaultDoctorShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the doctorList where email equals to UPDATED_EMAIL
        defaultDoctorShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where email not equals to DEFAULT_EMAIL
        defaultDoctorShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the doctorList where email not equals to UPDATED_EMAIL
        defaultDoctorShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultDoctorShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the doctorList where email equals to UPDATED_EMAIL
        defaultDoctorShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where email is not null
        defaultDoctorShouldBeFound("email.specified=true");

        // Get all the doctorList where email is null
        defaultDoctorShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllDoctorsByEmailContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where email contains DEFAULT_EMAIL
        defaultDoctorShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the doctorList where email contains UPDATED_EMAIL
        defaultDoctorShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where email does not contain DEFAULT_EMAIL
        defaultDoctorShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the doctorList where email does not contain UPDATED_EMAIL
        defaultDoctorShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where estatus equals to DEFAULT_ESTATUS
        defaultDoctorShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the doctorList where estatus equals to UPDATED_ESTATUS
        defaultDoctorShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEstatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where estatus not equals to DEFAULT_ESTATUS
        defaultDoctorShouldNotBeFound("estatus.notEquals=" + DEFAULT_ESTATUS);

        // Get all the doctorList where estatus not equals to UPDATED_ESTATUS
        defaultDoctorShouldBeFound("estatus.notEquals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultDoctorShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the doctorList where estatus equals to UPDATED_ESTATUS
        defaultDoctorShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllDoctorsByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList where estatus is not null
        defaultDoctorShouldBeFound("estatus.specified=true");

        // Get all the doctorList where estatus is null
        defaultDoctorShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllDoctorsByHospitalIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);
        Hospital hospital = HospitalResourceIT.createEntity(em);
        em.persist(hospital);
        em.flush();
        doctor.setHospital(hospital);
        doctorRepository.saveAndFlush(doctor);
        Long hospitalId = hospital.getId();

        // Get all the doctorList where hospital equals to hospitalId
        defaultDoctorShouldBeFound("hospitalId.equals=" + hospitalId);

        // Get all the doctorList where hospital equals to hospitalId + 1
        defaultDoctorShouldNotBeFound("hospitalId.equals=" + (hospitalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDoctorShouldBeFound(String filter) throws Exception {
        restDoctorMockMvc
            .perform(get("/api/doctors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].claveRfc").value(hasItem(DEFAULT_CLAVE_RFC)))
            .andExpect(jsonPath("$.[*].idHospital").value(hasItem(DEFAULT_ID_HOSPITAL)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].especialidad").value(hasItem(DEFAULT_ESPECIALIDAD)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));

        // Check, that the count call also returns 1
        restDoctorMockMvc
            .perform(get("/api/doctors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDoctorShouldNotBeFound(String filter) throws Exception {
        restDoctorMockMvc
            .perform(get("/api/doctors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDoctorMockMvc
            .perform(get("/api/doctors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDoctor() throws Exception {
        // Get the doctor
        restDoctorMockMvc.perform(get("/api/doctors/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Update the doctor
        Doctor updatedDoctor = doctorRepository.findById(doctor.getId()).get();
        // Disconnect from session so that the updates on updatedDoctor are not directly saved in db
        em.detach(updatedDoctor);
        updatedDoctor
            .claveRfc(UPDATED_CLAVE_RFC)
            .idHospital(UPDATED_ID_HOSPITAL)
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .especialidad(UPDATED_ESPECIALIDAD)
            .email(UPDATED_EMAIL)
            .estatus(UPDATED_ESTATUS);
        DoctorDTO doctorDTO = doctorMapper.toDto(updatedDoctor);

        restDoctorMockMvc
            .perform(put("/api/doctors").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isOk());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getClaveRfc()).isEqualTo(UPDATED_CLAVE_RFC);
        assertThat(testDoctor.getIdHospital()).isEqualTo(UPDATED_ID_HOSPITAL);
        assertThat(testDoctor.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDoctor.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testDoctor.getEspecialidad()).isEqualTo(UPDATED_ESPECIALIDAD);
        assertThat(testDoctor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDoctor.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Create the Doctor
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(put("/api/doctors").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        int databaseSizeBeforeDelete = doctorRepository.findAll().size();

        // Delete the doctor
        restDoctorMockMvc
            .perform(delete("/api/doctors/{id}", doctor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
