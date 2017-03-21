package org.paradise.microservice.userpreference.service.metadata;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapping;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.paradise.microservice.userpreference.service.annotations.Label;
import org.paradise.microservice.userpreference.service.annotations.Path;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Provides a list of utility methods for dealing with headers rows is a file.
 */
public interface HeaderMetadata {

    List<Class> MANDATORY_ANNOTATIONS = new ArrayList<>(Arrays.asList(NotNull.class, NotBlank.class, NotEmpty.class));

    /**
     * Retrieves a map of all field names (as the value) from the target class provided and the alias they are known by (as the key).
     * The alias is defined using the {@link Label} annotation. If not alias is defined the field name is used as the key.
     *
     * @param targetClass The class for which the field names and aliases are being retrieved.
     * @return A Map containing the alias (key) and the bean field name (value)
     */
    static Map<String, String> getFieldNamesAndAliases(Class targetClass) {
        if (targetClass == null) {
            return Collections.emptyMap();
        }
        Map<String, String> fieldsAndAliases = new HashMap<>();
        Field[] fields = targetClass.getDeclaredFields();
        Arrays.stream(fields)
                .forEach(field -> {
                    if (!field.isSynthetic()) {
                        String alias = field.getDeclaredAnnotation(Label.class) != null
                                ? field.getDeclaredAnnotation(Label.class).value() : field.getName();
                        fieldsAndAliases.put(alias, field.getName());
                    }
                });

        return fieldsAndAliases;
    }
    /**
     * Retrieves a map of all destination field paths (as the key) and the associated aliases they are known by (as the value)
     * The destination is defined using the {@link Path} or {@link Mapping} annotation. If both present then both will be added. If
     * field has neither value it is not added to the Map.
     * The alias is defined using the {@link Label} annotation. If not alias is defined the field name is used as the key.
     *
     * @param targetClass The class for which the destination fields paths and aliases are being retrieved.
     * @param fieldPathPrefix A String prefix that shoudld be applied to all field paths.
     * @return A Map containing the destination field path (key) and the source alias (value)
     */
    static Map<String, String> getFieldPathAliases(Class targetClass, String fieldPathPrefix) {
        if (targetClass == null) {
            return Collections.emptyMap();
        }
        Map<String, String> fieldPathAliases = new HashMap<>();
        Field[] fields = targetClass.getDeclaredFields();
        Arrays.stream(fields)
                .forEach(field -> {
                    if (!field.isSynthetic()) {
                        List<String> fieldPaths = new ArrayList<>();

                        if (field.getDeclaredAnnotation(Path.class) != null) {
                            fieldPaths.add(field.getDeclaredAnnotation(Path.class).value());
                        }

                        if (field.getDeclaredAnnotation(Mapping.class) != null) {
                            fieldPaths.add(field.getDeclaredAnnotation(Mapping.class).value());
                        }

                        if (CollectionUtils.isNotEmpty(fieldPaths)) {
                            fieldPaths.forEach(fieldPath -> {
                                if (StringUtils.isNotEmpty(fieldPathPrefix)) {
                                    fieldPath = fieldPathPrefix + "." + fieldPath;
                                }

                                String alias = field.getDeclaredAnnotation(Label.class) != null
                                        ? field.getDeclaredAnnotation(Label.class).value() : field.getName();

                                fieldPathAliases.put(fieldPath, alias);
                            });
                        }
                    }
                });

        return fieldPathAliases;
    }

    /**
     * Associates the index of the header and the name of the associated bean field name from the map provided.
     * The order of the headerNames array is maintained in the resulting map.
     *
     *
     * @param headerNames An array of header names
     * @param fieldAliasMap A map of header names and associated field names
     * @see HeaderMetadata#getFieldNamesAndAliases(Class)
     * @return An ordered map containing the index of the header name and the associated header name from the map provided
     */
    static Map<Integer, String> getCsvHeaderIndexAndName(String[] headerNames, Map<String, String> fieldAliasMap) {
        if (headerNames == null || fieldAliasMap == null) {
            return Collections.emptyMap();
        }
        // important that we keep order or insertion
        Map<Integer, String> headerIndexMap = new LinkedHashMap<>();
        Set<String> columnNames = fieldAliasMap.keySet();
        IntStream.range(0, headerNames.length)
                .forEach(index -> {
                    String csvHeader = headerNames[index].trim();
                    Optional<String> matchedHeaderCheck = columnNames.stream()
                            .filter(columnName -> columnName.equalsIgnoreCase(csvHeader)).findFirst();
                    if (matchedHeaderCheck.isPresent()) {
                        headerIndexMap.put(index, fieldAliasMap.get(matchedHeaderCheck.get()));
                    }
                });
        return headerIndexMap;
    }

    /**
     * Returns a Set of fields from the given Class that have been marked as mandatory.
     * A field is deemed mandatory if it has been marked with the annotations (@NotNull, @NotBlank, @NotEmpty)
     *
     * @param targetClass The class to be inspected
     * @return A Set containing all fields that are marked as mandatory
     */
    static Set<String> getMandatoryFields(Class targetClass) {
        if (targetClass == null) {
            return Collections.emptySet();
        }
        Field[] fields = targetClass.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(field -> {
                    List<Class> fieldAnnotationClasses = Arrays.stream(field.getDeclaredAnnotations())
                            .map(Annotation::annotationType)
                            .collect(Collectors.toList());
                    return  !Collections.disjoint(fieldAnnotationClasses, MANDATORY_ANNOTATIONS);
                })
                .map(Field::getName)
                .collect(Collectors.toSet());
    }

    /**
     * Retrieves the key of a map for a given value.
     *
     * @param map The map containing the key/value pairs.
     * @param value The value who's key will be retrieved.
     * @param <T> The Type of the key
     * @param <E> The Type of the value
     *
     * @return The key matching the value provided
     */
    static  <T, E> T getKeyByValue(Map<T, E> map, E value) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

}
