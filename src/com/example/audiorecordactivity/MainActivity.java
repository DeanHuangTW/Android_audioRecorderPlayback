package com.example.audiorecordactivity;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	private final String extStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
	private String filePath = extStoragePath + "/myAudio.3gp";
	private Button mBtnRecord;
	private Button mBtnPlay;
	
	private Boolean isRecording = false;
	private Boolean isPlaying = false;
	
	private MediaRecorder recorder;
	private MediaPlayer player;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.v("Dean", "extStoragePath:" + extStoragePath);
		mBtnRecord = (Button) findViewById(R.id.button_record);
		mBtnPlay = (Button) findViewById(R.id.button_play);
		
		mBtnRecord.setOnClickListener(this);
		
		mBtnPlay.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v == mBtnRecord) {
			if (isRecording == false) { //開始錄音				
				isRecording = true;
				mBtnRecord.setText("停止錄音");
				try {
					recordAudio();
				} catch (IOException e) {
					Log.v("Dean", "Recorder IOException");
				}
			} else { //停止錄音				
				isRecording = false;
				mBtnRecord.setText("錄音");
				stopRecord();
			}
		} else if (v == mBtnPlay) {
			if (isPlaying == false) { //開始播放				
				isPlaying = true;
				mBtnPlay.setText("停止播放");
				playAudio();
			} else { //停止播放				
				isPlaying = false;
				mBtnPlay.setText("播放");
				stopAudio();
			}
		}
	}
	
	private void playAudio() {
		player = new MediaPlayer();
		try {
			Log.v("Dean", "player filepath:" + filePath);
			player.setDataSource(filePath);
			player.prepare();
			player.start();
		} catch (IOException e) {
			
		}
	}
	
	private void stopAudio() {
		//player.pause();
		player.stop();
	}

	private void recordAudio() throws IOException{
		Log.v("Dean", "recorder filepath:" + filePath);
		//檢查sdcard狀態
		String state = Environment.getExternalStorageState();
		if (!state.equals(Environment.MEDIA_MOUNTED)) {
			throw new IOException("SD card is not mounted");
		}
		
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(filePath);
		recorder.prepare();
		recorder.start();
	}
	
	private void stopRecord() {
		recorder.stop();
		recorder.release();
	}
}
