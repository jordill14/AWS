package org.paradise.microservice.userpreference.service.writer;

import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.paradise.microservice.userpreference.service.metadata.HeaderMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by terrence on 21/3/17.
 */
@Component
public class CSVFileWriter<T extends UserPreferences> implements FileWriter<T> {

    private final Logger logger = LoggerFactory.getLogger(CSVFileWriter.class);

    @Override
    public void write(WritableResource writableResource, List<T> contents, Class<T> targetClass) {

        final FlatFileItemWriter<T> itemWriter = createItemWriter(writableResource, targetClass);

        try {
            itemWriter.write(contents);
        } catch (Exception e) {
            logger.error("Unable to write Error CSV file");
            throw new RuntimeException("Error writing resource file: " + writableResource.getFilename(), e);
        }

        itemWriter.close();
    }

    private FlatFileItemWriter<T> createItemWriter(WritableResource writableResource, Class<T> targetClass) {

        final Map<String, String> fieldNamesAndAliases = HeaderMetadata.getFieldNamesAndAliases(targetClass);

        final BeanWrapperFieldExtractor<T> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(fieldNamesAndAliases.values().toArray(new String[0]));

        final DelimitedLineAggregator<T> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setFieldExtractor(fieldExtractor);

        final StringHeaderCallback headerWriter = new StringHeaderCallback(String.join(",", fieldNamesAndAliases.keySet().toArray(new String[0])));

        final FlatFileItemWriter<T> itemWriter = new FlatFileItemWriter<>();
        itemWriter.setResource(writableResource);
        itemWriter.setHeaderCallback(headerWriter);
        itemWriter.setLineAggregator(lineAggregator);

        return itemWriter;
    }

}
