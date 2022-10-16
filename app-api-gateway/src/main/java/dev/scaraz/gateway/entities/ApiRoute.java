package dev.scaraz.gateway.entities;

import dev.scaraz.common.domain.audit.AuditingEntity;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
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
}
