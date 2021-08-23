package com.github.wlaforest.geo;


public class GeometryParseException extends Exception {
    public GeometryParseException(String s) {
        super(s);
    }
    public GeometryParseException(String s, Exception e) {
        super(s, e);
    }
}