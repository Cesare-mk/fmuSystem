package com.fmu.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import no.ntnu.ihb.fmi4j.Fmi4jVariableUtils;
import no.ntnu.ihb.fmi4j.importer.fmi1.CoSimulationSlave;
import no.ntnu.ihb.fmi4j.importer.fmi1.Fmu;
import no.ntnu.ihb.fmi4j.modeldescription.variables.RealVariable;
import no.ntnu.ihb.fmi4j.Fmi4jVariableUtils;
import no.ntnu.ihb.fmi4j.importer.fmi1.CoSimulationSlave;
import no.ntnu.ihb.fmi4j.importer.fmi1.Fmu;
import no.ntnu.ihb.fmi4j.modeldescription.CoSimulationModelDescription;
import no.ntnu.ihb.fmi4j.modeldescription.util.FmiModelDescriptionUtil;
import no.ntnu.ihb.fmi4j.modeldescription.variables.RealVariable;
import no.ntnu.ihb.fmi4j.modeldescription.variables.TypedScalarVariable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 马蒙
 * @date 2024/4/23 13:59
 */

public class TestFmu {
    public static void main(String[] args) throws IOException {
        List<JSONObject> variableList = new ArrayList<>();
        Fmu fmu = Fmu.from(new File("D:\\JetBrains-work\\Java\\BouncingBall.fmu")); //URLs are also supported
        CoSimulationSlave slave = fmu.asCoSimulationFmu().newInstance();
        CoSimulationModelDescription modelDescriptionDes = slave.getModelDescription();
        System.out.println("模型名 " + modelDescriptionDes.getModelName());
        System.out.println("FMU版本 " + modelDescriptionDes.getFmiVersion());
        System.out.println("版本 " + modelDescriptionDes.getVersion());
        System.out.println("描述" + modelDescriptionDes.getDescription());
        System.out.println("作者" + modelDescriptionDes.getAuthor());
        System.out.println("单位" + modelDescriptionDes.getUnitDefinitions());
        for (TypedScalarVariable variable : modelDescriptionDes.getModelVariables()) {
            JSONObject variableObject = new JSONObject();
            variableObject.put("序号", variable.getValueReference());
            variableObject.put("变量名", variable.getName());
            variableObject.put("初始值", variable.getStart());
            variableObject.put("因果关系", variable.getCausality());
            variableObject.put("是否变量", variable.getVariability());
            variableObject.put("描述", variable.getDescription());
            variableList.add(variableObject);
            /*System.out.print("变量名 " + variable.getName() + "   ");
            System.out.print("初始值 ֵ" + variable.getStart() + "   ");
            //System.out.print("类型 " + variable.getDeclaredType() + "   ");
            System.out.print("因果关系 " + variable.getCausality() + "   ");
            System.out.print("是否变量 " + variable.getVariability() + "   ");
            System.out.println("类型2： " + variable.getType());
            System.out.print("描述 " + variable.getDescription() + "   ");
            System.out.println("值参照：" + variable.getValueReference());
            System.out.println("/n");*/
        }
        String jsonString = JSON.toJSONString(variableList);
        /*slave.simpleSetup();

        double t = 0;
        double stop = 10;
        double stepSize = 1.0 / 100;

        final RealVariable h = slave.getModelDescription()
                .getVariableByName("h").asRealVariable();

        final RealVariable v = slave.getModelDescription()
                .getVariableByName("v").asRealVariable();

        while (t <= stop) {
            if (!slave.doStep(t, stepSize)) {
                break;
            }
            double hValue = Fmi4jVariableUtils.read(h, slave).getValue();
            double vValue = Fmi4jVariableUtils.read(v, slave).getValue();

            System.out.println("h: " + hValue + ";  " + "v: " + vValue);
            t += stepSize;
        }
        slave.terminate(); //or close, try with resources is also supported*/
        fmu.close();
        System.out.println(jsonString);
    }
}

