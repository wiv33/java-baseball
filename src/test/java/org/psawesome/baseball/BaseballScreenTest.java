package org.psawesome.baseball;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * package: org.psawesome.baseball
 * author: PS
 * DATE: 2020-11-12 목요일 13:10
 */
public class BaseballScreenTest {

    BaseballScreen screen;
    @BeforeEach
    void setUp() {
        screen = new BaseballScreen();
    }

    @Test
    void testInitScreen() {
        assertNotNull(screen);
    }

    @Test
    @DisplayName("시작 테스트: 입력이 없는 상태에서 호출 시 에러 테스트")
    void testStartScreen() {
        assertThrows(NoSuchElementException.class, () -> screen.start());
        System.out.println();
        assertDoesNotThrow(() -> screen.start(new Scanner("231")));
    }

    @Test
    @DisplayName("종료 테스트: 정답을 모두 맞혔을 때 종료 테스트")
    @Disabled
    void testQuitScreen() {
    }

}
