package com.busanit501.springex.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper2 {
    // 매퍼 인터페이스 이름과, xml 파일이름 동일.
    // 메서드 로 사용하는 이름과,  xml 파일에서의 id 이름 동일.
    // main/resources/mappers, 폴더 만들기.
    // TimeMapper2.xml 파일 생성할 예정.
    String getNow();
}
