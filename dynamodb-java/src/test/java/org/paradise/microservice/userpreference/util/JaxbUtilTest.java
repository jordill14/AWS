package org.paradise.microservice.userpreference.util;

import com.ebay.model.GetSellerTransactionsResponseType;
import com.ebay.model.ObjectFactory;
import org.junit.Before;
import org.junit.Test;
import org.paradise.microservice.userpreference.Constants;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

/**
 * Created by terrence on 3/2/17.
 */
public class JaxbUtilTest {

    private JaxbUtil jaxbUtil;

    private String xmlContent;

    private GetSellerTransactionsResponseType getSellerTransactionsResponseType;

    private String version = "989";

    @Before
    public void init() throws IOException, JAXBException {

        jaxbUtil = new JaxbUtil(Constants.CONTEXT_PATH);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/getSellerTransactionsResponse.xml")));

        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while((line = bufferedReader.readLine())!= null){
            stringBuilder.append(line.trim());
        }

        xmlContent = stringBuilder.toString();

        getSellerTransactionsResponseType = createGetSellerTransactionsResponse();
    }

    @Test
    public void testMarshal() throws Exception {

        JAXBElement<GetSellerTransactionsResponseType> getSellerTransactionsResponseTypeJAXBElement =
                new ObjectFactory().createGetSellerTransactionsResponse(getSellerTransactionsResponseType);

        String result = jaxbUtil.marshal(getSellerTransactionsResponseTypeJAXBElement);

        assertEquals("Incorrect User Id", xmlContent, result);
    }

    @Test
    public void testUnmarshal() throws Exception {

        assertEquals("Incorrect User Id", version, getSellerTransactionsResponseType.getVersion());
    }

    private GetSellerTransactionsResponseType createGetSellerTransactionsResponse() throws IOException, JAXBException {

        return jaxbUtil.unmarshal(xmlContent);
    }

}