package com.xxl.module.media.audio;

import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author xxl
 * @date 19/6/13.
 * <p>
 * Description AudioTrack 方式播放 PCM  格式的音频流
 **/
public class AudioPlayer {

    private static final String TAG = AudioTrack.class.getSimpleName();

    private static final int DEFAULT_STREAM_TYPE = AudioManager.STREAM_MUSIC;
    private static final int DEFAULT_SAMPLE_RATE = 44100;
    private static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_STEREO;
    private static final int DEFAULT_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static final int DEFAULT_PLAY_MODE = AudioTrack.MODE_STREAM;

    private int mMinBufferSize;
    private AudioTrack mAudioTrack;

    private boolean mIsPlaying = false;
    private Thread mPlayingThread;
    private DataInputStream mDataInputStream;

    private AudioPlayListener mAudioPlayListener;

    private static final int STATE_START = 0x0011;
    private static final int STATE_PLAYING = 0x0022;
    private static final int STATE_STOP = 0x0033;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATE_START:
                    if (mAudioPlayListener != null) {
                        mAudioPlayListener.onAudioPlayStart();
                    }
                    break;
                case STATE_PLAYING:
                    if (mAudioPlayListener != null) {
                        mAudioPlayListener.onAudioPlaying();
                    }
                    break;
                case STATE_STOP:
                    if (mAudioPlayListener != null) {
                        mAudioPlayListener.onAudioPlayStop();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private AudioPlayer() {

    }

    private static class Holder {
        private static AudioPlayer INSTANCE = new AudioPlayer();
    }

    public static AudioPlayer getInstance() {
        return Holder.INSTANCE;
    }

    private void initConfig() {
        config(DEFAULT_STREAM_TYPE, DEFAULT_SAMPLE_RATE,
                DEFAULT_CHANNEL_CONFIG, DEFAULT_AUDIO_FORMAT, DEFAULT_PLAY_MODE);
    }

    private boolean config(int streamType, int sampleRateInHz, int channelConfig, int audioFormat, int mode) {
        if (mIsPlaying) {
            Log.e(TAG, "Player already started !");
            return false;
        }

        mMinBufferSize = AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);

        if (mMinBufferSize == AudioTrack.ERROR_BAD_VALUE) {
            Log.e(TAG, "Invalid parameter !");
            return false;
        }

        mAudioTrack = new AudioTrack(streamType, sampleRateInHz, channelConfig, audioFormat, mMinBufferSize, mode);

        if (mAudioTrack.getState() == AudioTrack.STATE_UNINITIALIZED) {
            Log.e(TAG, "AudioTrack initialize fail !");
            return false;
        }

        Log.d(TAG, "Start audio player success !");

        return true;

    }

    public AudioPlayer setAudioPlayListener(AudioPlayListener audioPlayListener) {
        this.mAudioPlayListener = audioPlayListener;
        return this;
    }

    /**
     * 正在播放
     *
     * @return
     */
    public boolean isPlaying() {
        return mIsPlaying;
    }

    /**
     * 开始播放录音
     */
    public synchronized void play(String filePath) {
        if (mAudioTrack == null) {
            initConfig();
        }
        Log.e(TAG, "播放状态：" + mAudioTrack.getState());
        if (mIsPlaying) {
            return;
        }

        if (!new File(filePath).exists()) {
            Log.e(TAG, "播放的音频文件不存在！");
            return;
        }

        if (null != mAudioTrack && mAudioTrack.getState() == AudioTrack.STATE_INITIALIZED) {
            mAudioTrack.play();
        }

        mHandler.sendEmptyMessage(STATE_START);
        Log.d(TAG, "开始播放 ...");

        mIsPlaying = true;
        mPlayingThread = new Thread(new PlayingRunnable(filePath), "PlayingThread");
        mPlayingThread.start();
    }

    class PlayingRunnable implements Runnable {
        private String mFilePath;

        public PlayingRunnable(String filePath) {
            this.mFilePath = filePath;
        }

        @Override
        public void run() {
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(mFilePath));
                mDataInputStream = new DataInputStream(fileInputStream);
                byte[] audioDataArray = new byte[mMinBufferSize];
                int readLength = 0;
                while (mDataInputStream.available() > 0) {
                    readLength = mDataInputStream.read(audioDataArray);

                    if (readLength > 0) {
                        if (mAudioTrack != null && mAudioTrack.getPlayState() != AudioTrack.PLAYSTATE_STOPPED) {
                            Log.d(TAG, "正在播放... ");
                            mHandler.sendEmptyMessage(STATE_PLAYING);
                            mAudioTrack.write(audioDataArray, 0, readLength);
                        }
                    }
                }
                stop();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止播放
     */
    public synchronized void stop() {
        try {
            if (mAudioTrack != null) {

                mIsPlaying = false;

                //停止播放
                if (mAudioTrack.getState() != AudioTrack.STATE_UNINITIALIZED) {
                    if (mAudioTrack.getPlayState() != AudioTrack.PLAYSTATE_STOPPED) {
                        mAudioTrack.stop();
                    }
                }

                //关闭线程
                if (mPlayingThread != null) {
                    mPlayingThread.interrupt();
                    mPlayingThread = null;
                }

                mHandler.sendEmptyMessage(STATE_STOP);
                Log.d(TAG, "停止播放...");

                //释放资源
                release();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mAudioTrack.getState() == AudioRecord.STATE_INITIALIZED) {
            mAudioTrack.release();
            mAudioTrack = null;
        }
    }

    /**
     * 销毁 页面调用
     */
    public void onDestory() {
        stop();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

}
