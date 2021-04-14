package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Doctor.
 */
@Entity
@Table(name = "doctor")
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * La clave RFC del doctor
     */
    @NotNull
    @Column(name = "clave_rfc", nullable = false, unique = true)
    private String claveRfc;

    /**
     * Identificador del hospital
     */
    @NotNull
    @Column(name = "id_hospital", nullable = false)
    private String idHospital;

    /**
     * Nombre del doctor
     */
    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Telefono
     */
    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    /**
     * Especialidad
     */
    @NotNull
    @Column(name = "especialidad", nullable = false)
    private String especialidad;

    /**
     * Correo
     */
    @Column(name = "email")
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", nullable = false)
    private EstatusRegistro estatus;

    @ManyToOne
    @JsonIgnoreProperties(value = "doctors", allowSetters = true)
    private Hospital hospital;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClaveRfc() {
        return claveRfc;
    }

    public Doctor claveRfc(String claveRfc) {
        this.claveRfc = claveRfc;
        return this;
    }

    public void setClaveRfc(String claveRfc) {
        this.claveRfc = claveRfc;
    }

    public String getIdHospital() {
        return idHospital;
    }

    public Doctor idHospital(String idHospital) {
        this.idHospital = idHospital;
        return this;
    }

    public void setIdHospital(String idHospital) {
        this.idHospital = idHospital;
    }

    public String getNombre() {
        return nombre;
    }

    public Doctor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public Doctor telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public Doctor especialidad(String especialidad) {
        this.especialidad = especialidad;
        return this;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEmail() {
        return email;
    }

    public Doctor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EstatusRegistro getEstatus() {
        return estatus;
    }

    public Doctor estatus(EstatusRegistro estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(EstatusRegistro estatus) {
        this.estatus = estatus;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public Doctor hospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doctor)) {
            return false;
        }
        return id != null && id.equals(((Doctor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Doctor{" +
            "id=" + getId() +
            ", claveRfc='" + getClaveRfc() + "'" +
            ", idHospital='" + getIdHospital() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", especialidad='" + getEspecialidad() + "'" +
            ", email='" + getEmail() + "'" +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}
