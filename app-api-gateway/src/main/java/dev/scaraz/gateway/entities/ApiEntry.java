package dev.scaraz.gateway.entities;


import dev.scaraz.common.domain.audit.AuditingEntity;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString
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
    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "entry")
    private Set<ApiHost> hosts = new HashSet<>();

    @Builder.Default
    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "entry")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ApiEntry)) return false;

        ApiEntry apiEntry = (ApiEntry) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getId(), apiEntry.getId())
                .append(getName(), apiEntry.getName())
                .append(getPrefix(), apiEntry.getPrefix())
                .append(getDescription(), apiEntry.getDescription())
                .append(getTags(), apiEntry.getTags())
                .append(getHosts(), apiEntry.getHosts())
                .append(getRoutes(), apiEntry.getRoutes())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getId())
                .append(getName())
                .append(getPrefix())
                .append(getDescription())
                .append(getTags())
                .append(getHosts())
                .append(getRoutes())
                .toHashCode();
    }
}
