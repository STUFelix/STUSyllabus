package com.example.daidaijie.syllabusapplication.mystu;

import com.example.daidaijie.syllabusapplication.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseWareDataWork {

    private List<CourseWareFileData> fileDataList;
    private List<CourseWareFolderData> folderDataList;
    private List<Map<String, Object>> dataList = new ArrayList<>();

    private static int wareNum;
    public static int getWareNum() {
        return wareNum;
    }


    public void setData(String jsonData) {
        Gson gson = new Gson();
        CourseWarePerson person = gson.fromJson(jsonData, CourseWarePerson.class);
        fileDataList = person.getFiles();
        folderDataList = person.getFolders();
    }

    public List<Map<String, Object>> getData() {
        try {
            JSONArray fileArray = new JSONArray(fileDataList.toString());
            wareNum = fileArray.length();

            for (int i = 0; i < wareNum; i++) {
                Map<String, Object> map = new HashMap<>();
                JSONObject jsonObject = fileArray.getJSONObject(i);
                if (jsonObject != null) {
                    String fileName = jsonObject.optString("fileName");
                    //String fileId = jsonObject.optString("fileId");
                    String fileLink = jsonObject.optString("fileLink");
                    String fileKind = jsonObject.optString("fileKind");
                    map.put("fileName", fileName);
                    //map.put("fileId", fileId);
                    map.put("fileLink", fileLink);
//                    Log.d("fileKind--------",fileKind);
                    switch (fileKind) {
                        case "document":
                            map.put("fileImg", R.drawable.mystu_courseware_wordx);
                            break;
                        case "pdf":
                            map.put("fileImg",R.drawable.mystu_courseware_pdfx);
                            break;
                        case "powerpoint":
                            map.put("fileImg", R.drawable.mystu_courseware_pptx);
                            break;
                        case "zip":
                            map.put("fileImg", R.drawable.mystu_courseware_zipx);
                            break;
                        case "video":
                            map.put("fileImg", R.drawable.mystu_courseware_videox);
                            break;
                        case "excel":
                            map.put("fileImg", R.drawable.mystu_courseware_excel);
                            break;
                        default:
                            map.put("fileImg", R.drawable.mystu_courseware_allx);
                    }
                    dataList.add(map);
                }
            }

            JSONArray folderArray = new JSONArray(folderDataList.toString());
            for (int j = 0; j < folderArray.length(); j++) {
                JSONObject jsonObject2 = folderArray.getJSONObject(j);
                Map<String, Object> map = new HashMap<>();
                if (jsonObject2 != null) {
                    String folderName = jsonObject2.optString("folderName");
                    String folderLinkId = jsonObject2.optString("folderLinkId");
                    String folderLink = jsonObject2.optString("folderLink");
                    map.put("fileName", folderName);
                    map.put("fileId", folderLinkId);
                    map.put("fileLink", folderLink);
                    map.put("fileKind", "folder");
                    map.put("fileImg",  R.drawable.mystu_courseware_allx);
                }
                dataList.add(map);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
