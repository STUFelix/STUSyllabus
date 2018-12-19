package com.example.daidaijie.syllabusapplication.mystu;

public class CourseWareFileData {
    private String fileId;
    private String fileLink;
    private String fileName;
    private String fileKind;

    CourseWareFileData(String fileName, String fileKind, String fileId, String fileLink){
        this.fileId =fileId;
        this.fileKind = fileKind;
        this.fileLink = fileLink;
        this.fileName = fileName;
    }

    String getFileId(){
        return fileId;
    }
    String getFileLink(){
        return fileLink;
    }
    String getFileName(){
        return fileName;
    }
    String getFileKind(){
        return fileKind;
    }
    @Override
    public String toString(){
        return "\n{\n" +
                "\"fileId\":\""+fileId+"\","+
                "\n\"fileKind\":\""+fileKind+"\","+
                "\n\"fileName\":\""+fileName+"\","+
                "\n\"fileLink\":\""+fileLink+"\""+
                "\n}";
    }


}
