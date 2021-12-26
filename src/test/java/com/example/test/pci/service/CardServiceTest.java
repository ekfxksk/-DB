package com.example.test.pci.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CardServiceTest {
    @Autowired
    private CardService cardService;

    @Test
    public void test() {

        cardService.cardValidCheck();
    }
}