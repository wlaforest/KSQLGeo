package com.github.wlaforest.ksql.udf;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.io.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class GeometryCrossedTest {
    public static final String MADISON_SCHOOL_DISTRICT = "POLYGON((-77.27483600429103 38.89521905950339," +
            "-77.29131549647853 38.892012508280466,-77.31277316859767 38.89254694353762,-77.32066959193752 " +
            "38.901097360742895,-77.31277316859767 38.90750949802689,-77.29938358119533 38.90697517537252," +
            "-77.30384677699611 38.91378748795597,-77.29818195155666 38.916325241169524,-77.30556339076564" +
            " 38.92927972487108,-77.29869693568752 38.929413263931195,-77.29200214198634 38.93315225554382," +
            "-77.28307575038478 38.92741015163275,-77.2705444698672 38.92126692120997,-77.26608127406642 " +
            "38.916191677473286,-77.2511467342715 38.91819510652208,-77.24634021571681 " +
            "38.91191750646839,-77.27483600429103 38.89521905950339))";

    public static final String OAKTON_SCHOOL_DISTRICT = "POLYGON((-77.3029731301166 38.871966195174494," +
            "-77.26846919334902 38.89027347822612,-77.29662165916933 38.88492877364056," +
            "-77.34846339500918 38.89575138309681,-77.33249888695254 38.8699614616396," +
            "-77.3029731301166 38.871966195174494))";

    public static final String FLINT_HILL = "POLYGON((-77.25595325282619 38.90049514948347,-77.25389331630275 " +
            "38.90503717840573,-77.289598882709 38.91171610485367,-77.2760376339297 " +
            "38.889539649049034,-77.25595325282619 38.90049514948347))";

    @Test
    void geometry_crossed() throws GeometryBase.GeometryParseException {
        GeometryCrossed gc = new GeometryCrossed();
        assertTrue(gc.geometry_crossed(MADISON_SCHOOL_DISTRICT,FLINT_HILL));
    }

    @Test
    void geometry_crossed_not() throws GeometryBase.GeometryParseException {
        GeometryCrossed gc = new GeometryCrossed();
        assertFalse(gc.geometry_crossed(MADISON_SCHOOL_DISTRICT,OAKTON_SCHOOL_DISTRICT));
    }

    @Test
    void geometry_crossed_empty_string_exception() {
        GeometryCrossed gc = new GeometryCrossed();
        try {
            gc.geometry_crossed(MADISON_SCHOOL_DISTRICT,"");
        } catch (GeometryBase.GeometryParseException e) {
            assertNotNull(e);
            return;
        }
        fail();
    }
}
