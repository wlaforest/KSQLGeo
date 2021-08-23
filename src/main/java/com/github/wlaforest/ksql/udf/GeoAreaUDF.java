package com.github.wlaforest.ksql.udf;

import com.github.wlaforest.geo.GeometryParseException;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udtf.Udtf;
import io.confluent.ksql.function.udtf.UdtfDescription;

import java.util.List;


@UdtfDescription(name = "geo_area",
        author = "Will LaForest",
        version = "1.3.1",
        description = "UDF to calculate the area of a shape")
public class GeoAreaUDF extends GeometryBase {

    @Udtf(description = "Takes WKT or GeoJSON Encoded Geometry and calculates the area in square degrees")
    public double geo_area(
            @UdfParameter(value = "geo", description = "WKT or GeoJSON Encoded Geometry to calculate area for") final String geo) throws GeometryParseException {

        return getSpatial4JHelper().area(geo);
    }
}
