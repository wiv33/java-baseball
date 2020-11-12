package org.psawesome.baseball;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.psawesome.baseball.dto.BaseballRequest;
import org.psawesome.baseball.dto.BaseballResponse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * package: org.psawesome.baseball
 * author: PS
 * DATE: 2020-11-12 목요일 22:14
 */
public class BaseballApiTest {

    BaseballScreen screen;

    @BeforeEach
    void setUp() {

    }

    // tag::1. 정답 생성[]
    @Test
    @DisplayName("게임 시작 시 answer 생성 테스트")
    void testExistsAnswerAtStartBaseball() {
        screen = new BaseballScreen(new Scanner("765"));
        assertNotNull(screen);
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
                        BaseballRequest.builder().api(BaseballApi.ANSWER).build())
                        .getBody()))
                ))
                .limit(300);
    }
    // end::1. 정답 생성[]


    // tag::2. 결과 반환하기 []

    // tag::2-1. checkedOut[]
    @ParameterizedTest
    @DisplayName("3개를 맞춰 게임 종료 문구 테스트")
    @MethodSource("expectedOutTexts")
    void testDisplayInScreen(String answer, String inputText,
                             String expected, String status) {
        final BaseballResponse actual = BaseballApi.call(BaseballRequest.builder()
                .api(BaseballApi.HOW_MUCH_MATCH)
                .info(answer)
                .body(inputText)
                .build());
        assertEquals(status, actual.getState());
        assertEquals(expected, actual.getBody());
    }

    static Stream<Arguments> expectedOutTexts() {
        return Stream.of(
                Arguments.of("234", "234", "3개의 숫자를 모두 맞히셨습니다. 게임 종료", "end"),
                Arguments.of("523", "523", "3개의 숫자를 모두 맞히셨습니다. 게임 종료", "end"),
                Arguments.of("156", "156", "3개의 숫자를 모두 맞히셨습니다. 게임 종료", "end"),
                Arguments.of("981", "981", "3개의 숫자를 모두 맞히셨습니다. 게임 종료", "end"),
                Arguments.of("592", "591", "2 스트라이크", "ing"),
                Arguments.of("732", "156", "", "ing")
        );
    }
    // end::2-1. checkedOut[]

    // tag::2-2. makeStrike[]
    @ParameterizedTest
    @DisplayName("Strike 테스트")
    @MethodSource("expectedStrike")
    void testStrike(String answer, String inputText, String expectedStrike) {
        final String strike = BaseballApi.makeStrike(answer, inputText, 0, 0);
        Assertions.assertEquals(expectedStrike, strike);
    }

    static Stream<Arguments> expectedStrike() {
        return Stream.of(
                Arguments.of("732", "132", "2 스트라이크"),
                Arguments.of("732", "691", ""),
                Arguments.of("732", "437", "1 스트라이크"),
                Arguments.of("732", "731", "2 스트라이크")
        );
    }
    // end::2-2. makeStrike[]


    // tag::2-3. makeResult - 전체 테스트[]
    @ParameterizedTest
    @DisplayName("strike, ball, out, nothing 테스트")
    @MethodSource("expectedAllTexts")
    void testDisplayInScreen(String answer, String inputText,
                             String expectedStrike,
                             String expectedBall,
                             String expected) {
        final BaseballResponse actual = BaseballApi.call(BaseballRequest.builder()
                .api(BaseballApi.HOW_MUCH_MATCH)
                .info(answer)
                .body(inputText)
                .build());
        System.out.println(inputText);
        System.out.println(actual.getBody());
        Assertions.assertEquals(expected, actual.getBody());
    }

    static Stream<Arguments> expectedAllTexts() {
        return Stream.of(
                Arguments.of("732", "132", "2 스트라이크", "", "2 스트라이크"),
                Arguments.of("732", "691", "", "", ""),
                Arguments.of("732", "437", "1 스트라이크", "1볼", "1 스트라이크 1볼"),
                Arguments.of("732", "731", "2 스트라이크", "", "2 스트라이크"),
                Arguments.of("732", "732", "", "", "3개의 숫자를 모두 맞히셨습니다. 게임 종료")
        );
    }
    // end::2-3. makeResult - 전체 테스트[]
    // end::2. 결과 반환하기 []

}
