package com.github.wlaforest.ksql.udf;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import org.locationtech.jts.geom.Geometry;


@UdfDescription(
        name = "geometry_intersects",
        description = "UDF function to test for geometry intersection. See " +
                "https://locationtech.github.io/jts/javadoc/org/locationtech/jts/geom/Geometry.html" +
                "#intersection-org.locationtech.jts.geom.Geometry-",
        version = "1.1",
        author = "Will LaForest"
)

public class GeometryIntersects extends GeometryBase {
    @Udf(description = "determines if a the two geometries intersect.")
    public boolean geometry_intersects(
            @UdfParameter(value = "geo1", description = "WKT or GeoJSON Encoded Geometry to check for intersection with geo2") final String geo1,
            @UdfParameter(value = "geo2", description = "WKT or GeoJSON Encoded Geometry to check for intersection with geo1") final String geo2)
            throws GeometryParseException {

        Geometry geometry1 = getGeometryFromString(geo1);
        Geometry geometry2 = getGeometryFromString(geo2);

        if (geometry2.intersects(geometry1)) {
            return true;
        } else {
            return false;
        }
    }
}
