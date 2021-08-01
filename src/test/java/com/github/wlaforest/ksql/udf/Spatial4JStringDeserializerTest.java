package com.github.wlaforest.ksql.udf;

import org.junit.jupiter.api.Test;
import org.locationtech.spatial4j.context.SpatialContextFactory;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;

import static org.junit.jupiter.api.Assertions.*;

class Spatial4JStringDeserializerTest extends BaseGeoUnitTest {

    Spatial4JStringDeserializerTest()
    {
        super(Spatial4JStringDeserializerTest.class);
    }

    @Test
    public void testWktEuclidean() throws GeometryParseException {
        SpatialContextFactory scf = new JtsSpatialContextFactory();
        Spatial4jStringDeserializer ssd = new Spatial4jStringDeserializer(scf, scf.newSpatialContext());
        Shape flintHill = ssd.getSpatial4JShapeFromString(ts("FLINT_HILL_WKT.txt"));
        assertNotNull(flintHill);
        assertFalse(flintHill.isEmpty());
    }

    // Currently not supporting feature collections.  Commenting out until its implemented (if its ever implemented)
//    @Test
//    public void testFullGeoJson() throws GeometryParseException {
//        SpatialContextFactory scf = new JtsSpatialContextFactory();
//        Spatial4jStringDeserializer ssd = new Spatial4jStringDeserializer(scf, scf.newSpatialContext());
//        Shape shape = ssd.getSpatial4JShapeFromString(ts("DC_METRO_MEDIUM_POLY.json"));
//        assertNotNull(shape);
//    }
}