package org.paradise.microservice.userpreference.service.writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by terrence on 21/3/17.
 */
public class StringHeaderCallback implements FlatFileHeaderCallback {

    private final String header;

    public StringHeaderCallback(String header) {

        this.header = header;
    }

    @Override
    public void writeHeader(Writer writer) throws IOException {

        writer.write(header);
    }

}
