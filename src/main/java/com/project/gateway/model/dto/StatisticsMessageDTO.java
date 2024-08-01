package com.project.gateway.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatisticsMessageDTO {
    private String serviceName;
    private String requestId;
    private String time;
    private String clientId;
}
