package com.example.test.pci.service;

import com.example.test.database.DbContextHolder;
import com.example.test.pci.code.DBList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CardService {

    private final SqlSessionTemplate sqlSessionTemplate;

    public void cardValidCheck() {


        for ( DBList db : DBList.values()) {
            DbContextHolder.setDBUser(db.getDbUserName());

            getUserList().forEach(user -> {
                log.info("db {} / user {} / {}", db.getDbUserName(), user, getDataCount(user, "data"));
            });
        }
    }

    private List<String> getUserList() {
        return sqlSessionTemplate.selectList("database.getDBUserList");
    }

    private String getDataCount(String schema, String std) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("schema", schema);
            param.put("std", std);
            return sqlSessionTemplate.selectOne("card.getCardData", param);
        } catch(Exception e) {
            return "-1";
        }

    }

}
