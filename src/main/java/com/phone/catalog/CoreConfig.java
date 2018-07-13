package com.phone.catalog;

import com.phone.catalog.core.Constants;
import com.phone.catalog.profile.Profiles;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:core.properties"})
@ComponentScan(basePackages = {Constants.CORE_PACKAGE})
@Import({JpaConfig.class})
public class CoreConfig {

    @Configuration
    @Profile(Profiles.LOCAL)
    @PropertySource("classpath:local.properties")
    static class LocalConfig {
    }

}
