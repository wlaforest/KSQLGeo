package com.github.wlaforest.ksql.udf;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import org.locationtech.jts.geom.Geometry;


@UdfDescription(
        name = "geometry_crossed",
        description = "UDF function to test geometry crossing: See " +
                "https://locationtech.github.io/jts/javadoc/org/locationtech/jts/geom/Geometry.html" +
                "#crosses-org.locationtech.jts.geom.Geometry-",
        version = "1.1",
        author = "Will LaForest"
)

public class GeometryCrossed extends GeometryBase {
    @Udf(description = "determines if a the two WKT geometriess cross.")
    public boolean geometry_crossed(
            @UdfParameter(value = "geo1", description = "WKT or GeoJSON Encoded Geometry to check for crossing with geo1") final String geo1,
            @UdfParameter(value = "geo2", description = "WKT or GeoJSON Encoded Geometry to check for crossing with geo2") final String geo2)
            throws GeometryParseException {

        Geometry geometry1 = getGeometryFromString(geo1);
        Geometry geometry2 = getGeometryFromString(geo2);

        if (geometry2.crosses(geometry1)) {
            return true;
        } else {
            return false;
        }
    }
}
