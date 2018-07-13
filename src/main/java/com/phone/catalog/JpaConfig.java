package com.phone.catalog;

import com.phone.catalog.core.Constants;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = Constants.CORE_PACKAGE)
@EntityScan(basePackages = Constants.CORE_PACKAGE)
public class JpaConfig {

}
