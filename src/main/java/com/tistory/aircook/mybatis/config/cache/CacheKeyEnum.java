package com.tistory.aircook.mybatis.config.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CacheKeyEnum {

    PEOPLE_ALL("01", "사람캐시 전체키"),
    MEMO_ALL("02", "메모캐시 전체키"),
    EMPTY(StringUtils.EMPTY, "비어있는 값");

    private final String key;

    private final String desc;

    public static CacheKeyEnum getEnum(String key) {
        return Arrays.stream(CacheKeyEnum.values())
                .filter(c -> c.getKey().equals(key))
                .findAny()
                .orElse(EMPTY);
    }


}