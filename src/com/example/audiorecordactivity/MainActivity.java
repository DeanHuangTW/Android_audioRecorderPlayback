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
			if (isRecording == false) { //�}�l����				
				isRecording = true;
				mBtnRecord.setText("�������");
				try {
					recordAudio();
				} catch (IOException e) {
					Log.v("Dean", "Recorder IOException");
				}
			} else { //�������				
				isRecording = false;
				mBtnRecord.setText("����");
				stopRecord();
			}
		} else if (v == mBtnPlay) {
			if (isPlaying == false) { //�}�l����				
				isPlaying = true;
				mBtnPlay.setText("�����");
				playAudio();
			} else { //�����				
				isPlaying = false;
				mBtnPlay.setText("����");
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
		//�ˬdsdcard���A
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
