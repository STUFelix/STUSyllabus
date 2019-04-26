package com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.local;

import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.todo.dataTemp.EvalBeanDao;

import java.util.List;

/**
 * Created by 16zhchen on 2018/10/14.
 */

public class EvalDataManager {
    private static EvalDataManager INSTANCE = null;
    private EvalBeanDao evalBeanDao;
    private App base;

    private EvalDataManager(){
        base = App.getInstances();
        evalBeanDao = base.getDaoSession().getEvalBeanDao();

    }

    public static EvalDataManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new EvalDataManager();
        }
        return INSTANCE;
    }
    public EvalBean getEvalByclassID(int classID){
        return evalBeanDao.queryBuilder()
                .where(EvalBeanDao.Properties.Class_id.eq(classID))
                .unique();
    }
    public void inseartAll(List<EvalBean> list){
        evalBeanDao.insertInTx(list);
    }
    public void deleteAll(){
        evalBeanDao.deleteAll();
    }
    public long addEval(EvalBean eval){
        return evalBeanDao.insert(eval);
    }

    public int deleteByCid(int cid){
        EvalBean temp = evalBeanDao.queryBuilder()
                .where(EvalBeanDao.Properties.Cid.eq(cid))
                .unique();
        if(temp != null){
            evalBeanDao.deleteByKey(temp.getId());
            return 1;
        }
        return 0;
    }
    public int updateEval(EvalBean eval){
        EvalBean dbEval = evalBeanDao.queryBuilder()
                .where(EvalBeanDao.Properties.Id.eq(eval.getId()))
                .unique();
        if(dbEval != null){
            dbEval = eval;
            evalBeanDao.update(eval);
            return 1;
        }
        return 0;
    }
    public long dbCount(){
        return evalBeanDao.count();
    }
}
