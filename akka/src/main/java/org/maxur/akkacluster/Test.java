package org.maxur.akkacluster;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.StringWriter;

public class Test {

    static StringWriter textWriter = new StringWriter();

    public static void main(String[] args) {
        code();
        deCode();
    }

    public static void code() {
        Record record = Record.create(true);
        ObjectMapper mapper = new ObjectMapper();

        try {
            //StringWriter textWriter = new StringWriter();

            // convert personobject to json string, and save to a file
            mapper.writeValue(textWriter, record);

            // display to console
            //System.out.println(mapper.writeValueAsString(record));
            System.out.println(textWriter.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deCode() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // read from file, convert it to user class
            Record record = mapper.readValue(textWriter.toString(), Record.class);
            // display to console
            System.out.println(record);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
