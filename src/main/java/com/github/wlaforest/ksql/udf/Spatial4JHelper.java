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

    // This will be created for cylandrical or spherical operations
    private SpatialContextFactory scf;
    private SpatialContext sc;

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
     * @param euclidean Is this euclidean space?  If not it will try and support cylander or spherical
     * @return does it intersect
     */
    public boolean intersect(String geo1, String geo2, boolean euclidean) throws GeometryParseException {
        Shape geoShape1;
        Shape geoShape2;

        Spatial4jStringDeserializer stringDeserializer = getDeserializer(euclidean);

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
     * @param euclidean Is this euclidean space?  If not it will try and support cylander or spherical
     * @return Does geo1 contain geo2
     */
    public boolean contained(String geo1, String geo2, boolean euclidean) throws GeometryParseException {
        Shape geoShape1;
        Shape geoShape2;

        Spatial4jStringDeserializer stringDeserializer = getDeserializer(euclidean);

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
     * @param euclidean Is this euclidean space?  If not it will try and support cylander or spherical
     * @return Does geo1 contain geo2
     */
    public boolean contained(String geo1, double lat, double lon, boolean euclidean) throws GeometryParseException {
        Shape geoShape1;
        Shape geoShape2;

        Spatial4jStringDeserializer stringDeserializer = getDeserializer(euclidean);

        geoShape1 = stringDeserializer.getSpatial4JShapeFromString(geo1);

        if (euclidean)
        {
            geoShape2 = scfEuclidean.makeShapeFactory(scEuclidean).pointXY(lon,lat);
        }
        else
        {
            geoShape2 = scfEuclidean.makeShapeFactory(sc).pointXY(lon, lat);
        }


        SpatialRelation relation = geoShape1.relate(geoShape2);
        return relation == SpatialRelation.CONTAINS;
    }


    private Spatial4jStringDeserializer getDeserializer(boolean euclidean)
    {
        // Lazy creation of factory and context if necessary
        if (euclidean == true)
        {
            if (scfEuclidean == null) {
                this.scfEuclidean = new JtsSpatialContextFactory();
                this.scEuclidean = scfEuclidean.newSpatialContext();
            }
            return new Spatial4jStringDeserializer(scfEuclidean,scEuclidean);
        }
        else
        {
            if (scf == null) {
                this.scf = new SpatialContextFactory();
                this.sc = scf.newSpatialContext();
            }
            return new Spatial4jStringDeserializer(scf, sc);
        }
    }
}
