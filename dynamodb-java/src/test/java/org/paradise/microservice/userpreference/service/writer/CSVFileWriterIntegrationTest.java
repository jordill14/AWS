package org.paradise.microservice.userpreference.service.writer;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by terrence on 29/3/17.
 */
@RunWith(SpringRunner.class)
public class CSVFileWriterIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(CSVFileWriterIntegrationTest.class);

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @MockBean
    private AmazonS3 mockAmazonS3;

    private File errorFile;
    private WritableResource errorResource;

    private CSVFileWriter csvFileWriter = new CSVFileWriter();

    @Before
    public void setUp() throws IOException {

        errorFile = temporaryFolder.newFile("error.csv");
        errorResource = new FileSystemResource(errorFile);

        Answer<PutObjectResult> answer = invocation -> {
            PutObjectRequest putObjectRequest = invocation.getArgumentAt(0, PutObjectRequest.class);

            String bucketName = putObjectRequest.getBucketName();
            String key = putObjectRequest.getKey();
            File file = putObjectRequest.getFile();

            LOG.info("Bucket [{}], Key [{}], File [{}]", bucketName, key, file.getName());

            assertThat("", key, is("error.csv"));

            return new PutObjectResult();
        };

        when(mockAmazonS3.putObject(any(PutObjectRequest.class))).thenAnswer(answer);

        ReflectionTestUtils.setField(csvFileWriter, "amazonS3", mockAmazonS3);
    }

    @Test
    public void write() throws Exception {

        csvFileWriter.write(errorResource, new ArrayList());
    }

    @Test
    public void writeWithoutClassType() throws Exception {

    }

}