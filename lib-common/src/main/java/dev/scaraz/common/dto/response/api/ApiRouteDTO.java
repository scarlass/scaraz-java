package dev.scaraz.common.dto.response.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiRouteDTO {

    private long id;

    private Long entryId;

    private boolean active;

    private HttpMethod method;

    private String path;

    private List<String> variables;

    private String description;

}
