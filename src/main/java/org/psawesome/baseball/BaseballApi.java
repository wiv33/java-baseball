package org.psawesome.baseball;

import org.psawesome.baseball.dto.BaseballRequest;
import org.psawesome.baseball.dto.BaseballResponse;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

/**
 * package: org.psawesome.baseball
 * author: PS
 * DATE: 2020-11-12 목요일 22:45
 */
public enum BaseballApi {
    ANSWER(req -> {
        final int RESULT_LENGTH = 3;
        Set<String> result = new HashSet<>();

        do {
            final int x = ThreadLocalRandom.current().nextInt(1, 10);
            result.add(String.valueOf(x));
        } while (result.size() < RESULT_LENGTH);

        return new BaseballResponse("ing", result.toString().replaceAll("[^\\d]", ""));
    }), HOW_MUCH_MATCH(req -> {
        final String answer = req.getInfo();
        final String inputNumbers = req.getBody();

        if (checkedOut(answer, inputNumbers)) {
            return new BaseballResponse("end", "3개의 숫자를 모두 맞히셨습니다. 게임 종료");
        }
        final String result = makeStrike(answer, inputNumbers, 0, 0);

        return new BaseballResponse("ing", result);
    });

    Function<BaseballRequest, BaseballResponse> userAct;

    BaseballApi(Function<BaseballRequest, BaseballResponse> userAct) {
        this.userAct = userAct;
    }

    public static BaseballResponse call(BaseballRequest request) {
        return BaseballApi.valueOf(request.getApi().name())
                .userAct.apply(request);
    }

    // tag::2-1. checkedOut[]
    private static boolean checkedOut(String answer, String inputNumbers) {
        return answer.equals(inputNumbers);
    }
    // end::2-1. checkedOut[]

    // tag::2-2. makeStrike[]
    public static String makeStrike(String answer, String inputNumbers, int idx, int strike) {
        strike = increaseIfEqualStrike(answer, inputNumbers, idx, strike);
        if (idx > answer.length() - 2) {
            return concatText(strike, " 스트라이크");
        }
        return makeStrike(answer, inputNumbers, idx + 1, strike);
    }

    private static int increaseIfEqualStrike(String answer, String inputNumbers, int idx, int strike) {
        if (answer.charAt(idx) == inputNumbers.charAt(idx)) {
            return strike + 1;
        }
        return strike;
    }

    public static String concatText(int count, String text) {
//        return count == 0 ? "" : String.format("%d%s", count, text);
        if (count == 0) {
            return "";
        }
        return String.format("%d%s", count, text);
    }

    // end::2-2. makeStrike[]


}
