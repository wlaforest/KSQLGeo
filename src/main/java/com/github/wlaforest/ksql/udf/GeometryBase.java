package com.github.wlaforest.ksql.udf;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.io.geojson.GeoJsonReader;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

abstract class GeometryBase
{
    private GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

    protected Geometry getGeometryFromString(String stringEncoding) throws GeometryParseException {

        WKTReader reader = new WKTReader(geometryFactory);

        Geometry geometry;
        try {
            geometry = reader.read(stringEncoding);

            if (geometry == null)
            {
                GeoJsonReader gjr = new GeoJsonReader();
                geometry = gjr.read(stringEncoding);
            }

            } catch (ParseException e) {
            GeoJsonReader gjr = new GeoJsonReader();
            try {
                geometry = gjr.read(stringEncoding);
            } catch (ParseException e1) {
                throw new GeometryParseException("Bad string encoding: " + stringEncoding, e);
            }
        }

        if (geometry == null)
        {
            throw new GeometryParseException("Bad string encoding: " + stringEncoding);
        }
        return geometry;
    }

    protected Geometry getPoint(double x, double y)
    {
        Coordinate coord = new Coordinate(x, y);
        return geometryFactory.createPoint(coord);
    }

}
