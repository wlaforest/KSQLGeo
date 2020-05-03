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
        version = "1.0-SNAPSHOT",
        author = "Will LaForest"
)

public class GeometryCrossed extends GeometryBase {
    @Udf(description = "determines if a the two WKT geometriess cross.")
    public boolean geometry_crossed(
            @UdfParameter(value = "wkt1", description = "WKT Encoded Geometry to check for crossing with wk2") final String wkt1,
            @UdfParameter(value = "wkt1", description = "WKT Encoded Geometry to check for crossing with wk1") final String wkt2)
            throws GeometryParseException {

        Geometry geometry1 = getGeometryFromString(wkt1);
        Geometry geometry2 = getGeometryFromString(wkt2);

        if (geometry2.crosses(geometry1)) {
            return true;
        } else {
            return false;
        }
    }
}
