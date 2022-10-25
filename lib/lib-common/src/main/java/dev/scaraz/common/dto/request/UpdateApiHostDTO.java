package dev.scaraz.common.dto.request;

import dev.scaraz.common.utils.enums.HostProfile;
import dev.scaraz.common.validation.constraint.IsURL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApiHostDTO {

    private Boolean active;

    @IsURL
    private String host;

    private String description;

}
