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

    private long id;

    private Long entryId;

    private boolean active;

    private HostProfile profile;

    private String host;

    private String description;

}
