package dev.scaraz.core.datasource.domains;

import dev.scaraz.common.domain.audit.AuditingEntity;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "t_api_host")
public class ApiHost extends AuditingEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column
    @Builder.Default
    private boolean active = true;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "ref_entry_id")
    private ApiEntry entry;
    @Column
    private String host;
    @Column
    private String description;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ApiHost)) return false;

        ApiHost that = (ApiHost) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(isActive(), that.isActive())
                .append(getId(), that.getId())
                .append(getHost(), that.getHost())
                .append(getDescription(), that.getDescription())
                .isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getId())
                .append(isActive())
                .append(getHost())
                .append(getDescription())
                .toHashCode();
    }
}
