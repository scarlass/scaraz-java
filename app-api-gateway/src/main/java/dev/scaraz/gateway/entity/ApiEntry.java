package dev.scaraz.gateway.entity;


import dev.scaraz.common.domain.AuditingEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "api_entry")
public class ApiEntry extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String prefix;

    @Column
    private String description;

    @Column
    private String tags;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    private Set<ApiRoute> routes = new HashSet<>();

}
