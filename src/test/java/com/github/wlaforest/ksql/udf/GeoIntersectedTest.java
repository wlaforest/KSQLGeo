package com.github.wlaforest.ksql.udf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoIntersectedTest
{
    @Test
    void geometry_intersects() throws GeometryParseException {
        GeoIntersected gc = new GeoIntersected();
        assertTrue(gc.geo_intersected(TestStrings.MADISON_SCHOOL_DISTRICT,
                TestStrings.FLINT_HILL));
    }

    @Test
    void geometry_intersects_geojson() throws GeometryParseException {
        GeoIntersected gc = new GeoIntersected();
        assertTrue(gc.geo_intersected(TestStrings.MADISON_SCHOOL_DISTRICT_GEOJSON,
                TestStrings.FLINT_HILL_GEOJSON));
    }

    @Test
    void geometry_intersects_not() throws GeometryParseException {
        GeoIntersected gc = new GeoIntersected();
        assertFalse(gc.geo_intersected(TestStrings.MADISON_SCHOOL_DISTRICT,
                TestStrings.OAKTON_SCHOOL_DISTRICT));
    }

    @Test
    void geometry_intersects_geojson_not() throws GeometryParseException {
        GeoIntersected gc = new GeoIntersected();
        assertFalse(gc.geo_intersected(TestStrings.MADISON_SCHOOL_DISTRICT_GEOJSON,
                TestStrings.OAKTON_SCHOOL_DISTRICT_GEOJSON));
    }

    /**
     * This test check to see that interect returns true when shape is fully contained
     */
    @Test
    void geometry_interseccts_point_contained() throws GeometryParseException {
        GeoIntersected gc = new GeoIntersected();
        assertTrue(gc.geo_intersected(TestStrings.MADISON_SCHOOL_DISTRICT, TestStrings.FLINT_HILL));
    }

    @Test
    void geometry_interssect_long_shape() throws GeometryParseException {
        System.out.println(TestStrings.SHORT_POLY_JSON);
        System.out.println(TestStrings.LINE_STRING_JSON);
        GeoIntersected gi = new GeoIntersected();
//        assertTrue(gi.geo_intersected(POLY_JSON,POLY_JSON));

    }
}
