package com.gamma.tools;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Date;

public class VelocityTest {

    public static void main(String[] args) throws Exception {

        // 初始化并取得Velocity引擎
        VelocityEngine ve = new VelocityEngine();
        ve.init();

        // 取得velocity的模版内容, 模板内容来自字符传

        String content = "";
        content += "Welcome  $name  to Javayou.com! ";
        content += " today is  $date.";
        // 取得velocity的上下文context
        VelocityContext context = new VelocityContext();
        // 把数据填入上下文
        context.put("name", "javaboy2012");

        context.put("date", (new Date()).toString());

        // 输出流
        StringWriter writer = new StringWriter();
        // 转换输出
        ve.evaluate(context, writer, "", content); // 关键方法
        System.out.println(writer.toString());

    }


}
