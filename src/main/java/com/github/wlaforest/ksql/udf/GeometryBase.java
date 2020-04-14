package com.github.wlaforest.ksql.udf;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

abstract class GeometryBase
{
    private GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

    protected Geometry getGeometryWKT(String WKT) throws GeometryParseException {

        WKTReader reader = new WKTReader(geometryFactory);
        Polygon polygon = null;
        try {
            polygon = (Polygon) reader.read(WKT);
        } catch (ParseException e) {
            throw new GeometryParseException("Bad WKT Encoding " + WKT, e);
        }
        if (polygon == null)
            throw new GeometryParseException("Bad WKT Encoding " + WKT);
        return polygon;
    }

    protected Geometry getPoint(double x, double y)
    {
        Coordinate coord = new Coordinate(x, y);
        return geometryFactory.createPoint(coord);
    }

}
