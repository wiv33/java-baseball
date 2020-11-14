package org.psawesome.baseball;

import lombok.Data;
import org.psawesome.baseball.dto.BaseballRequest;
import org.psawesome.baseball.dto.BaseballResponse;

import java.util.Scanner;

/**
 * package: org.psawesome.baseball
 * author: PS
 * DATE: 2020-11-12 목요일 22:18
 */
@Data
public class BaseballScreen {
    private Scanner scanner;
    private String answer;

    public BaseballScreen() {
        scanner = new Scanner(System.in);
        this.answer = this.getAnswerByApi();
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

    public String start() {
        this.ready();
        final String inputNumbers = this.scanner.next();
        System.out.println("" + inputNumbers);
        return inputNumbers;
    }

    public void resetAnswer() {
        this.answer = getAnswerByApi();
    }

    public BaseballResponse submit(String inputNumbers) {
        final BaseballResponse response = BaseballApi.call(BaseballRequest.builder()
                .api(BaseballApi.HOW_MUCH_MATCH)
                .info(this.answer)
                .body(inputNumbers)
                .build());
        return transferQuit(response);
    }

    private BaseballResponse transferQuit(BaseballResponse response) {
        if (!response.getState().equals("end")) {
            return response;
        }
        System.out.print("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
        if (scanner.next().equals("2")) {
            return new BaseballResponse("quit", "게임종료");
        }
        this.resetAnswer();
        return new BaseballResponse("ing", "게임 시작");
    }

    public void start(Scanner sc) {
        scanner = sc;
        this.ready();
        System.out.println(this.scanner.next());
    }

    public void ready() {
        System.out.print("숫자를 입력해주세요 : ");
    }

    public static void main(String[] args) {
        final BaseballScreen baseballScreen = new BaseballScreen();
        BaseballResponse response;
        do {
//            System.out.println(baseballScreen.answer);
            final String inputNumbers = baseballScreen.start();
            response = baseballScreen.submit(inputNumbers);
            System.out.println(response.getBody());
        } while (!response.getState().equals("quit"));
    }
}
