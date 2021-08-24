package com.github.wlaforest.ksql.udf;

import com.github.wlaforest.geo.GeometryParseException;
import com.github.wlaforest.geo.Spatial4JHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GeoAreaUDFTest extends BaseGeoUnitTest {

    GeoAreaUDFTest() {
        super(GeoAreaUDFTest.class);
    }

    @Test
    void test_simple_square() throws GeometryParseException, IOException {
        String testString = UnitTestHelper.getResourceFileAsString(this.getClass(),"SQUARE_10_X_10.json");
        GeoAreaUDF gaudf = new GeoAreaUDF();
        HashMap<String,String> argMap = new HashMap<>(1);
        argMap.put(Spatial4JHelper.GEO_CONFIG_PARAM, "false");
        gaudf.configure(argMap);
        double area = gaudf.geo_area(testString);
        assertEquals(1,area);
        assertNotNull(testString);
    }

    /**
     * Test that we get a different area with a geodesic model
     */
    @Test
    void test_simple_square_geodesic() throws GeometryParseException, IOException {
        String testString = UnitTestHelper.getResourceFileAsString(this.getClass(),"SQUARE_10_X_10.json");
        GeoAreaUDF gaudf = new GeoAreaUDF();
        HashMap<String,String> argMap = new HashMap<>(1);
        argMap.put(Spatial4JHelper.GEO_CONFIG_PARAM, "true");
        gaudf.configure(argMap);
        double area = gaudf.geo_area(testString);
        assertTrue(area < 1 && area > .9);
        assertNotNull(testString);
    }
}