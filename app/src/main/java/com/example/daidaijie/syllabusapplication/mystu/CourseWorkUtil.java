package com.example.daidaijie.syllabusapplication.mystu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseWorkUtil {

    private static List<Map<String, String>> Wlist = new ArrayList<Map<String, String>>();

    //private static List<Map<String, String>> Dlist = new ArrayList<Map<String, String>>();

    private static Map<String, String> detailsMap  = new HashMap<String, String>();


    private static int worklistNum;

    public static int getWorklistNum() {
        return worklistNum;
    }


    public static void toParseJson_Title(String json) {

        try {

            JSONObject worklist_jsonobject = new JSONObject(json);
            worklistNum = worklist_jsonobject.getInt("num");
            JSONArray worklist_array = worklist_jsonobject.getJSONArray("assigns");

            /**不然反复点击的时候,Wlist缓存还在，会影响显示**/
            Wlist.clear();//注意 注意

            for (int i = 0; i < worklistNum; i++) {
                Map<String, String> map = new HashMap<String, String>();//list储存多个同名（同keyset）map必须注意的问题

                JSONObject thework = worklist_array.getJSONObject(i);
                String assignLink = thework.getString("assignLink");
                String assignLinkId = thework.getString("assignLinkId");
                String assignTitle = thework.getString("assignTitle");
                map.put("assignLink", assignLink);
                map.put("assignLinkId", assignLinkId);
                map.put("assignTitle", assignTitle);
                Wlist.add(map);
            }
            /**测试： 输出被加载好的Wlist**/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void toParseJson_Content(String json) {

        try {
            /**
             * 不清空 调用一次 装入一次！装到一个List中
             * */
            //Dlist.clear();

            detailsMap.clear(); //注意 注意

            JSONObject workdetails_jsonobject = new JSONObject(json);


            /**
             * 必须进行相应的if判断 否则会导致一个为null 全为null的情况
             * */
            if(!workdetails_jsonobject.isNull("submitStatus")) {
                String submitStatus = workdetails_jsonobject.getString("submitStatus");
                detailsMap.put("submitStatus", submitStatus);
            }else {
                detailsMap.put("submitStatus", "无");
            }

            if(!workdetails_jsonobject.isNull("beginTime")) {
                String beginTime = workdetails_jsonobject.getString("beginTime");
                detailsMap.put("beginTime", beginTime);
            } else {
                detailsMap.put("beginTime", "无");
            }

            if(!workdetails_jsonobject.isNull("endTime")) {
                String endTime = workdetails_jsonobject.getString("endTime");
                detailsMap.put("endTime", endTime);
            }else {
                detailsMap.put("endTime", "无");
            }

            if(!workdetails_jsonobject.isNull("assign")) {
                String assign = workdetails_jsonobject.getString("assign");
                detailsMap.put("assign", assign);
            }else {
                detailsMap.put("assign", "无");
            }

            if(!workdetails_jsonobject.isNull("gradeStatus")){
                String gradeStatus = workdetails_jsonobject.getString("gradeStatus");
                detailsMap.put("gradeStatus", gradeStatus);
            }else {
                detailsMap.put("gradeStatus", "无");
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
    private static String[] contentArray = new String[]{

            "数据预处理作业要求\n\n" +
                    "一、计算\n\n" +
                    "1、  将数据中‘？’标志的缺失数据补齐。\n" +
                    "2、  计算“观测窗口总基本积分”，“第一年总票价”的四分位数，并做出盒图\n" +
                    "3、  做出3个属性的直方图、分位数图、散布图\n" +
                    "4、  按各个属性对数据进行最小-最大规范化\n\n" +
                    "二、写出报告对以上操作结果作出解释\n\n",

            "购物篮数据关联分析作业要求  \n\n" +
                    "一、 数据整理：\n" +
                    "将数据中的birth_year、weight、height的数据离散化\n\n" +
                    "二、 编写Apriori算法程序，平台自选。\n\n" +
                    "三、 用Apriori 算法找出频繁项集，支持度和置信度根据情况自行设定。\n\n" +
                    "四、 找出强关联规则以及相应的支持度和置信度\n\n" +
                    "五、 完成挖掘报告\n\n",

            "由于Mystu系统问题，很多同学提交的作业1不成功，现请所有同学在本周日（11月18日）前再次提交。\n\n"
    };
     */



    public static String getContent(int position) {

        String str ="~轻触获得作业详情！~";
        return str;
    }


    public static String getName(int position) {
        return Wlist.get(position % worklistNum).get("assignTitle");
    }


    public static String getAssignLinkId(int position) {
        return Wlist.get(position).get("assignLinkId");
    }

    public static String getDialogMessage() {

        String str="";

        str ="提交状态  ："+detailsMap.get("submitStatus")+"\n\n"+
                "开始时间  ："+detailsMap.get("beginTime")+"\n\n"+
                "结束时间  ："+detailsMap.get("endTime")+"\n\n\n"+
                "作业内容  ："+detailsMap.get("assign")+"\n\n"+
                "评分状态  ："+detailsMap.get("gradeStatus")+"\n"
            ;

         return str;
    }
}