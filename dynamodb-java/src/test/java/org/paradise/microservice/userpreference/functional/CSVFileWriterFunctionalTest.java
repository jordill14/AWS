package org.paradise.microservice.userpreference.functional;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.io.File;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by terrence on 29/3/17.
 */
@DirtiesContext
@TestPropertySource("/test.properties")
@Ignore
public class CSVFileWriterFunctionalTest extends AbstractFunctionalTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @MockBean(name = "amazonS3Client")
    private AmazonS3 amazonS3;

    private File importFile, statusFile, errorFile;

    @Before
    public void setUp() {

        Answer<PutObjectResult> answer = invocation -> {
            PutObjectRequest putObjectRequest = invocation.getArgumentAt(0, PutObjectRequest.class);

            putObjectRequest.getBucketName();
            putObjectRequest.getKey();
            putObjectRequest.getFile();

            return new PutObjectResult();
        };

        when(amazonS3.putObject(any(PutObjectRequest.class))).thenAnswer(answer);
    }

    @Test
    public void write() throws Exception {

        importFile = temporaryFolder.newFile("import.csv");
        statusFile = temporaryFolder.newFile("status.json");
        errorFile = temporaryFolder.newFile("error.csv");
    }

    @Test
    public void writeWithoutClassType() throws Exception {

    }

}