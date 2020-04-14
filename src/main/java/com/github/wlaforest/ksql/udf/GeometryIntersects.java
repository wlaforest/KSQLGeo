package com.github.wlaforest.ksql.udf;

import io.confluent.ksql.function.udf.UdfDescription;
import org.locationtech.jts.geom.Geometry;


@UdfDescription(
        name = "geometry_intersects",
        description = "UDF function to test for geometry intersection. See " +
                "https://locationtech.github.io/jts/javadoc/org/locationtech/jts/geom/Geometry.html" +
                "#intersection-org.locationtech.jts.geom.Geometry-",
        version = "1.0-SNAPSHOT",
        author = "Will LaForest"
)

public class GeometryIntersects extends GeometryBase {
    public boolean geometry_intersects(String wkt1, String wkt2) throws GeometryParseException {
        Geometry geometry1 = getGeometryWKT(wkt1);
        Geometry geometry2 = getGeometryWKT(wkt2);

        if (geometry2.intersects(geometry1)) {
            return true;
        } else {
            return false;
        }
    }
}
