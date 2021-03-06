package hu.tsystems.devlad.service.dto;


import javax.validation.constraints.*;

import hu.tsystems.devlad.domain.enumeration.Level;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Developer entity.
 */
public class DeveloperDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2021641732814945657L;

	private Long id;

    @NotNull
    private String name;

    @NotNull
    @Size(min = 3)
    private String identifier;

    private String description;

    @NotNull
    private Level level;

    private Integer experiencePoints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
    public Integer getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(Integer experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeveloperDTO developerDTO = (DeveloperDTO) o;

        if ( ! Objects.equals(id, developerDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DeveloperDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", identifier='" + identifier + "'" +
            ", description='" + description + "'" +
            ", level='" + level + "'" +
            ", experiencePoints='" + experiencePoints + "'" +
            '}';
    }
}
