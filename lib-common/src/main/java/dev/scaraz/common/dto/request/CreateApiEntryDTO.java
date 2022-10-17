package dev.scaraz.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateApiEntryDTO {

    @NotNull
    private String name;

    private String prefix;

    private String description;

    private List<String> tags;

    @Builder.Default
    private List<CreateApiHostDTO> hosts = new ArrayList<>();

    @Builder.Default
    private List<CreateApiRouteDTO> routes = new ArrayList<>();


}
