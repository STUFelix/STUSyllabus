package com.example.daidaijie.syllabusapplication.mystu;

import java.util.List;

/**
 * Created by Administratior on 2018/12/3.
 */

public class CourseWarePerson {

    private List<CourseWareFileData> files;
    private List<CourseWareFolderData> folders;

    CourseWarePerson(List<CourseWareFileData> files, List<CourseWareFolderData> folders){
        this.files = files;
        this.folders = folders;
    }

    List<CourseWareFileData> getFiles(){
        return files;
    }

    List<CourseWareFolderData> getFolders(){
        return folders;
    }

    public String toString(){
        return  "files:" + files+
                "\nfolders:" + folders;
    }
}
