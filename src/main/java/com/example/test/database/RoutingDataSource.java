package com.example.test.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        //return DbContextHolder.getDBUser() == null ? "TEST1" : DbContextHolder.getDBUser();
        return DbContextHolder.getDBUser();
    }
}
