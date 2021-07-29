package com.github.wlaforest.ksql.udf;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.context.SpatialContextFactory;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;
import org.locationtech.spatial4j.shape.SpatialRelation;

public class Spatial4JHelper {

    // This will be lazily created when need a factory to support operations that are only available in 2D
    private SpatialContextFactory scfEuclidean;
    private SpatialContext scEuclidean;

    public void Spatial4JEngine()
    {
        // nothing yet
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

    Spatial4jStringDeserializer getDeserializer()
    {
        // Lazy creation of factory and context if necessary
        if (scfEuclidean == null) {
            this.scfEuclidean = new JtsSpatialContextFactory();
            this.scEuclidean = scfEuclidean.newSpatialContext();
        }
        return new Spatial4jStringDeserializer(scfEuclidean,scEuclidean);
    }
}
