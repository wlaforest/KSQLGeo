package com.github.wlaforest.ksql.udf;

import com.github.wlaforest.geo.GeoHashUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import org.locationtech.spatial4j.shape.Rectangle;
import org.locationtech.spatial4j.shape.Shape;
import org.locationtech.spatial4j.shape.ShapeFactory;
import org.locationtech.spatial4j.shape.SpatialRelation;

import static org.junit.jupiter.api.Assertions.*;

// todo: Add a test to make sure adjacent geohashes do NOT intersect

/**
 * Unit test for the GeoHashKeysUDTFTest
 */
class GeoHashKeysUDTFTest {

    @Test
    void testSimple() throws IOException, GeometryParseException {
        String testString = UnitTestHelper.getResourceFileAsString(this.getClass(),"MADISON_SCHOOL_DISTRICT_POLYGON_GEOJSON.json");
        assertNotNull(testString);

        GeoCoveringGeoHashesUDTF gh = new GeoCoveringGeoHashesUDTF();
        List<String> results = gh.geo_covering_geohashes(testString,5);

        // let's validate that the geohashes retuen intersect with the polygon.  First lets get the shape from
        // Spatial4JHelper.

        Spatial4JHelper helper = new Spatial4JHelper();
        Spatial4jStringDeserializer deserializer = helper.getDeserializer();
        Shape testShape = deserializer.getSpatial4JShapeFromString(testString);

        GeoHashUtils ghu = new GeoHashUtils();
        for (String result : results) {
            System.out.println("Checking hash " + result);
            double[] bbox = ghu.decodeBoundingBox(result);
            ShapeFactory shapeFactory = helper.getShapeFactory();
            Rectangle rect = shapeFactory.rect(bbox[1], bbox[3], bbox[0], bbox[2]);
            SpatialRelation relation = testShape.relate(rect);
            assertTrue(relation.intersects());
        }
    }
}