package com.example.test.database;

import com.example.test.comm.YamlPropertySourceFactory;
import com.example.test.database.model.DBConnectionInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource(value = "classpath:database.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "database")
public class DBInfoProps {
    @Getter
    @Setter
    private List<DBConnectionInfo> info;
}
