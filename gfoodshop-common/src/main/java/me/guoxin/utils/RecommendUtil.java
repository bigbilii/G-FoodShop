package me.guoxin.utils;

import me.guoxin.pojo.GfsOP;
import org.apache.commons.beanutils.ConvertUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecommendUtil {

    public static List<String> getAsMatrix(List<GfsOP> ops) {
        if (ops == null || ops.size() == 0) {
            return null;
        }
        List<String> matrix = new ArrayList<>();
        Long now = ops.get(0).getOrderId();
        StringBuffer nowS = new StringBuffer();
        //Set<String> nowS = new HashSet<>();
        for (GfsOP op : ops) {
            if (op.getOrderId().equals(now)) {
                nowS.append(op.getProductId()).append(";");
            }else {
                matrix.add(nowS.toString());
                nowS = new StringBuffer();
                now = op.getOrderId();
                nowS.append(op.getProductId()).append(";");
            }
        }
        return matrix;
    }
}
