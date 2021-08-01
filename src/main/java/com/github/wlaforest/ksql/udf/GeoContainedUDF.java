package com.github.wlaforest.ksql.udf;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;


@UdfDescription(
        name = "geo_contained",
        description = "UDF function to test containment of a point in a geometry.  Geometry can be encoded in WKT" +
                "or GeoJSON.  null paremeters will always result in false",
        version = "1.3.0",
        author = "Will LaForest"
)
public class GeoContainedUDF extends GeometryBase {
    @Udf(description = "determines if a double value lat/long is inside or outside the geometry passed as the " +
            "3rd parameter encoded in either WKT OR GeoJSON.")
    public boolean geo_contained(
            @UdfParameter(value = "latitude", description = "the latitude of the point") final double latitude,
            @UdfParameter(value = "longitude", description = "the longitude of the point") final double longitude,
            @UdfParameter(value = "geo", description = "WKT or GeoJSON Encoded Geometry to check for enclosure") final String geo) throws GeometryParseException {
        try {
            if (geo == null) return false;
            return getSpatial4JHelper().contained(geo, latitude, longitude);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Udf(description = "determines if a String value lat/long is inside or outside the geometry passed as the" +
            " 3rd parameter as wkt or geoJSON")
    public boolean geo_contained(
            @UdfParameter(value = "latitude", description = "the latitude of the point") final String latitude,
            @UdfParameter(value = "longitude", description = "the longitude of the point") final String longitude,
            @UdfParameter(value = "geo", description = "WKT or GeoJSON Encoded Geometry to check for enclosure") final String geo)
                throws GeometryParseException {

        if (latitude == null || longitude == null) return false;
        return geo_contained(Double.parseDouble(latitude), Double.parseDouble(longitude), geo);
    }

    @Udf(description = "determines if geo1 is contained within geo2 where geo1 and geo2 are encoded as wkt or geoJSON")
    public boolean geo_contained(
            @UdfParameter(value = "geo1", description = "WKT or GeoJSON Encoded Geometry to check for enclosure") final String geo1,
            @UdfParameter(value = "geo2", description = "WKT or GeoJSON Encoded Geometry to check for enclosure") final String geo2)
                throws GeometryParseException {

        return getSpatial4JHelper().contained(geo1,geo2);
    }

}
