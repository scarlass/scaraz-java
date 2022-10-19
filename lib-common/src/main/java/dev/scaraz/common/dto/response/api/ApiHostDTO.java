package dev.scaraz.common.dto.response.api;

import dev.scaraz.common.utils.enums.HostProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiHostDTO {

    private String id;

    private String entryId;

    private boolean active;

    private String host;

    private String description;

}
