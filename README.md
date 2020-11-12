# 숫자 야구 게임
## 진행 방법
* 숫자 야구 게임 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 과제를 제출한다.

## 과제 제출 과정
* [과제 제출 방법](https://github.com/next-step/nextstep-docs/tree/master/precourse)

---

Baseball
=

[게임방식](https://namu.wiki/w/%EC%88%AB%EC%9E%90%EC%95%BC%EA%B5%AC)

게임 룰
- 0-9 서로 다른 숫자
- 숫자는 맞지만 위치 가틀렸을 때 '볼'
- 숫자, 위치가 맞을 경우 '스트라이크'
- 숫자와 위치가 맞지 않으면 '아웃'


    3 스트라이크일 경우 정답

## 기능 요구사항

* 숫자 
    - [1-9]{3}

* 출력 내용
    - {count} 스트라이크
    - {count} 볼
    - {count} nothing

* 컴퓨터가 가지고 있는 3개의 숫자를 사람이 입력하는 방식

* 숫자 3개를 맞히면 게임 종료

* 게임 종류 후 재시작하거나 프로그램을 종료할 수 있다.



필요한 기능
-

BaseballScreen.java

- [x] 게임 start, quit
    - [x] ing
    - [x] end

- [x] 정답인지 제출하는 행위 submit

- [x] submit 결과 출력
    + [x] 현재 결과
    + [x] 이후의 상태로 전환
        * [x] ing
        * [x] end


BaseballApi.java
    
- [x] 게임 시작 시 1-9까지 3개의 숫자 생성

- [x] 결과 반환하기: makeResult
    + [x] checkedOut
    + [x] makeStrike
    + [x] makeBall


