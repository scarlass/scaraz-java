package dev.scaraz.common.dto.request;

import dev.scaraz.common.utils.enums.HostProfile;
import dev.scaraz.common.validation.constraint.IsURL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateApiHostDTO {

    @NotNull
    private HostProfile profile;

    @IsURL
    @NotNull
    private String host;

    private String description;

}
