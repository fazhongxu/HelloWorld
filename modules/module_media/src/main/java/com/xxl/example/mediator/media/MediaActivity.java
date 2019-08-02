package com.xxl.example.mediator.media;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xxl.example.mediator.media.audio.AudioCapture;
import com.xxl.example.mediator.media.audio.AudioPlayListener;
import com.xxl.example.mediator.media.audio.AudioPlayer;
import com.xxl.example.mediator.media.audio.AudioRecordListener;
import com.xxl.example.media.R;

import java.io.File;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


@Route(path = IConstantMedia.MEDIA_PATH)
public class MediaActivity extends AppCompatActivity implements View.OnClickListener, AudioRecordListener, AudioPlayListener {

    private Button mRecordBtn;
    private Button mPlayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);


        mRecordBtn = findViewById(R.id.media_record);
        mPlayBtn = findViewById(R.id.media_play);

        mRecordBtn.setOnClickListener(this);
        mPlayBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.media_record) {
            RxPermissions rxPermissions = new RxPermissions(this);
            Disposable disposable = rxPermissions.request(Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                if (!AudioCapture.getInstance().isCaptureStarted()) {
                                    String audioOutputpath = Environment.getExternalStorageDirectory() + File.separator + "HelloWorld";
                                    AudioCapture.getInstance()
                                            .setAudioRecordListener(MediaActivity.this)
                                            .startRecord(audioOutputpath);
                                } else {
                                    AudioCapture.getInstance().stopRecord();
                                }
                            } else {
                                Toast.makeText(MediaActivity.this, "请打开录音权限", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else if (id == R.id.media_play) {
            try {
                if (!AudioPlayer.getInstance().isPlaying()) {
                    AudioPlayer.getInstance().setAudioPlayListener(MediaActivity.this)
                            .play(AudioCapture.getInstance().getLastPcmFilePath());
                } else {
                    AudioPlayer.getInstance().stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioCapture.getInstance().onDestory();
        AudioPlayer.getInstance().onDestory();
    }

    @Override
    public void onRecordStart() {
        mRecordBtn.setText(getString(R.string.media_stop));
    }

    @Override
    public void onRecording() {
    }

    @Override
    public void onRecordStop() {
        mRecordBtn.setText(getString(R.string.media_record));
    }

    @Override
    public void onAudioPlayStart() {
        mPlayBtn.setText(R.string.media_stop);
    }

    @Override
    public void onAudioPlaying() {
    }

    @Override
    public void onAudioPlayStop() {
        mPlayBtn.setText(R.string.media_play);
    }
}
