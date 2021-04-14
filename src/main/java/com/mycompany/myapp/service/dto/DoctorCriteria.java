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
 * Criteria class for the {@link com.mycompany.myapp.domain.Doctor} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.DoctorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /doctors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DoctorCriteria implements Serializable, Criteria {

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

    private StringFilter claveRfc;

    private StringFilter idHospital;

    private StringFilter nombre;

    private StringFilter telefono;

    private StringFilter especialidad;

    private StringFilter email;

    private EstatusRegistroFilter estatus;

    private LongFilter hospitalId;

    public DoctorCriteria() {}

    public DoctorCriteria(DoctorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.claveRfc = other.claveRfc == null ? null : other.claveRfc.copy();
        this.idHospital = other.idHospital == null ? null : other.idHospital.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.especialidad = other.especialidad == null ? null : other.especialidad.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
        this.hospitalId = other.hospitalId == null ? null : other.hospitalId.copy();
    }

    @Override
    public DoctorCriteria copy() {
        return new DoctorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getClaveRfc() {
        return claveRfc;
    }

    public void setClaveRfc(StringFilter claveRfc) {
        this.claveRfc = claveRfc;
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

    public StringFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
    }

    public StringFilter getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(StringFilter especialidad) {
        this.especialidad = especialidad;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
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
        final DoctorCriteria that = (DoctorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(claveRfc, that.claveRfc) &&
            Objects.equals(idHospital, that.idHospital) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(especialidad, that.especialidad) &&
            Objects.equals(email, that.email) &&
            Objects.equals(estatus, that.estatus) &&
            Objects.equals(hospitalId, that.hospitalId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, claveRfc, idHospital, nombre, telefono, especialidad, email, estatus, hospitalId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (claveRfc != null ? "claveRfc=" + claveRfc + ", " : "") +
                (idHospital != null ? "idHospital=" + idHospital + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
                (especialidad != null ? "especialidad=" + especialidad + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (estatus != null ? "estatus=" + estatus + ", " : "") +
                (hospitalId != null ? "hospitalId=" + hospitalId + ", " : "") +
            "}";
    }
}
