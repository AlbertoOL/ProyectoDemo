package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Hospital.
 */
@Entity
@Table(name = "hospital")
public class Hospital implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Identificador del hospital
     */
    @NotNull
    @Column(name = "id_hospital", nullable = false, unique = true)
    private String idHospital;

    /**
     * Nombre del Hospital
     */
    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Numero de pisos en el hospital
     */
    @NotNull
    @Column(name = "num_pisos", nullable = false)
    private String numPisos;

    /**
     * Numero de camas en el hospital
     */
    @NotNull
    @Column(name = "num_camas", nullable = false)
    private String numCamas;

    /**
     * NUmero de cuartos
     */
    @NotNull
    @Column(name = "num_cuartos", nullable = false)
    private String numCuartos;

    /**
     * Fecha en la que se cosntruyó el hospital
     */
    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", nullable = false)
    private EstatusRegistro estatus;

    @OneToMany(mappedBy = "hospital")
    private Set<Doctor> doctors = new HashSet<>();

    @OneToMany(mappedBy = "hospital")
    private Set<Paciente> pacientes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdHospital() {
        return idHospital;
    }

    public Hospital idHospital(String idHospital) {
        this.idHospital = idHospital;
        return this;
    }

    public void setIdHospital(String idHospital) {
        this.idHospital = idHospital;
    }

    public String getNombre() {
        return nombre;
    }

    public Hospital nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumPisos() {
        return numPisos;
    }

    public Hospital numPisos(String numPisos) {
        this.numPisos = numPisos;
        return this;
    }

    public void setNumPisos(String numPisos) {
        this.numPisos = numPisos;
    }

    public String getNumCamas() {
        return numCamas;
    }

    public Hospital numCamas(String numCamas) {
        this.numCamas = numCamas;
        return this;
    }

    public void setNumCamas(String numCamas) {
        this.numCamas = numCamas;
    }

    public String getNumCuartos() {
        return numCuartos;
    }

    public Hospital numCuartos(String numCuartos) {
        this.numCuartos = numCuartos;
        return this;
    }

    public void setNumCuartos(String numCuartos) {
        this.numCuartos = numCuartos;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public Hospital fechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstatusRegistro getEstatus() {
        return estatus;
    }

    public Hospital estatus(EstatusRegistro estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(EstatusRegistro estatus) {
        this.estatus = estatus;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public Hospital doctors(Set<Doctor> doctors) {
        this.doctors = doctors;
        return this;
    }

    public Hospital addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
        doctor.setHospital(this);
        return this;
    }

    public Hospital removeDoctor(Doctor doctor) {
        this.doctors.remove(doctor);
        doctor.setHospital(null);
        return this;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public Hospital pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public Hospital addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setHospital(this);
        return this;
    }

    public Hospital removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setHospital(null);
        return this;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hospital)) {
            return false;
        }
        return id != null && id.equals(((Hospital) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hospital{" +
            "id=" + getId() +
            ", idHospital='" + getIdHospital() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", numPisos='" + getNumPisos() + "'" +
            ", numCamas='" + getNumCamas() + "'" +
            ", numCuartos='" + getNumCuartos() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}
