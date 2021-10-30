package org.maxur.akkacluster;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.StringWriter;

public class SerialObj {
    public static String serial(Record record) {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter textWriter = new StringWriter();
        try {
            // convert personobject to json string, and save to a file
            mapper.writeValue(textWriter, record);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textWriter.toString();
    }

    public static Record deSerial(String jsonRecord) {
        ObjectMapper mapper = new ObjectMapper();
        Record record = null;
        try {
            // read from file, convert it to user class
            record = mapper.readValue(jsonRecord, Record.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return record;
    }
}
