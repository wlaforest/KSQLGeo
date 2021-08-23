package com.github.wlaforest.geo

import ch.hsr.geohash.BoundingBox
import ch.hsr.geohash.GeoHash
import ch.hsr.geohash.WGS84Point
import org.locationtech.jts.geom.*
import org.locationtech.jts.geom.impl.CoordinateArraySequence
import kotlin.math.floor

// This class has been adapted from https://github.com/xlvecle/geohash-poly which was written against the original JTS
// when it was a vividsolutions.com library.  I have simply just updated the classes for the new version under
// locationtech

fun Polygon.envelopeBox(): List<Coordinate> {
    /**
     * SouthEast -> NorthEast -> NorthWest -> SouthWest
     */
    val envelopeCoordinates = this.envelope.coordinates
    return envelopeCoordinates.slice(1..4)
}

fun BoundingBox.toJTSPolygon(): Polygon {
    /**
     * ch.hsr.GeoHash to JTS Polygon
     */
    val points = arrayOf(
        Coordinate(this.maxLon, this.minLat),
        Coordinate(this.maxLon, this.maxLat),
        Coordinate(this.minLon, this.maxLat),
        Coordinate(this.minLon, this.minLat),
        Coordinate(this.maxLon, this.minLat)
    )

    return GeometryFactory().createPolygon(LinearRing(CoordinateArraySequence(points), GeometryFactory()), null)
}

fun WGS84Point.toJTSPoint(): Point {
    return GeometryFactory().createPoint(Coordinate(this.longitude,this.latitude))
}

fun geohashPoly(polygon: Polygon, precision: Int = 7, mode: String = "center", threshold: Double? = null): List<String> {

    val b = polygon.envelopeBox()

    val hashNorthEast = GeoHash.withCharacterPrecision(b[1].y, b[1].x, precision)
    val hashSouthWest = GeoHash.withCharacterPrecision(b[3].y, b[3].x, precision)


    val perLat = hashNorthEast.boundingBox.latitudeSize
    val perLng = hashNorthEast.boundingBox.longitudeSize

    val latStep = floor((hashNorthEast.boundingBoxCenterPoint.latitude - hashSouthWest.boundingBoxCenterPoint.latitude) / perLat).toInt() + 1
    val lngStep = floor((hashNorthEast.boundingBoxCenterPoint.longitude - hashSouthWest.boundingBoxCenterPoint.longitude) / perLng).toInt() + 1

    val hashList = mutableListOf<String>()
    val p = mutableListOf<List<Double>>()

    var baseHash = hashSouthWest.southernNeighbour.southernNeighbour.westernNeighbour.westernNeighbour

    for (lat in 0..latStep + 1) {

        baseHash = baseHash.northernNeighbour

        var tmp = baseHash

        for (lng in 0..lngStep + 1) {

            val next = tmp.easternNeighbour

            p.add(listOf(next.boundingBoxCenterPoint.latitude, next.boundingBoxCenterPoint.longitude))
            if (mode == "center") {
                if (next.boundingBoxCenterPoint.toJTSPoint().within(polygon)) {
                    hashList.add(next.toBase32())
                }
            } else {
                val bbox = next.boundingBox
                if (mode == "inside") {
                    if (polygon.contains(bbox.toJTSPolygon())) {
                        hashList.add(next.toBase32())
                    }
                } else if (mode == "intersect") {
                    if (threshold != null) {
                        val intersectArea = polygon.intersection(bbox.toJTSPolygon()).area
                        if ((intersectArea / bbox.toJTSPolygon().area) > threshold) {
                            hashList.add(next.toBase32())
                        }
                    } else {
                        if (polygon.intersects(bbox.toJTSPolygon())) {
                            hashList.add(next.toBase32())
                        }
                    }
                }
            }
            tmp = next
        }
    }
    return hashList
}