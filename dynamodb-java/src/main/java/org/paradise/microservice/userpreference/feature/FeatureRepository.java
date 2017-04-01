package org.paradise.microservice.userpreference.feature;

import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by terrence on 1/4/17.
 */
public class FeatureRepository {

    private static final String FEATURE_PREFIX = "feature.";

    private final Environment environment;

    public FeatureRepository(Environment environment) {
        this.environment = environment;
    }

    public Set<String> featureKeys() {
        Map<String, Object> map = new HashMap<>();

        for (Iterator it = ((AbstractEnvironment) environment).getPropertySources().iterator(); it.hasNext();) {
            PropertySource propertySource = (PropertySource) it.next();

            if (propertySource instanceof MapPropertySource) {
                map.putAll(((MapPropertySource) propertySource).getSource());
            }
        }

        return map.keySet().stream()
                .filter(k -> k.startsWith(FEATURE_PREFIX))
                .collect(Collectors.toSet());
    }

    public Boolean isOn(String key) {

        return allFeatures().get(key);
    }

    public Map<String, Boolean> allFeatures() {

        return featureKeys().stream().collect(Collectors.toMap(k -> k, k -> Boolean.parseBoolean(environment.getProperty(k))));
    }

}