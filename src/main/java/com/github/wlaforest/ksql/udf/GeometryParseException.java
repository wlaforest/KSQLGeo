package com.github.wlaforest.ksql.udf;


public class GeometryParseException extends Exception {
    public GeometryParseException(String s) {
        super(s);
    }
    public GeometryParseException(String s, Exception e) {
        super(s, e);
    }
}