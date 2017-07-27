package com.capgemini.googlecloud;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;
import javax.validation.ParameterNameProvider;
import javax.validation.Validation;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.ValidationConfig;
import org.glassfish.jersey.server.validation.internal.InjectingConstraintValidatorFactory;

/**
 * This class acts like an Application level class where we can configure validations
 *
 */
@ApplicationPath("webapi")
public class ImportBillsApplication extends ResourceConfig {
    public ImportBillsApplication() {
    	System.out.println("In ImportBillsApplication v0.1");
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        properties.put(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
        addProperties(properties);
        packages("com.capgemini.googlecloud.web").register(MultiPartFeature.class);
    }



    /**
     * Custom configuration of validation. This configuration defines custom:
     * <ul>
     *     <li>ConstraintValidationFactory - so that validators are able to inject Jersey providers/resources.</li>
     *     <li>ParameterNameProvider - if method input parameters are invalid, this class returns actual parameter names
     *     instead of the default ones ({@code arg0, arg1, ..})</li>
     * </ul>
     */
    public static class ValidationConfigurationContextResolver implements ContextResolver<ValidationConfig> {

        @Context
        private ResourceContext resourceContext;

        @Override
        public ValidationConfig getContext(final Class<?> type) {
            return new ValidationConfig()
                    .constraintValidatorFactory(resourceContext.getResource(InjectingConstraintValidatorFactory.class))
                    .parameterNameProvider(new CustomParameterNameProvider());
        }

        /**
         * See ContactCardTest#testAddInvalidContact.
         */
        private class CustomParameterNameProvider implements ParameterNameProvider {

            private final ParameterNameProvider nameProvider;

            public CustomParameterNameProvider() {
                nameProvider = Validation.byDefaultProvider().configure().getDefaultParameterNameProvider();
            }

            @Override
            public List<String> getParameterNames(final Constructor<?> constructor) {
                return nameProvider.getParameterNames(constructor);
            }

            @Override
            public List<String> getParameterNames(final Method method) {
                return nameProvider.getParameterNames(method);
            }
        }
    }
}
