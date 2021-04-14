package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Paciente} entity.
 */
public class PacienteDTO implements Serializable {
    private Long id;

    /**
     * Numero de Seguro Social (NSS)
     */
    @NotNull
    @ApiModelProperty(value = "Numero de Seguro Social (NSS)", required = true)
    private String nss;

    /**
     * Identificador del hospital
     */
    @NotNull
    @ApiModelProperty(value = "Identificador del hospital", required = true)
    private String idHospital;

    /**
     * Nombre del paciente
     */
    @NotNull
    @ApiModelProperty(value = "Nombre del paciente", required = true)
    private String nombre;

    /**
     * Apellido paterno
     */
    @NotNull
    @ApiModelProperty(value = "Apellido paterno", required = true)
    private String apPaterno;

    /**
     * Apellido materno
     */
    @NotNull
    @ApiModelProperty(value = "Apellido materno", required = true)
    private String apMaterno;

    /**
     * Fecha de nacimiento
     */
    @NotNull
    @ApiModelProperty(value = "Fecha de nacimiento", required = true)
    private String fechaNac;

    @NotNull
    private EstatusRegistro estatus;

    private Long hospitalId;

    private String hospitalIdHospital;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(String idHospital) {
        this.idHospital = idHospital;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public EstatusRegistro getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusRegistro estatus) {
        this.estatus = estatus;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalIdHospital() {
        return hospitalIdHospital;
    }

    public void setHospitalIdHospital(String hospitalIdHospital) {
        this.hospitalIdHospital = hospitalIdHospital;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PacienteDTO)) {
            return false;
        }

        return id != null && id.equals(((PacienteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PacienteDTO{" +
            "id=" + getId() +
            ", nss='" + getNss() + "'" +
            ", idHospital='" + getIdHospital() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apPaterno='" + getApPaterno() + "'" +
            ", apMaterno='" + getApMaterno() + "'" +
            ", fechaNac='" + getFechaNac() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", hospitalId=" + getHospitalId() +
            ", hospitalIdHospital='" + getHospitalIdHospital() + "'" +
            "}";
    }
}
