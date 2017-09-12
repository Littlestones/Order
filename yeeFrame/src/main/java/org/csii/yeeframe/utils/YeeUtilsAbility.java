package org.csii.yeeframe.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import org.csii.yeeframe.YeeHttp;
import org.csii.yeeframe.http.HttpCallBack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class YeeUtilsAbility {

	/**
	 * 获取视频缩略图
	 * @param filePath 视频文件存放路径
	 * @return
	 */
	public static Bitmap getVideoThumbnail(String filePath) {  
        Bitmap bitmap = null;  
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();  
        try {  
            retriever.setDataSource(filePath);  
            bitmap = retriever.getFrameAtTime();  
        }catch(IllegalArgumentException e) {  
            e.printStackTrace();  
        }catch (RuntimeException e) {  
            e.printStackTrace();  
        }finally {  
            try {  
                retriever.release();  
            }   
            catch (RuntimeException e) {  
                e.printStackTrace();  
            }  
        }  
        return bitmap;  
    }  
	
	/**
	 * 播放视频
	 */
	public static void videoPlay(Context ctx, VideoView view, String videoPath){
		view.setVisibility(View.VISIBLE);
		Uri uri = Uri.parse(videoPath); 
		view.setMediaController(new MediaController(ctx)); 
		view.setVideoURI(uri); 
		view.start(); 
		view.requestFocus();
	}
	
	/**  
	 * 拍摄视频  
	 */    
	public static void videoTake(Activity activity, long limitTime, long limitSize) {    
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);    
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);   
        //限制时长
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, limitTime);
        //限制大小
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, limitSize);
        activity.startActivityForResult(intent, YeeCode.RQCODE_TAKE_PHOTO);    
	} 
	
	/**
	 * 录音
	 * @param voicePath
	 * @param time
	 * @return MediaRecorder 停止录音时需要手动停止recorder.release()、recorder.stop()
	 */
	public static MediaRecorder voiceTake(String voicePath, final int time) {    
		final MediaRecorder recorder = new MediaRecorder();  
		 recorder.setAudioSource(MediaRecorder.AudioSource.MIC);  
		 recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);  
		 recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);  
		 recorder.setOutputFile(voicePath);  
		 try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		recorder.start();
		return recorder;
	}
	
	public static void scanVersion(final Context ctx){
		Builder dialog = new AlertDialog.Builder(ctx);
		dialog.setMessage("是否更新?");
		dialog.setCancelable(false);
		dialog.show();
		dialog.setNegativeButton("更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				ProgressDialog progress=new ProgressDialog(ctx);  
				progress.setIcon(android.R.drawable.sym_def_app_icon);  
				progress.setTitle("应用更新");  
				progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
				progress.setIndeterminate(false);  
				progress.setMessage("更新说明：\n1.系统全局UI调整，界面操作更加顺畅平滑\n2.门户界面新增软读卡案例入口，演示卡识别\n3.完善开卡流程\n4.外设适配升级，全面支持各厂商外接设备");  
				progress.setMax(100);  
				progress.show();
				new YeeHttp().download("http://192.168.1.118:3003/csii.apk", YeeUtilsFile.getSDCardPath() + "/csii.apk", new HttpCallBack() {
					@Override
		            public void onSuccess(String f) {
		                super.onSuccess(f);
		                YeeLoger.debug("success");
//		                File file = new File(YeeUtilsFile.getSDCardPath(), "csii.apk");
//						YeeUtilsSystem.installApk(ctx, file);
		            }

		            @Override
		            public void onFailure(int errorNo,String strMsg) {
		                super.onFailure(errorNo, strMsg);
		                YeeLoger.debug("onFailure");
		            }
		            
		            @Override
		            public void onLoading(long count, long current) {
		            	YeeLoger.debug(count + ":" + current);
		            }

				});
			}

		});
//		dialog.cancel.setVisibility(View.GONE);
		dialog.show();
	}
	
	/**  
     * 上传文件  
     * @param uploadFile  
     */    
    private void uploadFile(Context ctx, final File uploadFile, final String filename, final String ip, final int port) { 
    	final UploadLogService logService = new UploadLogService(ctx);
        new Thread(new Runnable() {             
            @Override    
            public void run() {    
                try {   
                    Log.i("length", uploadFile.length()+"");
                    String souceid = logService.getBindId(uploadFile);
                    String head = "Content-Length="+ uploadFile.length() + ";filename="+ filename + ";sourceid="+    
                        (souceid==null? "" : souceid)+"\r\n";    
                    Socket socket = new Socket(ip, port);    
                    OutputStream outStream = socket.getOutputStream();    
                    outStream.write(head.getBytes());    
                        
                    PushbackInputStream inStream = new PushbackInputStream(socket.getInputStream());        
                    String response = YeeUtilsStream.readLine(inStream);    
                    String[] items = response.split(";");    
                    String responseid = items[0].substring(items[0].indexOf("=")+1);    
                    String position = items[1].substring(items[1].indexOf("=")+1);    
                    if(souceid==null){//代表原来没有上传过此文件，往数据库添加一条绑定记录    
                        logService.save(responseid, uploadFile);
                    }
                    RandomAccessFile fileOutStream = new RandomAccessFile(uploadFile, "r");
                    fileOutStream.seek(Integer.valueOf(position));
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    int length = Integer.valueOf(position);    
                    while(((len = fileOutStream.read(buffer)) != -1)){   
                    	/*if(!start){
                    		Thread.interrupted();
                    	}*/
                        outStream.write(buffer, 0, len);
                        length += len;
                        Message msg = new Message();
                        msg.setData(new Bundle());
                        msg.getData().putLong("fullsize", uploadFile.length());
                        msg.getData().putLong("size", length);
                        msg.getData().putString("file", filename);
//                        handler.sendMessage(msg);
                    }    
                    fileOutStream.close();    
                    outStream.close();    
                    inStream.close();    
                    socket.close();    
                    if(length==uploadFile.length()) logService.delete(uploadFile);    
                } catch (Exception e) {    
                    e.printStackTrace();    
                }    
            }    
        }).start();    
    }

}
