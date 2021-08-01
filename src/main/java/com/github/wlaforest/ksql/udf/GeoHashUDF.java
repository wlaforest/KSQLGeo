package com.github.wlaforest.ksql.udf;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import org.locationtech.spatial4j.io.GeohashUtils;

@UdfDescription(
        name = "geo_hash",
        description = "Function to calculate the geohash of a given point.  Based on the Lucene code" +
                "https://lucene.apache.org/core/5_5_0/spatial/org/apache/lucene/spatial/util/GeoHashUtils.html",
        version = "1.3.0",
        author = "Will LaForest"
)
public class GeoHashUDF
{
    @Udf(description = "Function to calculate the geohash of a given point.  Precision defaults to 12")
    public String geo_hash(
            @UdfParameter(value = "lat", description = "latitude")double lat,
            @UdfParameter(value = "lat", description = "longitude")double lon) throws GeoHashBadParameterException {
        if (lat < -90 || lat > 90 || lon < -180 || lon > 180)
            throw new GeoHashBadParameterException("lat or lon are out of boundaries for -90 to 90 and -180 to 180");
        return GeohashUtils.encodeLatLon(lat,lon);
    }

    @Udf(description = "Function to calculate thee geohash of a given point.")
    public String geo_hash(
            @UdfParameter(value = "lat", description = "latitude")double lat,
            @UdfParameter(value = "lon", description = "longitude")double lon,
            @UdfParameter(value = "precision", description = "what level of precision?  Goes from 1-12")
                    int precision)
    {
        return GeohashUtils.encodeLatLon(lat,lon,precision);
    }

    public class GeoHashBadParameterException extends Exception {
        public GeoHashBadParameterException(String s) {
            super(s);
        }
    }
}
