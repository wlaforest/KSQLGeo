package com.github.wlaforest.ksql.udf;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;


@UdfDescription(
        name = "geo_intersected_sphere",
        description = "UDF function to test for geometry intersection on a spherical model. geometry should be" +
                "encoded in WKT or GeoJSON. null value will result in a false result being returned.",
        version = "1.2.1",
        author = "Will LaForest"
)
public class GeoIntersectedSphereUDF extends GeometryBase {

    @Udf(description = "determines if a the two geometries intersect with a spherical model.")
    public boolean geo_intersected_sphere(
            @UdfParameter(value = "geo1", description = "WKT or GeoJSON Encoded Geometry to check for intersection with geo2") final String geo1,
            @UdfParameter(value = "geo2", description = "WKT or GeoJSON Encoded Geometry to check for intersection with geo1") final String geo2)
            throws GeometryParseException {

        if (geo1 == null || geo2 == null) return false;
        return getSpatial4JHelper().intersect(geo1,geo2, false);
    }
}
