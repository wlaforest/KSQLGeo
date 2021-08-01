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

class GeoHashKeysUDTFTest {

    @Test
    void testSimple() throws IOException, GeometryParseException {
        String testString = UnitTestHelper.getResourceFileAsString(this.getClass(),"MADISON_SCHOOL_DISTRICT_POLYGON_GEOJSON.json");
        assertNotNull(testString);

        GeoHashKeysUDTF gh = new GeoHashKeysUDTF();
        List<String> results = gh.geo_hash_keys(testString,5);

        // lets validate that the geohashes retuen intersect with the polygon.  First lets get the shape from
        // Spatial4JHelper.

        Spatial4JHelper helper = new Spatial4JHelper();
        Spatial4jStringDeserializer deserializer = helper.getDeserializer();
        Shape testShape = deserializer.getSpatial4JShapeFromString(testString);

        GeoHashUtils ghu = new GeoHashUtils();
        for (int i = 0, l = results.size(); i < l; i++)
        {
            System.out.println("Checking hash " + results.get(i));
            double[] bbox = ghu.decodeBoundingBox(results.get(i));
            ShapeFactory shapeFactory = helper.getShapeFactory();
            Rectangle rect = shapeFactory.rect(bbox[0],bbox[1],bbox[2],bbox[3]);
            SpatialRelation relation = testShape.relate(rect);
            assertTrue(relation.intersects());
        }
    }
}