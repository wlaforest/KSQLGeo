package com.github.wlaforest.ksql.udf;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.context.SpatialContextFactory;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Rectangle;
import org.locationtech.spatial4j.shape.Shape;
import org.locationtech.spatial4j.shape.ShapeFactory;
import org.locationtech.spatial4j.shape.SpatialRelation;

import java.util.List;

public class Spatial4JHelper {

    // This will be lazily created when need a factory to support operations that are only available in 2D
    private SpatialContextFactory scfEuclidean;
    private SpatialContext scEuclidean;
    private Spatial4jStringDeserializer deserializer;
    private ShapeFactory shapeFactory;

    public Spatial4JHelper()
    {
        this.scfEuclidean = new JtsSpatialContextFactory();
        this.scEuclidean = scfEuclidean.newSpatialContext();
        this.deserializer = new Spatial4jStringDeserializer(this.scfEuclidean,this.scEuclidean);
        this.shapeFactory = scfEuclidean.makeShapeFactory(scEuclidean);
    }

    /**
     * Checks to see if the two shapes encoded in a string intersect.
     * This supports multiple string encodings like WKT and GeoJSON.  In theory any format supported by the underlying
     * Spatial4j library.  Please check the Spatial4j documentation to see what shapes are supported in what models.
     *
     * @param geo1 geo shape one
     * @param geo2 geo shape two
     * @return does it intersect
     */
    public boolean intersect(String geo1, String geo2) throws GeometryParseException {
        Shape geoShape1;
        Shape geoShape2;

        Spatial4jStringDeserializer stringDeserializer = getDeserializer();

        geoShape1 = stringDeserializer.getSpatial4JShapeFromString(geo1);
        geoShape2 = stringDeserializer.getSpatial4JShapeFromString(geo2);

        SpatialRelation relation = geoShape1.relate(geoShape2);
        return relation.intersects();
    }

    /**
     * Checks to see if the geo1 contains geo2 where the geos are shapes encoded in a string.
     * This supports multiple string encodings like WKT and GeoJSON.  In theory any format supported by the underlying
     * Spatial4j library.  Please check the Spatial4j documentation to see what shapes are supported in what models.
     *
     * @param geo1 geo shape one
     * @param geo2 geo shape two
     * @return Does geo1 contain geo2
     */
    public boolean contained(String geo1, String geo2) throws GeometryParseException {
        Shape geoShape1;
        Shape geoShape2;

        Spatial4jStringDeserializer stringDeserializer = getDeserializer();

        geoShape1 = stringDeserializer.getSpatial4JShapeFromString(geo1);
        geoShape2 = stringDeserializer.getSpatial4JShapeFromString(geo2);

        SpatialRelation relation = geoShape1.relate(geoShape2);
        return relation == SpatialRelation.CONTAINS;
    }

    /**
     * Checks to see if the geo1 contains the point specified by lat, lon. geo1 is a shape encoded in a string.
     * This supports multiple string encodings like WKT and GeoJSON.  In theory any format supported by the underlying
     * Spatial4j library.  Please check the Spatial4j documentation to see what shapes are supported in what models.
     *
     * @param geo1 geo shape one
     * @param lat latitude for point to check contain inside of geo1
     * @param lon longitude for point to check contain inside of geo1
     * @return Does geo1 contain geo2
     */
    public boolean contained(String geo1, double lat, double lon) throws GeometryParseException {
        Shape geoShape1;
        Shape geoShape2;

        Spatial4jStringDeserializer stringDeserializer = getDeserializer();

        geoShape1 = stringDeserializer.getSpatial4JShapeFromString(geo1);
        geoShape2 = scfEuclidean.makeShapeFactory(scEuclidean).pointXY(lon,lat);

        SpatialRelation relation = geoShape1.relate(geoShape2);
        return relation == SpatialRelation.CONTAINS;
    }

    /**
     * Takes a shape provided by geo1 and return the set of geohashes at the specificed level of precision to cover it.
     * This supports multiple string encodings like WKT and GeoJSON.  In theory any format supported by the underlying
     * Spatial4j library.  Please check the Spatial4j documentation to see what shapes are supported in what models.
     *
     * @param geo1 geo shape
     * @param precision from 1 to 12
     * @return Does geo1 contain geo2
     */
    public List<String> coveringGeoHashes(String geo1, int precision) throws GeometryParseException {
        Shape geoShape1;
        Spatial4jStringDeserializer stringDeserializer = getDeserializer();
        geoShape1 = stringDeserializer.getSpatial4JShapeFromString(geo1);

        GeometryFactory gf = new GeometryFactory();
        Coordinate[] bbCords = new Coordinate[5];

        Rectangle bb = geoShape1.getBoundingBox();
        bbCords[0] = new Coordinate(bb.getMinX(), bb.getMinY());
        bbCords[1] = new Coordinate(bb.getMinX(), bb.getMaxY());
        bbCords[2] = new Coordinate(bb.getMaxX(), bb.getMaxY());
        bbCords[3] = new Coordinate(bb.getMaxX(), bb.getMinY());
        bbCords[4] = new Coordinate(bb.getMinX(), bb.getMinY());

        Polygon p = gf.createPolygon(bbCords);
        return com.github.wlaforest.ksql.GeoHashKeysKt.geohashPoly(p,precision, "intersect", (double) 0);
    }

    public ShapeFactory getShapeFactory()
    {
        return shapeFactory;
    }

    public Spatial4jStringDeserializer getDeserializer()
    {
        return deserializer;
    }
}
