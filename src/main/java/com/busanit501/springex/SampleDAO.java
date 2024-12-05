package com.busanit501.springex;

import org.springframework.stereotype.Repository;

@Repository
// 느스한 결합 예제, -> 인터페이스로 변경.
//public class SampleDAO {
public interface SampleDAO {
    // 뭔가 작업을 해요,
    // 수정 작업을 하면, SampleDAO 클래스를 이용하고 있는,
    // SampleService 도 같이 코드를 수정해줘야 해요.
    // 결론, 2번 수정 해야함.
    // SampleDAO-> 인터페이스 타입으로 변경하면,
    // 결론, 작업을 SampleDAO 인터페이스를 구현한 클래스만 수정하면됨.
    // 1번 수정 해야함.
    //
    // 예시, 기능도 수정함.
}
