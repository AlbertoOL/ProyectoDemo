package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Hospital} entity.
 */
public class HospitalDTO implements Serializable {
    private Long id;

    /**
     * Identificador del hospital
     */
    @NotNull
    @ApiModelProperty(value = "Identificador del hospital", required = true)
    private String idHospital;

    /**
     * Nombre del Hospital
     */
    @NotNull
    @ApiModelProperty(value = "Nombre del Hospital", required = true)
    private String nombre;

    /**
     * Numero de pisos en el hospital
     */
    @NotNull
    @ApiModelProperty(value = "Numero de pisos en el hospital", required = true)
    private String numPisos;

    /**
     * Numero de camas en el hospital
     */
    @NotNull
    @ApiModelProperty(value = "Numero de camas en el hospital", required = true)
    private String numCamas;

    /**
     * NUmero de cuartos
     */
    @NotNull
    @ApiModelProperty(value = "NUmero de cuartos", required = true)
    private String numCuartos;

    /**
     * Fecha en la que se cosntruyó el hospital
     */
    @ApiModelProperty(value = "Fecha en la que se cosntruyó el hospital")
    private String fechaCreacion;

    @NotNull
    private EstatusRegistro estatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNumPisos() {
        return numPisos;
    }

    public void setNumPisos(String numPisos) {
        this.numPisos = numPisos;
    }

    public String getNumCamas() {
        return numCamas;
    }

    public void setNumCamas(String numCamas) {
        this.numCamas = numCamas;
    }

    public String getNumCuartos() {
        return numCuartos;
    }

    public void setNumCuartos(String numCuartos) {
        this.numCuartos = numCuartos;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstatusRegistro getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusRegistro estatus) {
        this.estatus = estatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HospitalDTO)) {
            return false;
        }

        return id != null && id.equals(((HospitalDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HospitalDTO{" +
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
