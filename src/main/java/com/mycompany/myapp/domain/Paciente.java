package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Numero de Seguro Social (NSS)
     */
    @NotNull
    @Column(name = "nss", nullable = false, unique = true)
    private String nss;

    /**
     * Identificador del hospital
     */
    @NotNull
    @Column(name = "id_hospital", nullable = false)
    private String idHospital;

    /**
     * Nombre del paciente
     */
    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Apellido paterno
     */
    @NotNull
    @Column(name = "ap_paterno", nullable = false)
    private String apPaterno;

    /**
     * Apellido materno
     */
    @NotNull
    @Column(name = "ap_materno", nullable = false)
    private String apMaterno;

    /**
     * Fecha de nacimiento
     */
    @NotNull
    @Column(name = "fecha_nac", nullable = false)
    private String fechaNac;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", nullable = false)
    private EstatusRegistro estatus;

    @ManyToOne
    @JsonIgnoreProperties(value = "pacientes", allowSetters = true)
    private Hospital hospital;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNss() {
        return nss;
    }

    public Paciente nss(String nss) {
        this.nss = nss;
        return this;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getIdHospital() {
        return idHospital;
    }

    public Paciente idHospital(String idHospital) {
        this.idHospital = idHospital;
        return this;
    }

    public void setIdHospital(String idHospital) {
        this.idHospital = idHospital;
    }

    public String getNombre() {
        return nombre;
    }

    public Paciente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public Paciente apPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
        return this;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public Paciente apMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
        return this;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public Paciente fechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
        return this;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public EstatusRegistro getEstatus() {
        return estatus;
    }

    public Paciente estatus(EstatusRegistro estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(EstatusRegistro estatus) {
        this.estatus = estatus;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public Paciente hospital(Hospital hospital) {
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
        if (!(o instanceof Paciente)) {
            return false;
        }
        return id != null && id.equals(((Paciente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", nss='" + getNss() + "'" +
            ", idHospital='" + getIdHospital() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apPaterno='" + getApPaterno() + "'" +
            ", apMaterno='" + getApMaterno() + "'" +
            ", fechaNac='" + getFechaNac() + "'" +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}
