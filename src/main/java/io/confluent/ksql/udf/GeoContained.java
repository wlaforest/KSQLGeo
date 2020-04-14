package io.confluent.ksql.udf;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.GeometryFactory;


@UdfDescription(
        name = "point_contained",
        description = "UDF function to test containment of a point",
        version = "1.0-SNAPSHOT",
        author = "Will LaForest"
)

public class GeoContained {
    @Udf(description = "determines if a double value lat/long is inside or outside the geometry passed as the 3rdparameter as WKT encoded ...")
    public boolean point_contained(
            @UdfParameter(value = "latitude", description = "the latitude of the point") final double latitude,
            @UdfParameter(value = "longitude", description = "the longitude of the point") final double longitude,
            @UdfParameter(value = "geometryWKT", description = "WKT Encoded Geometry to check for enclosure") final String geometryWKT)
            throws ParseException
    {

        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);
        Polygon polygon = (Polygon) reader.read(geometryWKT);
        Coordinate coord = new Coordinate(longitude, latitude);
        Point point = geometryFactory.createPoint(coord);
        if (point.within(polygon)) {
            return true;
        } else {
            return false;
        }
    }

    @Udf(description = "determines if a String value lat/long is inside or outside the geometry passed as the 3rdparameter as WKT encoded ...")
    public boolean point_contained(
            @UdfParameter(value = "latitude", description = "the latitude of the point") final String latitude,
            @UdfParameter(value = "longitude", description = "the longitude of the point") final String longitude,
            @UdfParameter(value = "geometryWKT", description = "WKT Encoded Geometry to check for enclosure") final String geometryWKT)
            throws ParseException
    {
        String status = "";
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        WKTReader reader = new WKTReader(geometryFactory);
        Polygon polygon = (Polygon) reader.read(geometryWKT);
        Coordinate coord = new Coordinate(Double.parseDouble(longitude), Double.parseDouble(latitude));
        Point point = geometryFactory.createPoint(coord);
        if (point.within(polygon)) {
            return true;
        } else {
            return false;
        }
    }
}
