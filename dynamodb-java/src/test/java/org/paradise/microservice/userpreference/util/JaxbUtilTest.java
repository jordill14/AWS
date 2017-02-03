package org.paradise.microservice.userpreference.util;

import com.ebay.model.GetSellerTransactionsResponseType;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Created by terrence on 3/2/17.
 */
public class JaxbUtilTest {

    private String testXmlFilename = "GetSellerTransactionsResponse.xml";

    @Test
    public void testMarshall() throws Exception {

    }

    @Test
    public void testUnmarshall() throws Exception {

        GetSellerTransactionsResponseType getSellerTransactionsResponseType = createGetSellerTransactionsFixture();

        assertEquals("Incorrect User Id", "1", getSellerTransactionsResponseType.getSeller().getUserID());
    }

    private GetSellerTransactionsResponseType createGetSellerTransactionsFixture() throws IOException, JAXBException {

        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource(testXmlFilename).getFile());

        String xmlContent = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

        return JaxbUtil.unmarshal(xmlContent, GetSellerTransactionsResponseType.class);
    }

}