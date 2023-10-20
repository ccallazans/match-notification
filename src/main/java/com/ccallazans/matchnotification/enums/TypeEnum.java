package com.ccallazans.matchnotification.enums;

import lombok.Getter;


@Getter
public enum TypeEnum {
    MATCH("MATCH"),
    GOAL("GOAL");

    private final String type;
    TypeEnum(String type) {
        this.type = type;
    }
}
