package com.rcibanque.rcidirect.services.core.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import com.rcibanque.rcidirect.services.core.config.domain.Market;
import com.rcibanque.rcidirect.services.core.test.ConditionalTestRunner;

@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(ConditionalTestRunner.class)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface IgnoreIf {

	Market[] value();

}
