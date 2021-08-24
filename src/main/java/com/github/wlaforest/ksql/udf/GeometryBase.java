package com.github.wlaforest.ksql.udf;

import com.github.wlaforest.geo.Spatial4JHelper;
import org.apache.kafka.common.Configurable;

import java.util.Map;

abstract class GeometryBase implements Configurable
{
    private final Spatial4JHelper spatial4JHelper = new Spatial4JHelper();

    public Spatial4JHelper getSpatial4JHelper()
    {
        return spatial4JHelper;
    }

    @Override
    public void configure(final Map<String, ?> map) {
        spatial4JHelper.configure(map);
    }
}
