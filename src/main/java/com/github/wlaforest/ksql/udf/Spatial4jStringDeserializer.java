package com.github.wlaforest.ksql.udf;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.context.SpatialContextFactory;
import org.locationtech.spatial4j.io.GeoJSONReader;
import org.locationtech.spatial4j.io.WKTReader;
import org.locationtech.spatial4j.shape.Shape;

import java.io.StringReader;
import java.text.ParseException;

public class Spatial4jStringDeserializer {

    private SpatialContextFactory scf;
    private SpatialContext sc;

    public Spatial4jStringDeserializer(SpatialContextFactory scf, SpatialContext sc)
    {
        this.scf = scf;
        this.sc = sc;
    }

    public Shape getSpatial4JShapeFromString(String stringEncoding) throws GeometryParseException
    {

        Shape shape;

        WKTReader reader = new WKTReader(sc, scf);

        try {
             shape = reader.parse(stringEncoding);
             if (shape != null) return shape;
        } catch (ParseException pe) {
            // we will try geojson next
        }

        GeoJSONReader gjr = new GeoJSONReader(sc,scf);
        try {
            shape = gjr.read(new StringReader(stringEncoding));
            if (shape != null) return shape;
        } catch (Exception e) {
            throw new GeometryParseException("Bad string encoding: " + stringEncoding,e);
        }

        throw new GeometryParseException("Bad string encoding: " + stringEncoding);
    }
}
