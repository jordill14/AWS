package org.paradise.microservice.userpreference.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by terrence on 3/2/17.
 */
public final class JaxbUtil {

    private JaxbUtil() {

    }

    public static <T> String marshal(JAXBElement<?> jaxbElement, Class<T> clazz) throws JAXBException {

        StringWriter stringWriter = new StringWriter();

        JAXBContext.newInstance(clazz).createMarshaller().marshal(jaxbElement, stringWriter);

        return stringWriter.toString();
    }

    public static <T> T unmarshal(String body, Class<T> clazz) throws JAXBException {

        StringReader stringReader = new StringReader(body);

        JAXBElement<T> jaxbElement = (JAXBElement<T>) JAXBContext.newInstance(clazz).createUnmarshaller().unmarshal(stringReader);

        return jaxbElement.getValue();
    }

}
