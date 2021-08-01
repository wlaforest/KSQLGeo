package com.github.wlaforest.ksql.udf;

import io.confluent.ksql.function.udtf.Udtf;
import io.confluent.ksql.function.udtf.UdtfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

import java.util.List;


@UdtfDescription(name = "geo_covering_geohashes",
        author = "Will LaForest",
        version = "1.3.0",
        description = "UDTF for computing the geohashes to completely cover a a geometry.  It will calculate all the " +
                "geohashes a geometry falls in. This is very useful for partitioning for the distributed join")
public class GeoHashKeysUDTF extends GeometryBase {

    @Udtf(description = "Takes WKT or GeoJSON Encoded Geometry and a geohash granularity and computes all geohash " +
            "bins the geometry falls in.  This is helpful for re-keying a st")
    public List<String> geo_covering_geohashes(
            @UdfParameter(value = "geo", description = "WKT or GeoJSON Encoded Geometry to check for enclosure") final String geo,
            @UdfParameter(value = "precision", description = "what level of precision?  Goes from 1-12") final int precision) throws GeometryParseException {

        return getSpatial4JHelper().coveringGeoHashes(geo,precision);

    }
    @Udtf(description = "Takes WKT or GeoJSON Encoded Geometry and a geohash granularity and computes all geohash " +
            "bins the geometry falls in.  This is helpful for re-keying a st")
    public List<String> geo_covering_geohashes(
            @UdfParameter(value = "geo", description = "WKT or GeoJSON Encoded Geometry to check for enclosure") final String geo) throws GeometryParseException {

        return geo_covering_geohashes(geo, 7);
    }
}
