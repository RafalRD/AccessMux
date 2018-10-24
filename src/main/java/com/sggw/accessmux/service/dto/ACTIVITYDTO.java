package com.sggw.accessmux.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ACTIVITY entity.
 */
public class ACTIVITYDTO implements Serializable {

    private Long id;

    @NotNull
    private String login;

    @NotNull
    private String role;

    @NotNull
    private String url;

    @NotNull
    private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ACTIVITYDTO aCTIVITYDTO = (ACTIVITYDTO) o;
        if (aCTIVITYDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aCTIVITYDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ACTIVITYDTO{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", role='" + getRole() + "'" +
            ", url='" + getUrl() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
