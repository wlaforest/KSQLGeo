package com.github.wlaforest.ksql

import ch.hsr.geohash.BoundingBox
import ch.hsr.geohash.GeoHash
import ch.hsr.geohash.WGS84Point
import org.locationtech.jts.geom.*
import org.locationtech.jts.geom.impl.CoordinateArraySequence

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
    val points = kotlin.arrayOf(Coordinate(this.minLat, this.maxLon),
        Coordinate(this.maxLat, this.maxLon),
        Coordinate(this.maxLat, this.minLon),
        Coordinate(this.minLat, this.minLon),
        Coordinate(this.minLat, this.maxLon))
    val polygon = GeometryFactory().createPolygon(LinearRing(CoordinateArraySequence(points), GeometryFactory()), null)

    return polygon
}

fun WGS84Point.toJTSPoint(): Point {
    return GeometryFactory().createPoint(Coordinate(this.latitude, this.longitude))
}

fun geohashPoly(polygon: org.locationtech.jts.geom.Polygon, precision: Int = 7, mode: String = "center", threshold: Double? = null): List<String> {

    val b = polygon.envelopeBox()

    val hashNorthEast = GeoHash.withCharacterPrecision(b[1].x, b[1].y, precision)
    val hashSouthWest = GeoHash.withCharacterPrecision(b[3].x, b[3].y, precision)


    val perLat = hashNorthEast.boundingBox.latitudeSize
    val perLng = hashNorthEast.boundingBox.longitudeSize

    val latStep = Math.floor((hashNorthEast.boundingBoxCenterPoint.latitude - hashSouthWest.boundingBoxCenterPoint.latitude) / perLat).toInt() + 1
    val lngStep = Math.floor((hashNorthEast.boundingBoxCenterPoint.longitude - hashSouthWest.boundingBoxCenterPoint.longitude) / perLng).toInt() + 1

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