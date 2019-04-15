package me.guoxin.manager.utils;

import me.guoxin.pojo.GfsAddress;
import me.guoxin.pojo.GfsStore;

import java.util.List;

public class CommonUtils {
    private static final double EARTH_RADIUS = 6378137;//赤道半径

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * @param lon1
     * @param lat1
     * @param lon2
     * @param lat2
     * @return 两点距离
     */
    public static double GetDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s;//单位米
    }

    public static GfsStore getCloseStore(List<GfsStore> stores, GfsAddress address) {
        double close = Double.MAX_VALUE;
        GfsStore result = stores.get(0);
        double lat = address.getLatitude();
        double lon = address.getLongitude();
        for (GfsStore store : stores) {
            double lat1 = store.getLatitude();
            double lon1 = store.getLongitude();
            double distance = CommonUtils.GetDistance(lon, lat, lon1, lat1);
            if (close > distance) {
                close = distance;
                result = store;
            }
        }
        return result;
    }
}
