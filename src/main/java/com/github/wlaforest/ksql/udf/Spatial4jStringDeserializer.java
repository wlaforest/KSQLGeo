package com.github.wlaforest.ksql.udf;

import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.context.SpatialContextFactory;
import org.locationtech.spatial4j.io.GeoJSONReader;
import org.locationtech.spatial4j.io.WKTReader;
import org.locationtech.spatial4j.shape.Shape;

import java.io.StringReader;
import java.text.ParseException;

public class Spatial4jStringDeserializor {

    protected static Shape getSpatial4JShapeFromString(String stringEncoding) throws GeometryParseException {

        SpatialContextFactory scf = new SpatialContextFactory();
        SpatialContext sc = scf.newSpatialContext();

        WKTReader reader = new WKTReader(sc, scf);
        try {
             return reader.parse(stringEncoding);
        } catch (ParseException pe) {
            GeoJSONReader gjr = new GeoJSONReader(sc,scf);
            try {
                gjr.read(new StringReader(stringEncoding));
            } catch (Exception e) {
                throw new GeometryParseException("Bad string encoding: " + stringEncoding);
            }
        }
    }
}
