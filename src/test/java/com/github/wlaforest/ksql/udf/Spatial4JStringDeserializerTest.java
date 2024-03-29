package com.github.wlaforest.ksql.udf;

import com.github.wlaforest.geo.GeometryParseException;
import com.github.wlaforest.geo.Spatial4JHelper;
import com.github.wlaforest.geo.Spatial4jStringDeserializer;
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

    @Test
    public void testGeoJSONFeatureCollection()
    {
        Spatial4JHelper helper = new Spatial4JHelper();
        Spatial4jStringDeserializer ssd = helper.getDeserializer();
        try {
            ssd.getSpatial4JShapeFromString(ts("FEATURE_COLLECTION.json"));
        } catch (GeometryParseException e) {
            //feature colleciton not yet supported so this should fail
            return;
        }
        fail();
    }

    @Test
    public void testFeature()
    {
        Spatial4JHelper helper = new Spatial4JHelper();
        Spatial4jStringDeserializer ssd = helper.getDeserializer();
        try {
            Shape foo = ssd.getSpatial4JShapeFromString(ts("FEATURE_DOC.json"));
            assertTrue(foo.hasArea());
            assertFalse(foo.isEmpty());
        } catch (GeometryParseException e) {
            fail();
        }
    }
}