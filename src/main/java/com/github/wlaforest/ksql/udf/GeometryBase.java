package com.github.wlaforest.ksql.udf;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.io.geojson.GeoJsonReader;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

abstract class GeometryBase
{
    protected static Spatial4JHelper spatial4JHelper = new Spatial4JHelper();

    private GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

    protected Geometry getPoint(double x, double y)
    {
        Coordinate coord = new Coordinate(x, y);
        return geometryFactory.createPoint(coord);
    }

}
