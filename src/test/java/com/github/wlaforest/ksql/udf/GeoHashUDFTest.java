package com.github.wlaforest.ksql.udf;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GeoHashUDFTest
{
    @Test
    void testSimple() throws GeoHashUDF.GeoHashBadParameterException {
        GeoHashUDF gh = new GeoHashUDF();
        String result = gh.geo_hash(-82,76);
        assertNotNull(result);
    }

    @Test
    void testPrecion() throws GeoHashUDF.GeoHashBadParameterException {
        GeoHashUDF gh = new GeoHashUDF();
        String result = gh.geo_hash(-82,76);
        assertNotNull(result);
    }

    @Test
    void testBadValues() {
        GeoHashUDF gh = new GeoHashUDF();
        try{
            gh.geo_hash(-82032,74436);
        }
        catch ( GeoHashUDF.GeoHashBadParameterException ghb)
        {
            return;
        }

        fail("bad paraameters should have thrown excpetion");
    }
}
