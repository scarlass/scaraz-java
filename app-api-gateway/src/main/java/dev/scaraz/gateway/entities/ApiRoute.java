package dev.scaraz.gateway.entities;

import dev.scaraz.common.domain.audit.AuditingEntity;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.http.HttpMethod;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    @ToString.Exclude
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

    public Set<String> getVariables() {
        if (StringUtils.isBlank(variables)) return Set.of();
        return Arrays.stream(variables.split(","))
                .collect(Collectors.toSet());
    }

    public void setVariables(Iterable<String> variables) {
        if (variables == null) this.variables = null;
        else {
            Set<String> set = new HashSet<>();
            if (!StringUtils.isBlank(this.variables)) set.addAll(Set.of(this.variables.split(",")));
            variables.forEach(set::add);
            this.variables = String.join(",", set);
        }
    }

    public void addVariable(String var) {
        if (StringUtils.isBlank(variables)) {
            variables = var;
            return;
        }

        Set<String> set = new HashSet<>(Set.of(var.split(",")));
        set.add(var);
        variables = String.join(",", set);
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
                .append(isActive())
                .append(getMethod())
                .append(getPath())
                .append(getVariables())
                .append(getDescription())
                .toHashCode();
    }
}
