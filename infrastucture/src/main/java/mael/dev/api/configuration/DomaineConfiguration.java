package mael.dev.api.configuration;

import mael.dev.army.Army;
import mael.dev.ddd.DomaineService;
import mael.dev.ddd.Stub;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.springframework.context.annotation.FilterType.ANNOTATION;


@Configuration
@ComponentScan(
        basePackageClasses = {Army.class},
        includeFilters = {@ComponentScan.Filter(type = ANNOTATION, classes = {DomaineService.class, Stub.class})}
)
public class DomaineConfiguration {
}
