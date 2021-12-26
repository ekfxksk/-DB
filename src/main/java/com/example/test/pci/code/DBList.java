package com.example.test.pci.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DBList {
    SYSTEM("system");

    private final String dbUserName;
}
