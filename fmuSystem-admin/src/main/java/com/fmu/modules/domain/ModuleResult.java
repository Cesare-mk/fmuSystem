package com.fmu.modules.domain;

import java.util.Arrays;

/**
 * @author 马蒙
 * @date 2024/4/29 15:00
 */
public class ModuleResult {
    /**
     * 小球模型的输出参数
     */
    private double[] h;
    private double[] v;

    public double[] getH() {
        return h;
    }

    public void setH(double[] h) {
        this.h = h;
    }

    public double[] getV() {
        return v;
    }

    public void setV(double[] v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "ModuleResult{" +
                "h=" + Arrays.toString(h) +
                ", v=" + Arrays.toString(v) +
                '}';
    }
}
