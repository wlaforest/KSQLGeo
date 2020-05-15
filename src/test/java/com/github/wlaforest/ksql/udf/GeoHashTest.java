package com.github.wlaforest.ksql.udf;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GeoHashTest
{
    @Test
    void testSimple() throws GeoHash.GeoHashBadParameterException {
        GeoHash gh = new GeoHash();
        String result = gh.geo_hash(-82,76);
        assertNotNull(result);
    }

    @Test
    void testPrecion() throws GeoHash.GeoHashBadParameterException {
        GeoHash gh = new GeoHash();
        String result = gh.geo_hash(-82,76);
        assertNotNull(result);
    }

    @Test
    void testBadValues() {
        GeoHash gh = new GeoHash();
        try{
            gh.geo_hash(-82032,74436);
        }
        catch ( GeoHash.GeoHashBadParameterException ghb)
        {
            return;
        }

        fail("bad paraameters should have thrown excpetion");
    }
}
