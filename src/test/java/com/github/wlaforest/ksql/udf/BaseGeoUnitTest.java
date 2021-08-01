package com.github.wlaforest.ksql.udf;

import java.io.IOException;

import static org.junit.Assert.fail;

public abstract class BaseGeoUnitTest {
    private Class subclass;

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
