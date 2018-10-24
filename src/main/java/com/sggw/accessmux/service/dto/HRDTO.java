package com.sggw.accessmux.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the HR entity.
 */
public class HRDTO implements Serializable {

    private Long id;

    @NotNull
    private String url;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HRDTO hRDTO = (HRDTO) o;
        if (hRDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hRDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HRDTO{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
