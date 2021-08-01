package com.github.wlaforest.ksql.udf;

import java.io.*;

public class UnitTestHelper {

    static protected String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    static protected String getResourceFileAsString(Class classArg, String relativePath) throws IOException {

        InputStream inputStream = null;
        try {
            inputStream = classArg.getResourceAsStream(relativePath);
            return readFromInputStream(inputStream);
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
