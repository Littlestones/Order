package com.example.gdong.myapplication.util;

import android.net.Uri;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.ProgressCallback;
import cn.bmob.v3.listener.UploadFileListener;

import static cn.bmob.v3.Bmob.getApplicationContext;
import static com.example.gdong.myapplication.util.ContentUriUtil.getPath;

/**
 * Created by Gdong on 2017/10/20.
 */

public class network {
    public static String uploadimg(Uri imgurl){
        boolean flag;
        final BmobFile bmobFile = new BmobFile(new File(getPath(getApplicationContext(), imgurl)));
         bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "图片上传成功", Toast.LENGTH_SHORT);
                        } else {
                            Toast.makeText(getApplicationContext(), "图片上传失败", Toast.LENGTH_SHORT);
                        }
                    }
                });
        if(bmobFile.getFileUrl()==null){
            return null;
        }else {
            return bmobFile.getFileUrl().toString() ;

        }
    }
}
