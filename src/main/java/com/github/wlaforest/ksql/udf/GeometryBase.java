package com.github.wlaforest.ksql.udf;

import org.geotools.geometry.jts.JTSFactoryFinder;


abstract class GeometryBase
{
    private static Spatial4JHelper spatial4JHelper = new Spatial4JHelper();

    public static Spatial4JHelper getSpatial4JHelper() {
        return spatial4JHelper;
    }
}
