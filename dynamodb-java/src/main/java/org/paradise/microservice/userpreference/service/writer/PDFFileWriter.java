package org.paradise.microservice.userpreference.service.writer;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by terrence on 21/3/17.
 */
@Component
public class PDFFileWriter<T extends UserPreferences> implements FileWriter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(PDFFileWriter.class);

    private String bucketName = "dev-stack-bucket";
    private String folderName = "pdev01";
    private String keyName = "user-preferences/download/apprentice.pdf";

    @Autowired
    private AmazonS3 amazonS3;

    public void write(WritableResource writableResource, List<T> contents, Class<T> targetClass) {

    }

    public void write(WritableResource writableResource, List<T> contents) throws IOException {

        byte[] content = null;

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("application/pdf");
        objectMetadata.setContentLength(contents.size());

        LOG.debug("Bucket Name: " + bucketName + " Key: " + folderName + "/" + keyName);

        amazonS3.putObject(bucketName, folderName + "/" + keyName, byteArrayInputStream, objectMetadata);
    }

    /**
     * AWS S3 pre-signed URL for object on S3 bucket, e.g.:
     *      https://dev-stack-bucket.s3.amazonaws.com/pdev01/user-preferences/download/apprentice.pdf?
     *          AWSAccessKeyId=AKIAI5DLXEPMLSDDUHPA&Expires=1491534678&Signature=8W2Cg96VL1S2Xb7hN6Qze6ae12Y%3D
     *
     * Content of S3 object can keep being updated but pre-signed URL and signature WON'T change
     *
     * @return AWS S3 object's pre-signed URL
     */
    public URL generatePresignedUrl() {

        Date expiration = Date.from(ZonedDateTime.now().plusHours(TimeUnit.DAYS.toHours(1)).toInstant());

        GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, folderName + "/" + keyName)
                .withExpiration(expiration)
                .withMethod(HttpMethod.GET);

        URL presignedUrl = amazonS3.generatePresignedUrl(presignedUrlRequest);

        LOG.debug("Presigned Url for S3 file: ", generatePresignedUrl());

        return presignedUrl;
    }

}
