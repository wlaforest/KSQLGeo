package com.github.wlaforest.ksql.udf;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import org.locationtech.spatial4j.io.GeohashUtils;

@UdfDescription(
        name = "geo_hash",
        description = "Function to calculate thee geohash of a given point.",
        version = "1.1",
        author = "Will LaForest"
)
public class GeoHash extends GeometryBase
{
    @Udf(description = "Function to calculate thee geohash of a given point.")
    public String geo_hash(
            @UdfParameter(value = "lat", description = "latitude")double lat,
            @UdfParameter(value = "lat", description = "latitude")double lon) throws GeoHashBadParameterException {
        if (lat < -90 || lat > 90 || lon < -180 || lon > 180)
            throw new GeoHashBadParameterException("lat or lon are out of boundaries for -90 to 90 and -180 to 180");
        return GeohashUtils.encodeLatLon(lat,lon);
    }

    @Udf(description = "Function to calculate thee geohash of a given point.")
    public String geo_hash(double lat, double lon, int precision)
    {
        return GeohashUtils.encodeLatLon(lat,lon,precision);
    }

    public class GeoHashBadParameterException extends Exception {
        public GeoHashBadParameterException(String s) {
            super(s);
        }
    }
}
