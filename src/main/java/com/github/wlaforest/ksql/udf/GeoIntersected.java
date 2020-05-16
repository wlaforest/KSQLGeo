package com.github.wlaforest.ksql.udf;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.io.WKTReader;


@UdfDescription(
        name = "geo_intersected",
        description = "UDF function to test for geometry intersection.",
        version = "1.1",
        author = "Will LaForest"
)
public class GeoIntersected extends GeometryBase {

    @Udf(description = "determines if a the two geometries intersect.")
    public boolean geo_intersected (
            @UdfParameter(value = "geo1", description = "WKT or GeoJSON Encoded Geometry to check for intersection with geo2") final String geo1,
            @UdfParameter(value = "geo2", description = "WKT or GeoJSON Encoded Geometry to check for intersection with geo1") final String geo2)
            throws GeometryParseException {

        return getSpatial4JHelper().intersect(geo1,geo2, true);
    }
}
