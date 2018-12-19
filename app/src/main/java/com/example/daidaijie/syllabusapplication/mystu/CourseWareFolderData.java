package com.example.daidaijie.syllabusapplication.mystu;

/**
 * Created by Administratior on 2018/12/3.
 */

public class CourseWareFolderData {
    private String folderLinkId;
    private String folderLink;
    private String folderName;

    CourseWareFolderData(String folderName, String folderLinkId, String folderLink){
        this.folderLinkId =folderLinkId;
        this.folderLink = folderLink;
        this.folderName = folderName;
    }

    String getfolderLinkId(){
        return folderLinkId;
    }
    String getfolderLink(){
        return folderLink;
    }
    String getfolderName(){
        return folderName;
    }

    @Override
    public String toString(){
        return "\n{\n" +
                "\"folderLinkId\":\""+folderLinkId+"\","+
                "\n\"folderName\":\""+folderName+"\","+
                "\n\"folderLink\":\""+folderLink+"\""+
                "\n}";
    }
}
