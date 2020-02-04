package com.adviserit.ms.industrias.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.adviserit.ms.industrias.domain.enumeration.Categoria;

/**
 * A Industrias.
 */
@Entity
@Table(name = "industrias")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Industrias implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "industria", length = 100, nullable = false, unique = true)
    private String industria;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Categoria categoria;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndustria() {
        return industria;
    }

    public Industrias industria(String industria) {
        this.industria = industria;
        return this;
    }

    public void setIndustria(String industria) {
        this.industria = industria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Industrias categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Industrias)) {
            return false;
        }
        return id != null && id.equals(((Industrias) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Industrias{" +
            "id=" + getId() +
            ", industria='" + getIndustria() + "'" +
            ", categoria='" + getCategoria() + "'" +
            "}";
    }
}
