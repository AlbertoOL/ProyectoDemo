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
 * Criteria class for the {@link com.mycompany.myapp.domain.Hospital} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.HospitalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /hospitals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HospitalCriteria implements Serializable, Criteria {

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

    private StringFilter idHospital;

    private StringFilter nombre;

    private StringFilter numPisos;

    private StringFilter numCamas;

    private StringFilter numCuartos;

    private StringFilter fechaCreacion;

    private EstatusRegistroFilter estatus;

    private LongFilter doctorId;

    private LongFilter pacienteId;

    public HospitalCriteria() {}

    public HospitalCriteria(HospitalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idHospital = other.idHospital == null ? null : other.idHospital.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.numPisos = other.numPisos == null ? null : other.numPisos.copy();
        this.numCamas = other.numCamas == null ? null : other.numCamas.copy();
        this.numCuartos = other.numCuartos == null ? null : other.numCuartos.copy();
        this.fechaCreacion = other.fechaCreacion == null ? null : other.fechaCreacion.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
        this.doctorId = other.doctorId == null ? null : other.doctorId.copy();
        this.pacienteId = other.pacienteId == null ? null : other.pacienteId.copy();
    }

    @Override
    public HospitalCriteria copy() {
        return new HospitalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public StringFilter getNumPisos() {
        return numPisos;
    }

    public void setNumPisos(StringFilter numPisos) {
        this.numPisos = numPisos;
    }

    public StringFilter getNumCamas() {
        return numCamas;
    }

    public void setNumCamas(StringFilter numCamas) {
        this.numCamas = numCamas;
    }

    public StringFilter getNumCuartos() {
        return numCuartos;
    }

    public void setNumCuartos(StringFilter numCuartos) {
        this.numCuartos = numCuartos;
    }

    public StringFilter getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(StringFilter fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstatusRegistroFilter getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusRegistroFilter estatus) {
        this.estatus = estatus;
    }

    public LongFilter getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(LongFilter doctorId) {
        this.doctorId = doctorId;
    }

    public LongFilter getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(LongFilter pacienteId) {
        this.pacienteId = pacienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HospitalCriteria that = (HospitalCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idHospital, that.idHospital) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(numPisos, that.numPisos) &&
            Objects.equals(numCamas, that.numCamas) &&
            Objects.equals(numCuartos, that.numCuartos) &&
            Objects.equals(fechaCreacion, that.fechaCreacion) &&
            Objects.equals(estatus, that.estatus) &&
            Objects.equals(doctorId, that.doctorId) &&
            Objects.equals(pacienteId, that.pacienteId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idHospital, nombre, numPisos, numCamas, numCuartos, fechaCreacion, estatus, doctorId, pacienteId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HospitalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idHospital != null ? "idHospital=" + idHospital + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (numPisos != null ? "numPisos=" + numPisos + ", " : "") +
                (numCamas != null ? "numCamas=" + numCamas + ", " : "") +
                (numCuartos != null ? "numCuartos=" + numCuartos + ", " : "") +
                (fechaCreacion != null ? "fechaCreacion=" + fechaCreacion + ", " : "") +
                (estatus != null ? "estatus=" + estatus + ", " : "") +
                (doctorId != null ? "doctorId=" + doctorId + ", " : "") +
                (pacienteId != null ? "pacienteId=" + pacienteId + ", " : "") +
            "}";
    }
}
