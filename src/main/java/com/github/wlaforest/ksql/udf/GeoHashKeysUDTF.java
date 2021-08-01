package com.github.wlaforest.ksql.udf;

import io.confluent.ksql.function.udtf.Udtf;
import io.confluent.ksql.function.udtf.UdtfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

import java.util.ArrayList;
import java.util.List;


@UdtfDescription(name = "geo_hash_keys",
        author = "Will LaForest",
        version = "1.3.0",
        description = "UDTF for computing multiple geohashes for a geometry.  It will calculate all the geohashes a " +
                "geometry falls in. This is very usefual for partitioning for the distributed join")
public class GeoHashKeysUDTF extends GeometryBase {


    @Udtf(description = "Takes WKT or GeoJSON Encoded Geometry and a geoahsh granularity and computes all geohash " +
            "bins the geometry falls in.  This is helpful for re-keying a st")
    public List<String> geo_hash_keys(
            @UdfParameter(value = "geo", description = "WKT or GeoJSON Encoded Geometry to check for enclosure") final String geo,
            @UdfParameter(value = "precision", description = "what level of precision?  Goes from 1-12") final int precision) {
        List<String> result = new ArrayList<>();


        result.add("1");
        result.add("2");
        result.add("3");

        return result;
    }
    @Udtf(description = "Takes WKT or GeoJSON Encoded Geometry and a geoahsh granularity and computes all geohash " +
            "bins the geometry falls in.  This is helpful for re-keying a st")
    public List<String> geo_hash_keys(
            @UdfParameter(value = "geo", description = "WKT or GeoJSON Encoded Geometry to check for enclosure") final String geo) {

        List<String> result = new ArrayList<>();

        result.add("1");
        result.add("2");
        result.add("3");

        return result;
    }
}
