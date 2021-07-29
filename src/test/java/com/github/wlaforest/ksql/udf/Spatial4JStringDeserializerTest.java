package com.github.wlaforest.ksql.udf;

import org.junit.jupiter.api.Test;
import org.locationtech.spatial4j.context.SpatialContextFactory;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;

import static org.junit.jupiter.api.Assertions.*;

class Spatial4JStringDeserializerTest {

    @Test
    public void testWktEuclidean() throws GeometryParseException {
        SpatialContextFactory scf = new JtsSpatialContextFactory();
        Spatial4jStringDeserializer ssd = new Spatial4jStringDeserializer(scf, scf.newSpatialContext());
        Shape flintHill = ssd.getSpatial4JShapeFromString(TestStrings.FLINT_HILL_WKT);
        assertNotNull(flintHill);
        assertFalse(flintHill.isEmpty());
    }
}