package com.sy.notifyserver.util;

import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.shape.Rectangle;

/**
 *
 * @author wangxiao
 * @since 1.1
 */
public class RectangleUtil {

    private static SpatialContext spatialContext = SpatialContext.GEO;

    /**
     *  获取坐标点固定范围的外界正方形
     * @param distance 举例 km
     * @param userLng 经度
     * @param userLat 维度
     * @return Rectangle
     */
    public static Rectangle getRectangle(double distance, double userLng, double userLat) {
        return spatialContext.getDistCalc()
                .calcBoxByDistFromPt(spatialContext.makePoint(userLng, userLat),
                        distance * DistanceUtils.KM_TO_DEG, spatialContext, null);
    }

    /***
     * 球面中，两点间的距离
     * @param longitude 经度1
     * @param latitude  纬度1
     * @param userLng   经度2
     * @param userLat   纬度2
     * @return 返回距离，单位km
     */
    private double getDistance(Double longitude, Double latitude, double userLng, double userLat) {
        return spatialContext.calcDistance(spatialContext.makePoint(userLng, userLat),
                spatialContext.makePoint(longitude, latitude)) * DistanceUtils.DEG_TO_KM;
    }
}
