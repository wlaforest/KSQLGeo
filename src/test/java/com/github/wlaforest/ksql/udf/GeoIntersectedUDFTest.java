package com.github.wlaforest.ksql.udf;

import com.github.wlaforest.geo.GeometryParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoIntersectedUDFTest extends BaseGeoUnitTest
{
    final GeoIntersectedUDF gc = new GeoIntersectedUDF();

    GeoIntersectedUDFTest()
    {
        super(GeoIntersectedUDFTest.class);
        gc.configure(null);
    }

    @Test
    void geometry_intersects() throws GeometryParseException {
        assertTrue(gc.geo_intersected(ts("MADISON_SCHOOL_DISTRICT_POLYGON_WKT.txt"),
                ts("FLINT_HILL_WKT.txt")));
    }

    @Test
    void geometry_intersects_geojson() throws GeometryParseException {
        assertTrue(gc.geo_intersected(ts("MADISON_SCHOOL_DISTRICT_POLYGON_GEOJSON.json"),
                ts("FLINT_HILL_GEOJSON.json")));
    }

    @Test
    void geometry_intersects_not() throws GeometryParseException {
        assertFalse(gc.geo_intersected(ts("MADISON_SCHOOL_DISTRICT_POLYGON_WKT.txt"),
                ts("OAKTON_SCHOOL_DISTRICT_WKT.txt")));
    }

    @Test
    void geometry_intersects_geojson_not() throws GeometryParseException {
        assertFalse(gc.geo_intersected(ts("MADISON_SCHOOL_DISTRICT_POLYGON_GEOJSON.json"),
                ts("OAKTON_SCHOOL_DISTRICT_GEOJSON.json")));
    }

    /**
     * This test check to see that interect returns true when shape is fully contained
     */
    @Test
    void geometry_interseccts_point_contained() throws GeometryParseException {
        assertTrue(gc.geo_intersected(ts("MADISON_SCHOOL_DISTRICT_POLYGON_WKT.txt"),
                ts("FLINT_HILL_WKT.txt")));
    }
}
