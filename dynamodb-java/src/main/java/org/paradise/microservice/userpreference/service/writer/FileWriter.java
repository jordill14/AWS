package org.paradise.microservice.userpreference.service.writer;

import org.springframework.core.io.WritableResource;

import java.io.IOException;
import java.util.List;

/**
 * Created by terrence on 21/3/17.
 */
public interface FileWriter<T> {

    void write(WritableResource writableResource, List<T> contents) throws IOException;

    void write(WritableResource writableResource, List<T> contents, Class<T> targetClass);

}
