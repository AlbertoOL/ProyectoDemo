package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Doctor} entity.
 */
public class DoctorDTO implements Serializable {
    private Long id;

    /**
     * La clave RFC del doctor
     */
    @NotNull
    @ApiModelProperty(value = "La clave RFC del doctor", required = true)
    private String claveRfc;

    /**
     * Identificador del hospital
     */
    @NotNull
    @ApiModelProperty(value = "Identificador del hospital", required = true)
    private String idHospital;

    /**
     * Nombre del doctor
     */
    @NotNull
    @ApiModelProperty(value = "Nombre del doctor", required = true)
    private String nombre;

    /**
     * Telefono
     */
    @NotNull
    @ApiModelProperty(value = "Telefono", required = true)
    private String telefono;

    /**
     * Especialidad
     */
    @NotNull
    @ApiModelProperty(value = "Especialidad", required = true)
    private String especialidad;

    /**
     * Correo
     */
    @ApiModelProperty(value = "Correo")
    private String email;

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

    public String getClaveRfc() {
        return claveRfc;
    }

    public void setClaveRfc(String claveRfc) {
        this.claveRfc = claveRfc;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (!(o instanceof DoctorDTO)) {
            return false;
        }

        return id != null && id.equals(((DoctorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorDTO{" +
            "id=" + getId() +
            ", claveRfc='" + getClaveRfc() + "'" +
            ", idHospital='" + getIdHospital() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", especialidad='" + getEspecialidad() + "'" +
            ", email='" + getEmail() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", hospitalId=" + getHospitalId() +
            ", hospitalIdHospital='" + getHospitalIdHospital() + "'" +
            "}";
    }
}
