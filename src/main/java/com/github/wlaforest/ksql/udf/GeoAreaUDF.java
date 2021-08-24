package com.github.wlaforest.ksql.udf;

import com.github.wlaforest.geo.GeometryParseException;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udtf.UdtfDescription;

@UdtfDescription(name = "geo_area",
        author = "Will LaForest",
        version = "1.3.1",
        description = "UDF to calculate the area of a shape")
public class GeoAreaUDF extends GeometryBase {

    @Udf(description = "Takes WKT or GeoJSON Encoded Geometry and calculates the area in square degrees")
    public double geo_area(
            @UdfParameter(value = "geo", description = "WKT or GeoJSON Encoded Geometry to calculate area for") final String geo) throws GeometryParseException {

        return getSpatial4JHelper().area(geo);
    }
}
