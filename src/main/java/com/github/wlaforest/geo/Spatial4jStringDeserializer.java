package com.github.wlaforest.geo;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.context.SpatialContextFactory;
import org.locationtech.spatial4j.io.GeoJSONReader;
import org.locationtech.spatial4j.io.WKTReader;
import org.locationtech.spatial4j.shape.Shape;

import java.io.StringReader;
import java.text.ParseException;

public class Spatial4jStringDeserializer {

    public static final String UNABLE_TO_DECODE_MSG = "Unable to decode shape: ";
    public static final String MODEL_DOES_NOT_SUPPORT_SHAPE_MSG = "Spherical model does not support shape: ";


    private final SpatialContextFactory scf;
    private final SpatialContext sc;

    public Spatial4jStringDeserializer(SpatialContextFactory scf, SpatialContext sc)
    {
        this.scf = scf;
        this.sc = sc;
    }

    public Shape getSpatial4JShapeFromString(String stringEncoding) throws GeometryParseException
    {
        Shape shape;

        GeoJSONReader gjr = new GeoJSONReader(sc,scf);
        try {
            shape = gjr.read(new StringReader(stringEncoding));
            if (shape != null) return shape;
        } catch (UnsupportedOperationException eUnsupported) {
            if (eUnsupported.getMessage().contains("Unsupported shape"))
                throw new GeometryParseException(MODEL_DOES_NOT_SUPPORT_SHAPE_MSG + stringEncoding, eUnsupported);
        } catch (Exception e) {
            // Could parse it GeoJSON, lets try WKT
        }

        WKTReader reader = new WKTReader(sc, scf);
        try {
             shape = reader.parse(stringEncoding);
             if (shape != null) return shape;
        } catch (Exception e) {
            throw new GeometryParseException(UNABLE_TO_DECODE_MSG + stringEncoding,e);
        }

        throw new GeometryParseException(UNABLE_TO_DECODE_MSG + stringEncoding);
    }
}
