package com.example.audiotracktest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private Button playBtn;
	private AudioTrack player;
	private int audioBufSize;
	private byte[] audioData;

	String filePath = "/sdcard/recordDir/record1.pcm";
	private byte[] abc;
	int offset;
	Player player1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		audioBufSize = AudioTrack.getMinBufferSize(8000, AudioFormat.CHANNEL_OUT_STEREO,
				AudioFormat.ENCODING_PCM_16BIT);

		player = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_OUT_STEREO,
				AudioFormat.ENCODING_PCM_16BIT, audioBufSize, AudioTrack.MODE_STREAM);

		playBtn = (Button) findViewById(R.id.button1);

		playBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				player.play();

				player1 = new Player();
				player1.start();

			}
		});
	}

	class Player extends Thread {
		byte[] data1 = new byte[audioBufSize * 2];
		File file = new File(filePath);
		int off1 = 0;
		FileInputStream fileInputStream;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			while (true) {
				try {
					fileInputStream = new FileInputStream(file);
					fileInputStream.skip((long) off1);
					fileInputStream.read(data1, 0, audioBufSize * 2);
					off1 += audioBufSize * 2;
				} catch (Exception e) {
					// TODO: handle exception
				}
				player.write(data1, offset, audioBufSize * 2);
			}
		}
	}
}
