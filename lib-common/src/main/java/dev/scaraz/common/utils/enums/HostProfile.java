package dev.scaraz.common.utils.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum HostProfile {
    ALL,
    STAGING,
    PRODUCTION
}
