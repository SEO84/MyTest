package com.busanit501.springex.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
    //방법1, 맵퍼 인터페이스에서, 애너테이션이용해서,
    // 디비 호출하기.
    @Select("select now()")
    String getNow();

    // 방법2, SQL 문장을 따로 파일로 분리하기. xml 파일로
}
