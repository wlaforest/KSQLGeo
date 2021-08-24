package com.github.wlaforest.ksql.udf;

import com.github.wlaforest.geo.GeometryParseException;
import io.confluent.ksql.function.udtf.Udtf;
import io.confluent.ksql.function.udtf.UdtfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

import java.util.List;


@UdtfDescription(name = "geo_covering_geohashes",
        author = "Will LaForest",
        version = "1.3.1",
        description = "UDTF for computing the geohashes to completely cover a a geometry.  It will calculate all the " +
                "geohashes a geometry falls in. This is very useful for partitioning for the distributed join")
public class GeoCoveringGeoHashesUDTF extends GeometryBase {

    @Udtf(description = "Takes WKT or GeoJSON Encoded Geometry and a geohash granularity and computes all geohash " +
            "bins the geometry falls in.  This is helpful for re-keying a stream")
    public List<String> geo_covering_geohashes(
            @UdfParameter(value = "geo", description = "WKT or GeoJSON encoded geometry to check for enclosure") final String geo,
            @UdfParameter(value = "precision", description = "what level of precision?  Goes from 1-12") final int precision) throws GeometryParseException {

        try {
            return getSpatial4JHelper().coveringGeoHashes(geo, precision);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }

    }
    @Udtf(description = "Takes WKT or GeoJSON Encoded Geometry and a geohash granularity and computes all geohash " +
            "bins the geometry falls in.  This is helpful for re-keying a steam")
    public List<String> geo_covering_geohashes(
            @UdfParameter(value = "geo", description = "WKT or GeoJSON encoded geometry") final String geo) throws GeometryParseException {

        return geo_covering_geohashes(geo, 7);
    }
}
