package org.psawesome.baseball.dto;

import lombok.Builder;
import lombok.Getter;
import org.psawesome.baseball.BaseballApi;

/**
 * package: org.psawesome.baseball.dto
 * author: PS
 * DATE: 2020-11-12 목요일 22:44
 */
@Getter
@Builder
public class BaseballRequest {
    private final BaseballApi api;
    private String info;
    private String body;
}
