package com.github.wlaforest.ksql.udf;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import org.locationtech.jts.geom.Geometry;


@UdfDescription(
        name = "geometry_contained",
        description = "UDF function to test containment of a geometry in another geometry.  The container " +
                "argument will always be a polygon but the geometry to test for containment inside of the container " +
                "can be any Geometry",
        version = "1.1",
        author = "Will LaForest"
)

public class GeometryContained extends GeometryBase {
    @Udf(description = "determines if a double value lat/long is inside or outside the geometry passed as the " +
            "3rd parameter encoded in either WKT OR GeoJSON.")
    public boolean geometry_contained(
            @UdfParameter(value = "latitude", description = "the latitude of the point") final double latitude,
            @UdfParameter(value = "longitude", description = "the longitude of the point") final double longitude,
            @UdfParameter(value = "geo", description = "WKT or GeoJSON Encoded Geometry to check for enclosure") final String geo) throws GeometryParseException {

        return getSpatial4JHelper().contained(geo, latitude, longitude, true);

    }

    @Udf(description = "determines if a String value lat/long is inside or outside the geometry passed as the" +
            " 3rd parameter as wkt or geoJSON")
    public boolean geometry_contained(
            @UdfParameter(value = "latitude", description = "the latitude of the point") final String latitude,
            @UdfParameter(value = "longitude", description = "the longitude of the point") final String longitude,
            @UdfParameter(value = "geo", description = "WKT or GeoJSON Encoded Geometry to check for enclosure") final String geo) throws GeometryParseException {
        return geometry_contained(Double.parseDouble(latitude), Double.parseDouble(longitude), geo);
    }
}
