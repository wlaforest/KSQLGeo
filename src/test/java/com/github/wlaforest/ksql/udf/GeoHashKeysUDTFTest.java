package com.github.wlaforest.ksql.udf;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeoHashKeysUDTFTest {

    @Test
    void testSimple() throws IOException {
        String testString = UnitTestHelper.getResourceFileAsString(this.getClass(),"DC_METRO_MEDIUM_POLY.json");
        assertNotNull(testString);

        GeoHashKeysUDTF gh = new GeoHashKeysUDTF();
        List<String> result = gh.geo_hash_keys("foo");

        assertNotNull(result);
    }
}