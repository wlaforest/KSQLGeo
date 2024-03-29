# KSQLGeo
## Description
This project provides geosptial functions for ksqlDB.  ksqlDB a stream processing project that uses SQL like syntax
and runs on top of Apache Kafka (https://ksqldb.io/).  Currently, the functions in this library are underpinned by 
Spatial4j (https://github.com/locationtech/spatial4j). 

## Usage Notes

### Encodings

KSQLGeo uses the deserializtion from Spatial4J and currently support GeoJSON and WKT.  For more information about the
specifics of this see https://github.com/locationtech/spatial4j/blob/master/FORMATS.md

### Configuration

The fucntions in this library implement Configurable which means you can pass configuration paremeters in via environmental variables.  Currently the library exposes to parameters that directly map to Spatial4J's SpatialContextFactory (https://locationtech.github.io/spatial4j/apidocs/org/locationtech/spatial4j/context/SpatialContextFactory.html).
* `ksql.functions._global_.spatial4j.geo` - Default is `true` - Determines whether to use a model based on a sphere or flat plane (Euclidean)
* `ksql.functions._global_.spatial4j.normWrapLongitude` - Default is `false` - If true then normX(double) will wrap longitudes outside of the standard geodetic boundary into it. Example: 181 will become -179.

### Re-keying with UDTF

KSQLGeo has added a UDTF function called geo_covering_geohashes which will emit all the geohashes which cover a given shape.  This is important becasue when
you join two geospatial streams you need a common key for the on clause.  Since a shape can span multiple geohashes it is necessary to emit mutliple copies of the same record each with a seperate key to cover all possibled geohshes it falls into.  

## Building KSQLGeo

### Building the code

First obtain the code by cloning the Git repository:

    $ git clone https://github.com/wlaforest/KSQLGeo.git
    $ cd KSQLGeo

Then build the code using Maven:

    $ mvn clean package

Copy the uber jar into your KSQL server extension directory (specified by ksql.extension.dir in KSQL configuration
file ksql-server.properties). If this directory does not exist yet, simply create a new

    $ cp target/ksqlgeo-1.2.jar $KSQL-EXTENSION/

In order for KSQL to pick up the geospatial UDFS you need to restart ksql-server.  Once you have done that you can
demonstrate it working.  First insert some data. These queries will create a stream with records that have the
approximate school boundaries.

    CREATE STREAM SCHOOLS (NAME STRING, UNITY BOOLEAN, WKT STRING)
    WITH (KAFKA_TOPIC='SCHOOLS', VALUE_FORMAT='JSON', PARTITIONS=1, REPLICAS=1);

    INSERT INTO SCHOOLS VALUES ('Madison', true,
       'POLYGON((-77.27483600429103 38.89521905950339,-77.29131549647853 38.892012508280466,-77.31277316859767 38.89254694353762,-77.32066959193752 38.901097360742895,-77.31277316859767 38.90750949802689,-77.29938358119533 38.90697517537252,-77.30384677699611 38.91378748795597,-77.29818195155666 38.916325241169524,-77.30556339076564 38.92927972487108,-77.29869693568752 38.929413263931195,-77.29200214198634 38.93315225554382,-77.28307575038478 38.92741015163275,-77.2705444698672 38.92126692120997,-77.26608127406642 38.916191677473286,-77.2511467342715 38.91819510652208,-77.24634021571681 38.91191750646839,-77.27483600429103 38.89521905950339))');
    INSERT INTO SCHOOLS VALUES ('Oakton', true,
       'POLYGON((-77.3029731301166 38.871966195174494,-77.26846919334902 38.89027347822612,-77.29662165916933 38.88492877364056,-77.34846339500918 38.89575138309681,-77.33249888695254 38.8699614616396,-77.3029731301166 38.871966195174494))');

Now lets test to see which of the records intersects with this polygon.  set auto.offset.reset = Earliest to test
against the data you already added

    SELECT * from  SCHOOLS
    WHERE GEO_INTERSECTED(WKT, 'POLYGON((-77.25595325282619 38.90049514948347,-77.25389331630275 38.90503717840573,-77.289598882709 38.91171610485367,-77.2760376339297 38.889539649049034,-77.25595325282619 38.90049514948347))')
    EMIT CHANGES;

You should get the record for Madison

## Demonstration

For a demonsrattion see seperate github repo: https://github.com/wlaforest/KafkaGeoDemo
