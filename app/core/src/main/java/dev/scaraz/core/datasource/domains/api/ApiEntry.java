package dev.scaraz.core.datasource.domains.api;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import dev.scaraz.common.domain.audit.AuditingEntity;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "t_api_entry")
@TypeDefs({
        @TypeDef(name = "list-array", typeClass = ListArrayType.class)
})
public class ApiEntry extends AuditingEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String prefix;

    @Column
    private String description;

    @Builder.Default
    @Type(type= "list-array")
    @Column(columnDefinition = "varchar(50)[]")
    private List<String> tags = new ArrayList<>();

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "entry", fetch = FetchType.EAGER)
    private Set<ApiHost> hosts = new HashSet<>();

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
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getId())
                .append(getName())
                .append(getPrefix())
                .toHashCode();
    }

}
