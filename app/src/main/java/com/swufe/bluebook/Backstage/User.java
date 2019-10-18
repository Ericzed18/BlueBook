package com.swufe.bluebook.Backstage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class User extends BmobUser {

    private String headPortrait;//用户头像
    private String nickname;
    private String city;

    public String getOwnCity() {
        return city;
    }

    public void setOwnCity(String city) {
        this.city = city;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    //获取已经用户已完成的事件
    public void getCompletedEvent(SystemTask systemTask, final com.swufe.bluebook.Backstage.QueryListener callback) {
        BmobQuery<Event> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", this.getObjectId());
        query.addWhereEqualTo("taskId", systemTask.getObjectId());
        query.findObjects(new FindListener<Event>() {
            @Override
            public void done(List<Event> list, BmobException e) {
                if (e == null) {
                    //已完成任务
                    callback.onSuccess(list);
                } else {
                    callback.onFailure(e);
                }
            }
        });
    }

    //用户开始完成这个任务
    public void getTaskCompleted(SystemTask systemTask, List<String> picPaths, String description) {
        Event event = new Event();
        List<BmobFile> pics = new ArrayList<>();
        for (String picPath :
                picPaths) {
            pics.add(new BmobFile(new File(picPath)));
        }
        event.setPhotos(pics);
        event.setDescription(description);
        event.save(new SaveListener<String>() {
            @Override
            public void done(String eventId, BmobException e) {

            }
        });

    }


}
