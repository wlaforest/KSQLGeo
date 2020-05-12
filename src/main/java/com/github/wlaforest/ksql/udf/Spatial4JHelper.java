package com.github.wlaforest.ksql.udf;

import org.locationtech.spatial4j.context.SpatialContextFactory;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;
import org.locationtech.spatial4j.shape.SpatialRelation;

public class Spatial4JHelper {

    // This will be lazily created when need a factory to support operations that are only available in 2D
    private SpatialContextFactory scf2d;

    // This will be created for cylandrical or spherical operations
    private SpatialContextFactory scf;

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

    private Spatial4jStringDeserializer getDeserializer(boolean euclidean) {
        Spatial4jStringDeserializer stringDeserializer;

        // Lazy creation of factory if necessary
        if (euclidean == true)
        {
            if (scf2d == null)
                scf2d = new JtsSpatialContextFactory();

            stringDeserializer = new Spatial4jStringDeserializer(scf2d);
        }
        else
        {
            if (scf == null)
                scf = new SpatialContextFactory();

            stringDeserializer = new Spatial4jStringDeserializer(scf);
        }
        return stringDeserializer;
    }

}
