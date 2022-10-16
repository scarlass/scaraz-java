package dev.scaraz.common.dto.response.api;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiEntryDTO implements Serializable {

    private long id;

    private String name;

    private String prefix;

    private String description;

    private List<String> tags;

    private List<ApiRouteDTO> routes;

}
