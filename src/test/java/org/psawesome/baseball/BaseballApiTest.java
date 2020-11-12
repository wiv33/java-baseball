package org.psawesome.baseball;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.psawesome.baseball.dto.BaseballRequest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * package: org.psawesome.baseball
 * author: PS
 * DATE: 2020-11-12 목요일 22:14
 */
public class BaseballApiTest {

    BaseballScreen screen;

    @BeforeEach
    void setUp() {
        screen = new BaseballScreen(new Scanner("765"));
    }

    // tag::1. 정답 생성[]
    @Test
    @DisplayName("게임 시작 시 answer 생성 테스트")
    void testExistsAnswerAtStartBaseball() {
        Assertions.assertNotNull(screen);
        assertEquals(3, screen.getAnswer().length());
    }

    @ParameterizedTest(name = "[{index}] {arguments}")
    @DisplayName("answer 중복 없음 테스트")
    @MethodSource("getAnswers")
    void testShouldBeExistsAnswerInScreen(BaseballScreen screen) {
        assertEquals(3, screen.getAnswer().length());
        assertEquals(3, new HashSet<>(
                Arrays.asList(screen.getAnswer().split(""))).size());
    }

    private static Stream<Arguments> getAnswers() {
        return Stream.generate(() ->
                Arguments.of(new BaseballScreen(new Scanner(BaseballApi.call(
                        BaseballRequest.builder()
                                .api(BaseballApi.ANSWER)
                                .build())
                        .getBody()))))
                .limit(300);
    }
    // end::1. 정답 생성[]

}
