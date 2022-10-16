package dev.scaraz.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApiRouteDTO {

    private Boolean active;

    private HttpMethod method;

    private String path;

    private String description;

}
