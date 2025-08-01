package com.emorinken.bookstore.config;

import java.util.Optional;
import java.util.concurrent.Executor;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.config.liquibase.AsyncSpringLiquibase;

@Configuration
public class LiquibaseConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(LiquibaseConfiguration.class);

    private final Environment env;

    public LiquibaseConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public SpringLiquibase liquibase(
        @Qualifier("taskExecutor") Executor executor,
        LiquibaseProperties liquibaseProperties,
        R2dbcProperties dataSourceProperties
    ) {
        SpringLiquibase liquibase = new AsyncSpringLiquibase(executor, env);
        liquibase.setDataSource(createLiquibaseDataSource(liquibaseProperties, dataSourceProperties));
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        if (!CollectionUtils.isEmpty(liquibaseProperties.getContexts())) {
            liquibase.setContexts(StringUtils.collectionToCommaDelimitedString(liquibaseProperties.getContexts()));
        }
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        if (!CollectionUtils.isEmpty(liquibaseProperties.getLabelFilter())) {
            liquibase.setLabelFilter(StringUtils.collectionToCommaDelimitedString(liquibaseProperties.getLabelFilter()));
        }
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
        if (env.matchesProfiles(JHipsterConstants.SPRING_PROFILE_NO_LIQUIBASE)) {
            liquibase.setShouldRun(false);
        } else {
            liquibase.setShouldRun(liquibaseProperties.isEnabled());
            LOG.debug("Configuring Liquibase");
        }
        return liquibase;
    }

    private static DataSource createLiquibaseDataSource(LiquibaseProperties liquibaseProperties, R2dbcProperties dataSourceProperties) {
        String user = Optional.ofNullable(liquibaseProperties.getUser()).orElse(dataSourceProperties.getUsername());
        String password = Optional.ofNullable(liquibaseProperties.getPassword()).orElse(dataSourceProperties.getPassword());

        return DataSourceBuilder.create().url(liquibaseProperties.getUrl()).username(user).password(password).build();
    }
}
