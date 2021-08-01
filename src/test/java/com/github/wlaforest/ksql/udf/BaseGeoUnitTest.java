package com.github.wlaforest.ksql.udf;

import static org.junit.Assert.fail;

@SuppressWarnings("rawtypes")
public abstract class BaseGeoUnitTest {
    private final Class subclass;

    BaseGeoUnitTest(Class classArg)
    {
        this.subclass = classArg;
    }

    protected String ts(String relativePath) {
        try {
            return UnitTestHelper.getResourceFileAsString(subclass, relativePath);
        }catch (Exception e)
        {
            fail("failed with exception " + e);
            return null;
        }
    }
}
