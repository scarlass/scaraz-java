package dev.scaraz.gateway.entities;

import dev.scaraz.common.domain.audit.AuditingEntity;
import dev.scaraz.common.utils.enums.HostProfile;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_api_host")
public class ApiHost extends AuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ApiEntry entry;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private HostProfile profile;

    @Column
    private String host;

    @Column
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ApiHost)) return false;

        ApiHost apiHost = (ApiHost) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getId(), apiHost.getId())
                .append(getEntry(), apiHost.getEntry())
                .append(getProfile(), apiHost.getProfile())
                .append(getHost(), apiHost.getHost())
                .append(getDescription(), apiHost.getDescription())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getId())
                .append(getEntry())
                .append(getProfile())
                .append(getHost())
                .append(getDescription())
                .toHashCode();
    }
}


