package dev.scaraz.gateway.entities;

import dev.scaraz.common.domain.audit.AuditingEntity;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_api_route")
public class ApiRoute extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "ref_entry_id")
    private ApiEntry entry;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HttpMethod method;

    @Column(nullable = false)
    private String path;

    @Column
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String variables;

    @Column
    private String description;

    public String[] getVariables() {
        if (StringUtils.isBlank(variables)) return null;
        return variables.split(",");
    }

    public void setVariables(Iterable<String> variables) {
        if (variables == null) this.variables = null;
        else for (String v : variables) addVariable(v);
    }

    public void addVariable(String var) {
        if (StringUtils.isBlank(variables)) variables = var;
        else variables += "," + var;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ApiRoute)) return false;

        ApiRoute apiRoute = (ApiRoute) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getId(), apiRoute.getId())
                .append(isActive(), apiRoute.isActive())
                .append(getEntry(), apiRoute.getEntry())
                .append(getMethod(), apiRoute.getMethod())
                .append(getPath(), apiRoute.getPath())
                .append(getVariables(), apiRoute.getVariables())
                .append(getDescription(), apiRoute.getDescription())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getId())
                .append(getEntry())
                .append(isActive())
                .append(getMethod())
                .append(getPath())
                .append(getVariables())
                .append(getDescription())
                .toHashCode();
    }
}
