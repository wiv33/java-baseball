package org.psawesome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Set 테스트")
public class SetTest {
  private HashSet<Integer> numbers;

  @BeforeEach
  void setUp() {
    numbers = new HashSet<>(Arrays.asList(1, 1, 2, 3));
  }

  // tag::요구사항1[]

  @Test
  @DisplayName("요구사항1: Set의 size 확인")
  void testShouldBeSizeThree() {
    assertEquals(3, numbers.size());
  }
  // end::요구사항1[]


  // tag::요구사항2[]
  @Test
  @DisplayName("요구사항2: set에 존재하는 값 확인")
  void testContainsNumber() {
    assertAll(
            () -> assertTrue(numbers.contains(1)),
            () -> assertTrue(numbers.contains(2)),
            () -> assertTrue(numbers.contains(3)));
  }

  // tag::methodSource[]

  @ParameterizedTest(name = "exists [{0}] in numbers:Set")
  @DisplayName("요구사항2: Set에 존재하는 값 확인 - 중복제거")
  @ValueSource(ints = {1, 2, 3})
  void testShouldBeAllContainInNumbersByParameterizedTest(int num) {
    assertTrue(numbers.contains(num));
  }

  static Stream<Integer> getExpectedNumbers() {
    return Stream.iterate(1, i -> i + 1).limit(3);
  }
  // end::methodSource[]

  @ParameterizedTest
  @DisplayName("요구사항2: Set에 존재하는 값 확인 - 중복제거: method")
  @MethodSource("getExpectedNumbers")
  void testShouldBeAllContainsInNumbersByParameterStreamSource(int num) {
    assertTrue(numbers.contains(num));
  }

  // tag::ArgumentsSource:Provider[]
  @ParameterizedTest(name = "[{index}]: contains {0}, [{1}] in numbers:Set")
  @DisplayName("요구사항2: 중복제거")
  @ArgumentsSource(ExpectedInputNum.class)
  void testArgsInStream(boolean expected, int inputNum) {
    assertEquals(expected, numbers.contains(inputNum));
  }

  static class ExpectedInputNum implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
              Arguments.of(true, "1"),
              Arguments.of(false, "4"),
              Arguments.of(false, "333"),
              Arguments.of(true, "2"),
              Arguments.of(false, "777"),
              Arguments.of(true, "3"),
              Arguments.of(false, "12")
      );
    }
  }
  // end::ArgumentsSource:Provider[]

  // end::요구사항2[]


  // tag::요구사항3[]

  @ParameterizedTest(name = "[{index}] {arguments}")
  @DisplayName("요구사항3: Comma Separator Value를 활용한 중복제거 with type casting")
  @CsvSource(value = {"true:1", "true:2", "true:3", "false:4", "false:777"}, delimiter = ':')
  void testShouldBeAvailableCheckContainOrNot(boolean expected, int inputNum) {
    assertEquals(expected, numbers.contains(inputNum));
  }

  @ParameterizedTest(name = "{index}: {0}, contains [{1}] in numbers")
  @DisplayName("요구사항3: read csv in file")
  @CsvFileSource(resources = "/txt.csv", delimiter = ':', numLinesToSkip = 1)
  void testOtherParameterizedTests(boolean expected, int inputNum) {
    assertEquals(expected, numbers.contains(inputNum));
  }

  // end::요구사항3[]

}
