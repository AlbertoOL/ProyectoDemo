package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Paciente} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PacienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pacientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PacienteCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EstatusRegistro
     */
    public static class EstatusRegistroFilter extends Filter<EstatusRegistro> {

        public EstatusRegistroFilter() {}

        public EstatusRegistroFilter(EstatusRegistroFilter filter) {
            super(filter);
        }

        @Override
        public EstatusRegistroFilter copy() {
            return new EstatusRegistroFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nss;

    private StringFilter idHospital;

    private StringFilter nombre;

    private StringFilter apPaterno;

    private StringFilter apMaterno;

    private StringFilter fechaNac;

    private EstatusRegistroFilter estatus;

    private LongFilter hospitalId;

    public PacienteCriteria() {}

    public PacienteCriteria(PacienteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nss = other.nss == null ? null : other.nss.copy();
        this.idHospital = other.idHospital == null ? null : other.idHospital.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.apPaterno = other.apPaterno == null ? null : other.apPaterno.copy();
        this.apMaterno = other.apMaterno == null ? null : other.apMaterno.copy();
        this.fechaNac = other.fechaNac == null ? null : other.fechaNac.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
        this.hospitalId = other.hospitalId == null ? null : other.hospitalId.copy();
    }

    @Override
    public PacienteCriteria copy() {
        return new PacienteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNss() {
        return nss;
    }

    public void setNss(StringFilter nss) {
        this.nss = nss;
    }

    public StringFilter getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(StringFilter idHospital) {
        this.idHospital = idHospital;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(StringFilter apPaterno) {
        this.apPaterno = apPaterno;
    }

    public StringFilter getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(StringFilter apMaterno) {
        this.apMaterno = apMaterno;
    }

    public StringFilter getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(StringFilter fechaNac) {
        this.fechaNac = fechaNac;
    }

    public EstatusRegistroFilter getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusRegistroFilter estatus) {
        this.estatus = estatus;
    }

    public LongFilter getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(LongFilter hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PacienteCriteria that = (PacienteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nss, that.nss) &&
            Objects.equals(idHospital, that.idHospital) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(apPaterno, that.apPaterno) &&
            Objects.equals(apMaterno, that.apMaterno) &&
            Objects.equals(fechaNac, that.fechaNac) &&
            Objects.equals(estatus, that.estatus) &&
            Objects.equals(hospitalId, that.hospitalId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nss, idHospital, nombre, apPaterno, apMaterno, fechaNac, estatus, hospitalId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PacienteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nss != null ? "nss=" + nss + ", " : "") +
                (idHospital != null ? "idHospital=" + idHospital + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (apPaterno != null ? "apPaterno=" + apPaterno + ", " : "") +
                (apMaterno != null ? "apMaterno=" + apMaterno + ", " : "") +
                (fechaNac != null ? "fechaNac=" + fechaNac + ", " : "") +
                (estatus != null ? "estatus=" + estatus + ", " : "") +
                (hospitalId != null ? "hospitalId=" + hospitalId + ", " : "") +
            "}";
    }
}
