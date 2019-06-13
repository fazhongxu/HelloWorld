package com.xxl.module.media;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xxl.mediator.media.IConstantMedia;
import com.xxl.module.R;
import com.xxl.module.media.audio.AudioCapture;
import com.xxl.module.media.audio.AudioPlayer;

import java.io.File;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


@Route(path = IConstantMedia.MEDIA_PATH)
public class MediaActivity extends AppCompatActivity implements View.OnClickListener {

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
                                    AudioCapture.getInstance().startRecord(audioOutputpath);

                                    mRecordBtn.setText(getString(R.string.media_stop));
                                } else {
                                    AudioCapture.getInstance().stopRecord();
                                    mRecordBtn.setText(getString(R.string.media_record));
                                }
                            } else {
                                Toast.makeText(MediaActivity.this, "请打开录音权限", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else if (id == R.id.media_play) {
            try {
                if (!AudioPlayer.getInstance().isPlaying()) {
                    mPlayBtn.setText(getString(R.string.media_stop));
                    AudioPlayer.getInstance().play(AudioCapture.getInstance().getLastPcmFilePath());
                } else {
                    AudioPlayer.getInstance().stop();
                    mPlayBtn.setText(getString(R.string.media_play));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
