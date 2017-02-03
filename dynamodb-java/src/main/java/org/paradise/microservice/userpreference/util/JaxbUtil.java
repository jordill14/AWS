package org.paradise.microservice.userpreference.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.io.StringWriter;

import static javax.xml.bind.JAXBContext.newInstance;

/**
 * Created by terrence on 3/2/17.
 */
public class JaxbUtil {

    private JAXBContext jaxbContext;

    public JaxbUtil(String contextPath) throws JAXBException {

        jaxbContext = newInstance(contextPath);
    }

    public <T> String marshal(JAXBElement<T> jaxbElement) throws JAXBException {

        StringWriter stringWriter = new StringWriter();

        jaxbContext.createMarshaller().marshal(jaxbElement, stringWriter);

        return stringWriter.toString();
    }

    @SuppressWarnings(value = "unchecked")
    public <T> T unmarshal(String body) throws JAXBException {

        StringReader stringReader = new StringReader(body);

        JAXBElement<T> jaxbElement = (JAXBElement<T>) jaxbContext.createUnmarshaller().unmarshal(stringReader);

        return jaxbElement.getValue();
    }

}
