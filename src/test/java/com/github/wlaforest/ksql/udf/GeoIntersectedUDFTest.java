package com.github.wlaforest.ksql.udf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoIntersectedUDFTest extends BaseGeoUnitTest
{
    GeoIntersectedUDFTest()
    {
        super(GeoIntersectedUDFTest.class);
    }

    @Test
    void geometry_intersects() throws GeometryParseException {
        GeoIntersectedUDF gc = new GeoIntersectedUDF();
        assertTrue(gc.geo_intersected(ts("MADISON_SCHOOL_DISTRICT_POLYGON_WKT.txt"),
                ts("FLINT_HILL_WKT.txt")));
    }

    @Test
    void geometry_intersects_geojson() throws GeometryParseException {
        GeoIntersectedUDF gc = new GeoIntersectedUDF();
        assertTrue(gc.geo_intersected(ts("MADISON_SCHOOL_DISTRICT_POLYGON_GEOJSON.json"),
                ts("FLINT_HILL_GEOJSON.json")));
    }

    @Test
    void geometry_intersects_not() throws GeometryParseException {
        GeoIntersectedUDF gc = new GeoIntersectedUDF();
        assertFalse(gc.geo_intersected(ts("MADISON_SCHOOL_DISTRICT_POLYGON_WKT.txt"),
                ts("OAKTON_SCHOOL_DISTRICT_WKT.txt")));
    }

    @Test
    void geometry_intersects_geojson_not() throws GeometryParseException {
        GeoIntersectedUDF gc = new GeoIntersectedUDF();
        assertFalse(gc.geo_intersected(ts("MADISON_SCHOOL_DISTRICT_POLYGON_GEOJSON.json"),
                ts("OAKTON_SCHOOL_DISTRICT_GEOJSON.json")));
    }

    /**
     * This test check to see that interect returns true when shape is fully contained
     */
    @Test
    void geometry_interseccts_point_contained() throws GeometryParseException {
        GeoIntersectedUDF gc = new GeoIntersectedUDF();
        assertTrue(gc.geo_intersected(ts("MADISON_SCHOOL_DISTRICT_POLYGON_WKT.txt"),
                ts("FLINT_HILL_WKT.txt")));
    }

}
