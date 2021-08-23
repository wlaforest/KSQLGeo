package com.github.wlaforest.ksql.udf;

import com.github.wlaforest.geo.GeometryParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GeoAreaUDFTest extends BaseGeoUnitTest {

    GeoAreaUDFTest() {
        super(GeoAreaUDFTest.class);
    }

    @Test
    void test_simple_square() throws GeometryParseException, IOException {
        String testString = UnitTestHelper.getResourceFileAsString(this.getClass(),"SQUARE_10_X_10.json");
        GeoAreaUDF gaudf = new GeoAreaUDF();
        double area = gaudf.geo_area(testString);
        assertEquals(0.9996446369807445,area);
        assertNotNull(testString);
    }
}