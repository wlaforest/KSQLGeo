package io.confluent.ksql.udf;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.io.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class GeoContainedTest {
    public static final String MADISON_SCHOOL_DISTRICT = "POLYGON((-77.27483600429103 38.89521905950339," +
            "-77.29131549647853 38.892012508280466,-77.31277316859767 38.89254694353762,-77.32066959193752 " +
            "38.901097360742895,-77.31277316859767 38.90750949802689,-77.29938358119533 38.90697517537252," +
            "-77.30384677699611 38.91378748795597,-77.29818195155666 38.916325241169524,-77.30556339076564" +
            " 38.92927972487108,-77.29869693568752 38.929413263931195,-77.29200214198634 38.93315225554382," +
            "-77.28307575038478 38.92741015163275,-77.2705444698672 38.92126692120997,-77.26608127406642 " +
            "38.916191677473286,-77.2511467342715 38.91819510652208,-77.24634021571681 " +
            "38.91191750646839,-77.27483600429103 38.89521905950339))";


    @Test
    void point_contained_contained() throws ParseException {
        double lat = 38.91376458172108;
        double lon = -77.28468182552359;

        GeoContained geoContained = new GeoContained();
        boolean results = geoContained.point_contained(lat,lon,MADISON_SCHOOL_DISTRICT);
        assertEquals(true, results);
    }

    @Test
    void point_contained_not_lat() throws ParseException {
        double lat = 38.91376458172108;
        double lon = -78.28468182552359;

        GeoContained geoContained = new GeoContained();
        boolean results = geoContained.point_contained(lat,lon,MADISON_SCHOOL_DISTRICT);
        assertEquals(false, results);
    }

    @Test
    void point_contained_not_lon() throws ParseException {
        double lat = 38.91376458172108;
        double lon = -78.28468182552359;

        GeoContained geoContained = new GeoContained();
        boolean results = geoContained.point_contained(lat,lon,MADISON_SCHOOL_DISTRICT);
        assertEquals(false, results);
    }

    @Test
    void point_contained_contained_string() throws ParseException {
        String lat = "38.91376458172108";
        String lon = "-77.28468182552359";

        GeoContained geoContained = new GeoContained();
        boolean results = geoContained.point_contained(lat,lon,MADISON_SCHOOL_DISTRICT);
        assertEquals(true, results);
    }
}