package org.psawesome;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ps [https://github.com/wiv33/java-baseball]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 11. 10. Tuesday
 */
class StringTest {

  // tag::요구사항 1[]
  List<String> numList;

  @Test
  @DisplayName("요구사항1: `,` split 기능 테스트 - 분리")
  void testInit() {
    String numbers = "1,2,3";
    numList = getNumList(numbers);
    assertArrayEquals(new String[]{"1", "2", "3"}, numList.toArray());
    assertAll(
            () -> assertTrue(numList.contains("1")),
            () -> assertTrue(numList.contains("2")),
            () -> assertTrue(numList.contains("3"))
    );
    assertIterableEquals(Arrays.asList("1", "2", "3"), numList);
  }

  private List<String> getNumList(String numbers) {
    return Arrays.asList(numbers.split(","));
  }

  @Test
  @DisplayName("요구사항1: 원소 한 개를 `,`로 split 했을 때 1만 포함하는 배열이 반환되는지")
  void testSplitOne() {
    numList = getNumList("1");
    assertArrayEquals(new String[]{"1"}, numList.toArray());
    assertTrue(numList.contains("1"));
  }
  // end::요구사항 1[]


  // tag::요구사항 2[]

  @Test
  @DisplayName("요구사항2: '(1,2)'일 경우 substring()을 활용하여 1,2를 반환")
  void testUsingSubstring() {
    assertEquals("1,2", this.extractSub("(1,2)"));
  }

  private String extractSub(String str) {
    return str.substring(1, str.length() - 1);
  }
  // end::요구사항 2[]


  // tag::요구사항 3[]


  private String extractByAt(String str, int idx) {
    return String.valueOf(str.charAt(idx));
  }

  @Test
  @DisplayName("요구사항3: chatAt을 활용해 특정 위치의 문자를 추출 - 성공 케이스")
  void testUsingCharAt() {
    String str = "abc";
    assertEquals("a", this.extractByAt(str, 0));
  }

  @Test
  @DisplayName("요구사항3: index를 벗어났을 때의 예외 테스트")
  void testSIOOBE() {
    assertAll(
            () -> assertTrue(
                    assertThrows(StringIndexOutOfBoundsException.class, () -> this.extractByAt("abc", 3))
                            .getMessage().contains("out of range: 3")),
            () -> assertTrue(assertThrows(Exception.class, () -> this.extractByAt("", -1))
                    .getMessage().contains("index out of range: -1")));

    /*
    since 3.7
    굉장히 다양한 것들을 제공하지만, junit5 기능으로도 가능하지 않나?
    */
    assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> this.extractByAt("ss", 3))
            .withMessageMatching("String index out of range: \\d+");
  }

  // end::요구사항 3[]


}

