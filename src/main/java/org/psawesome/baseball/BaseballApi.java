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
public enum  BaseballApi {
    ANSWER(req -> {
        final int RESULT_LENGTH = 3;
        Set<String> result = new HashSet<>();

        do {
            final int x = ThreadLocalRandom.current().nextInt(1, 10);
            result.add(String.valueOf(x));
        } while (result.size() < RESULT_LENGTH);

        return new BaseballResponse("ing", result.toString().replaceAll("[^\\d]", ""));
    })
    ;

    Function<BaseballRequest, BaseballResponse> userAct;

    BaseballApi(java.util.function.Function<BaseballRequest, BaseballResponse> userAct) {
        this.userAct = userAct;
    }

    public static BaseballResponse call(BaseballRequest request) {
        return BaseballApi.valueOf(request.getApi().name())
                .userAct.apply(request);
    }
}
