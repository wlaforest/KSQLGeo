package com.github.wlaforest.ksql.udf;

import com.github.wlaforest.geo.Spatial4JHelper;

abstract class GeometryBase
{
    private static final Spatial4JHelper spatial4JHelper = new Spatial4JHelper();

    public static Spatial4JHelper getSpatial4JHelper() {
        return spatial4JHelper;
    }
}
