package com.github.wlaforest.ksql.udf;

import io.confluent.ksql.function.udf.UdfDescription;
import org.locationtech.jts.geom.Geometry;


@UdfDescription(
        name = "geometry_crossed",
        description = "UDF function to test containment of a point",
        version = "1.0-SNAPSHOT",
        author = "Will LaForest"
)

public class GeometryCrossed extends GeometryBase {
    public boolean geometry_crossed(String wkt1, String wkt2) throws GeometryParseException {
        Geometry geometry1 = getGeometryWKT(wkt1);
        Geometry geometry2 = getGeometryWKT(wkt2);

        if (geometry2.crosses(geometry1)) {
            return true;
        } else {
            return false;
        }
    }
}
