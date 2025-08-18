package com.hive.help;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

    @Component
    public class SqlConfig implements ApplicationRunner {

        private final DataSource dataSource;

        public SqlConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
            try (Connection connection = dataSource.getConnection()) {
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("data.sql"));
            }
        }
    }

