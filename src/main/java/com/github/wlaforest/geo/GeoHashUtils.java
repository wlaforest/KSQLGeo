package com.github.wlaforest.geo;

import java.util.HashMap;
import java.util.Map;

/**
 * Util class for geo hashes.
 * @author Will LaForest
 */
public class GeoHashUtils
{

    private final static String BASE32_CODES = "0123456789bcdefghjkmnpqrstuvwxyz";
    private final Map<Character, Integer> base32CodesMap;

    public GeoHashUtils()
    {
        base32CodesMap = new HashMap<>(32);
        for (int i = 0, l = BASE32_CODES.length(); i < l; i++)
        {
            base32CodesMap.put(BASE32_CODES.charAt(i), i);
        }
    }

    /**
     * Decode Bounding Box from a geohash
     *
     * Decode hashString into a bound box matches it. Data returned in a four-element array: [minlat, minlon, maxlat, maxlon]
     * @param geoHash geohash to decode
     * @return length 4 array of doubles [minlat, minlon, maxlat, maxlon]
     */
    public double[] decodeBoundingBox(String geoHash) {
        String geoHashLower = geoHash.toLowerCase();
        boolean isLon = true;
        double  maxLat = 90, minLat = -90, maxLon = 180,minLon = -180, mid;

        int hashValue;

        for (int i = 0, l = geoHashLower.length(); i < l; i++) {
            char code = geoHashLower.charAt(i);
            hashValue = base32CodesMap.get(code);

            for (int bits = 4; bits >= 0; bits--) {
                int bit = (hashValue >> bits) & 1;
                if (isLon) {
                    mid = (maxLon + minLon) / 2;
                    if (bit == 1) {
                        minLon = mid;
                    } else {
                        maxLon = mid;
                    }
                } else {
                    mid = (maxLat + minLat) / 2;
                    if (bit == 1) {
                        minLat = mid;
                    } else {
                        maxLat = mid;
                    }
                }
                isLon = !isLon;
            }
        }
        return new double[] {minLat, minLon, maxLat, maxLon};
    }
}
