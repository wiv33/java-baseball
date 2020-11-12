package org.psawesome.baseball.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * package: org.psawesome.baseball.dto
 * author: PS
 * DATE: 2020-11-12 목요일 22:44
 */
@Data
@RequiredArgsConstructor
public class BaseballResponse {
    private final String state;
    private final String body;
}
