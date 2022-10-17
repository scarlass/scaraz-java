package dev.scaraz.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateApiRouteDTO {

    @NotNull
    private HttpMethod method;

    @NotNull
    private String path;

    private String description;

}
