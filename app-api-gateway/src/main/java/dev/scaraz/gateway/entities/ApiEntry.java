package dev.scaraz.gateway.entities;


import dev.scaraz.common.domain.audit.AuditingEntity;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_api_entry")
public class ApiEntry extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String prefix;

    @NotNull
    @Column
    private String description;

    @Column
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String tags;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    private Set<ApiRoute> routes = new HashSet<>();

    public String[] getTags() {
        if (StringUtils.isBlank(tags)) return null;
        return tags.split(",");
    }

    public void setTags(Iterable<String> tags) {
        if (tags == null) this.tags = null;
        else tags.forEach(this::addTag);
    }

    public void addTag(String tag) {
        if (StringUtils.isBlank(tag)) this.tags = tag;
        else tags += "," + tag;
    }

}
