package com.github.wlaforest.ksql.udf;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.io.WKTReader;


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

        return getSpatial4JHelper().intersect(geo1,geo2, true);
    }

    @Udf(description = "determines if a the two geometries intersect with a spherical model.")
    public boolean geometry_intersects_sphere(
            @UdfParameter(value = "geo1", description = "WKT or GeoJSON Encoded Geometry to check for intersection with geo2") final String geo1,
            @UdfParameter(value = "geo2", description = "WKT or GeoJSON Encoded Geometry to check for intersection with geo1") final String geo2)
            throws GeometryParseException {

        return getSpatial4JHelper().intersect(geo1,geo2, false);
    }
}
