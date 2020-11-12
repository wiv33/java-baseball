package org.psawesome.baseball;

import lombok.Data;
import org.psawesome.baseball.dto.BaseballRequest;

import java.util.Scanner;

/**
 * package: org.psawesome.baseball
 * author: PS
 * DATE: 2020-11-12 목요일 22:18
 */
@Data
public class BaseballScreen {
    private Scanner scanner;
    private final String answer;

    public BaseballScreen() {
        scanner = new Scanner(System.in);
        this.answer = this.getAnswerByApi();
        this.start();
    }

    public String getAnswerByApi() {
        return BaseballApi.call(
                BaseballRequest.builder()
                        .api(BaseballApi.ANSWER)
                        .build())
                .getBody();
    }

    public BaseballScreen(Scanner scanner) {
        this.scanner = scanner;
        this.answer = this.getAnswerByApi();
        this.start();
    }

    public void start() {
        this.ready();
        System.out.println("" + this.scanner.next());
    }

    public void start(Scanner sc) {
        scanner = sc;
        this.ready();
        System.out.println(this.scanner.next());
    }

    public void ready() {
        System.out.print("숫자를 입력해주세요 : ");
    }
}
