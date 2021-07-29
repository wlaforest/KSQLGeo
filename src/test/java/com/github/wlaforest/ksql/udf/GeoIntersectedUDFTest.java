package com.github.wlaforest.ksql.udf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoIntersectedUDFTest
{
    @Test
    void geometry_intersects() throws GeometryParseException {
        GeoIntersectedUDF gc = new GeoIntersectedUDF();
        assertTrue(gc.geo_intersected(TestStrings.MADISON_SCHOOL_DISTRICT_POLYGON_WKT,
                TestStrings.FLINT_HILL_WKT));
    }

    @Test
    void geometry_intersects_geojson() throws GeometryParseException {
        GeoIntersectedUDF gc = new GeoIntersectedUDF();
        assertTrue(gc.geo_intersected(TestStrings.MADISON_SCHOOL_DISTRICT_POLYGON_GEOJSON,
                TestStrings.FLINT_HILL_GEOJSON));
    }

    @Test
    void geometry_intersects_not() throws GeometryParseException {
        GeoIntersectedUDF gc = new GeoIntersectedUDF();
        assertFalse(gc.geo_intersected(TestStrings.MADISON_SCHOOL_DISTRICT_POLYGON_WKT,
                TestStrings.OAKTON_SCHOOL_DISTRICT_WKT));
    }

    @Test
    void geometry_intersects_geojson_not() throws GeometryParseException {
        GeoIntersectedUDF gc = new GeoIntersectedUDF();
        assertFalse(gc.geo_intersected(TestStrings.MADISON_SCHOOL_DISTRICT_POLYGON_GEOJSON,
                TestStrings.OAKTON_SCHOOL_DISTRICT_GEOJSON));
    }

    /**
     * This test check to see that interect returns true when shape is fully contained
     */
    @Test
    void geometry_interseccts_point_contained() throws GeometryParseException {
        GeoIntersectedUDF gc = new GeoIntersectedUDF();
        assertTrue(gc.geo_intersected(TestStrings.MADISON_SCHOOL_DISTRICT_POLYGON_WKT, TestStrings.FLINT_HILL_WKT));
    }

    @Test
    void geometry_interssect_long_shape() throws GeometryParseException {
        System.out.println(TestStrings.SHORT_POLY_GEOJSON);
        System.out.println(TestStrings.LINE_STRING_GEOJSON);
        GeoIntersectedUDF gi = new GeoIntersectedUDF();
//        assertTrue(gi.geo_intersected(POLY_JSON,POLY_JSON));

    }
}
