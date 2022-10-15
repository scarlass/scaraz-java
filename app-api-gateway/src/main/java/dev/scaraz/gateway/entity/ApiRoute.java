package dev.scaraz.gateway.entity;

import lombok.*;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "api_route")
public class ApiRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "ref_entry_id")
    private ApiEntry entry;

    @Column
    private String path;

    @Column
    private String variables;

    @Column
    @Enumerated(EnumType.STRING)
    private HttpMethod method;


}
