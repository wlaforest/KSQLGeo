package com.github.wlaforest.geo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoHashUtilsTest {

    @Test
    public void testDecode()
    {
        String geoHash = "hf78j";
        GeoHashUtils ghu = new GeoHashUtils();
        double[] retval = ghu.decodeBoundingBox(geoHash);
        assertNotNull(retval);
        assertEquals(retval.length, 4);
    }
}