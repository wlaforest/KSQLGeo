package com.github.wlaforest.ksql.udf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoContainedUDFTest extends BaseGeoUnitTest {

    GeoContainedUDFTest() {
        super(GeoContainedUDFTest.class);
    }

    @Test
    void point_contained_contained() throws GeometryParseException {
        double lat = 38.91376458172108;
        double lon = -77.28468182552359;

        GeoContainedUDF geometryContained = new GeoContainedUDF();
        boolean results = geometryContained.geo_contained(lat,lon, ts("MADISON_SCHOOL_DISTRICT_POLYGON_WKT.txt"));
        assertTrue(results);
    }

    @Test
    void point_contained_not_lat() throws GeometryParseException {
        double lat = 38.91376458172108;
        double lon = -78.28468182552359;

        GeoContainedUDF geometryContained = new GeoContainedUDF();
        boolean results = geometryContained.geo_contained(lat,lon,ts("MADISON_SCHOOL_DISTRICT_POLYGON_WKT.txt"));
        assertFalse(results);
    }

    @Test
    void point_contained_not_lon() throws GeometryParseException {
        double lat = 38.91376458172108;
        double lon = -78.28468182552359;

        GeoContainedUDF geometryContained = new GeoContainedUDF();
        boolean results = geometryContained.geo_contained(lat,lon,ts("MADISON_SCHOOL_DISTRICT_POLYGON_WKT.txt"));
        assertFalse(results);
    }

    @Test
    void point_contained_contained_string() throws GeometryParseException {
        String lat = "38.91376458172108";
        String lon = "-77.28468182552359";

        GeoContainedUDF geometryContained = new GeoContainedUDF();
        boolean results = geometryContained.geo_contained(lat,lon,ts("MADISON_SCHOOL_DISTRICT_POLYGON_WKT.txt"));
        assertTrue(results);
    }

    @Test
    void point_contained_contained_circle() throws GeometryParseException {
        String lat = "38.91376458172108";
        String lon = "-77.28468182552359";

        GeoContainedUDF geometryContained = new GeoContainedUDF();
        boolean results = geometryContained.geo_contained(lat,lon,ts("MADISON_SCHOOL_DISTRICT_POLYGON_WKT.txt"));
        assertTrue(results);
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void point_contain_geoJSON() throws GeometryParseException {
        double lat = 49.43663;
        double lon = 7.76968;
        GeoContainedUDF geoContained = new GeoContainedUDF();
        assertTrue(geoContained.geo_contained(lon, lat, ts("GEOJSON_TEST.json")));

    }
}
