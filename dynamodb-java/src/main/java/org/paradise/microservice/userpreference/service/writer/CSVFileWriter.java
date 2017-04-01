package org.paradise.microservice.userpreference.service.writer;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.paradise.microservice.userpreference.service.metadata.HeaderMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by terrence on 21/3/17.
 */
@Component
public class CSVFileWriter<T extends UserPreferences> implements FileWriter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(CSVFileWriter.class);

    @Autowired
    private AmazonS3 amazonS3;

    public void write(WritableResource writableResource, List<T> contents, Class<T> targetClass) {

        final FlatFileItemWriter<T> itemWriter = createItemWriter(writableResource, targetClass);

        try {
            itemWriter.write(contents);
        } catch (Exception e) {
            LOG.error("Unable to write Error CSV file");
            throw new RuntimeException("Error writing resource file: " + writableResource.getFilename(), e);
        }

        itemWriter.close();
    }

    public void write(WritableResource writableResource, List<T> contents) throws IOException {

        // Example for AWS S3 object: s3://my-bucket/userPreferences/upload/import.csv
        // getFileName() get: /userPreferences/upload/import.csv
        String fileName = writableResource.getFilename();
        // getURL.getPath() get: /my-bucket/userPreferences/upload/import.csv
        String bucketName = writableResource.getURL().getPath().replace(fileName, StringUtils.EMPTY).replace("/", StringUtils.EMPTY);

        File errorCsvTempFile = File.createTempFile("user-preferences-import", "csv");

        FileUtils.writeLines(errorCsvTempFile, contents);

        LOG.debug("Writing import CSV to bucket [{}] with file [{}]", bucketName, fileName);

        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, errorCsvTempFile));
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
