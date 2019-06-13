package com.xxl.module.media.audio;

import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author xxl
 * @date 19/6/12.
 * <p>
 * Description 音频录制类
 * <p>
 * <p>
 * AudioRecord(更底层一些，拿到PCM编码方式的音频，如果要对音频进行算法处理，压缩，网络传输则使用这种方式录制)
 * <p>
 * <p>
 * (另外还有MediaRecord 更上层一些 适合MP3）
 **/
public class AudioCapture {

    private static final String TAG = AudioRecord.class.getName();

    private AudioRecord mAudioRecord;

    /**
     * 默认音频输入源
     * <p>
     * 该参数指的是音频采集的输入源，可选的值以常量的形式定义在 MediaRecorder.AudioSource 类中，常用的值包括：DEFAULT（默认），
     * VOICE_RECOGNITION（用于语音识别，等同于DEFAULT）
     * * ，MIC（由手机麦克风输入），VOICE_COMMUNICATION（用于VoIP应用）等等。
     */
    private static final int DEFAULT_SOURCE = MediaRecorder.AudioSource.DEFAULT;

    /**
     * 默认采样率（44100 是目前唯一兼容所有机型的值）
     * <p>
     * 采样率，注意，目前44100Hz是唯一可以保证兼容所有Android手机的采样率。
     */
    private static final int DEFAULT_SAMPLE_RATE = 44100;

    /**
     * 默认声道数
     * <p>
     * <p>
     * 通道数的配置，可选的值以常量的形式定义在 AudioFormat 类中，常用的是 CHANNEL_IN_MONO（单通道），
     * CHANNEL_IN_STEREO（双通道）
     */
    private static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_STEREO;

    /**
     * 默认数据位宽
     * <p>
     * 这个参数是用来配置“数据位宽”的，可选的值也是以常量的形式定义在 AudioFormat 类中，常用的是 ENCODING_PCM_16BIT（16bit），
     * ENCODING_PCM_8BIT（8bit），注意，前者是可以保证兼容所有Android手机的。
     */
    private static final int DEFAULT_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    /**
     * 配置的是 AudioRecord 内部的音频缓冲区的大小
     */
    private int mMinBufferSize = 0;

    private boolean mIsCaptureStarted = false;

    private volatile boolean mIsLoopExit = false;

    private Thread mCaptureThread;
    private DataOutputStream mDos;

    private String mPcmFilePath;
    private String mWavFilePath;

    private AudioRecordListener mAudioRecordListener;

    private static final int STATE_START = 0x0011;
    private static final int STATE_RECORDING = 0x0022;
    private static final int STATE_STOP = 0x0033;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATE_START:
                    if (mAudioRecordListener != null) {
                        mAudioRecordListener.onRecordStart();
                    }
                    break;
                case STATE_RECORDING:
                    if (mAudioRecordListener != null) {
                        mAudioRecordListener.onRecording();
                    }
                    break;
                case STATE_STOP:
                    if (mAudioRecordListener != null) {
                        mAudioRecordListener.onRecordStop();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private AudioCapture() {

    }

    private static class Holder {
        private static AudioCapture INSTANCE = new AudioCapture();
    }

    /**
     * 单例
     *
     * @return AudioCapture
     */
    public static AudioCapture getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 开始录制
     *
     * @param audioOutputPath
     * @return
     */
    public void startRecord(String audioOutputPath) {
        this.mWavFilePath = audioOutputPath;
        setPcmFileOutputPath();

        startRecord(DEFAULT_SOURCE, DEFAULT_SAMPLE_RATE,
                DEFAULT_CHANNEL_CONFIG, DEFAULT_AUDIO_FORMAT);

    }

    public int getMinBufferSize() {
        return mMinBufferSize;
    }

    /**
     * 停止录制
     */
    public void stopRecord() {
        if (!mIsCaptureStarted) {
            return;
        }

        mIsLoopExit = true;
        try {
            mCaptureThread.interrupt();
            mCaptureThread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            try {
                if (mDos != null) {
                    mDos.flush();
                    mDos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            mAudioRecord.stop();

            mHandler.sendEmptyMessage(STATE_STOP);
            Log.d(TAG, "停止录制 ... ");
        }

        mAudioRecord.release();

        mIsCaptureStarted = false;

        Log.d(TAG, "Stop audio capture success !");


        Pcm2Wav pcm2Wav = new Pcm2Wav(DEFAULT_SAMPLE_RATE, DEFAULT_CHANNEL_CONFIG, DEFAULT_AUDIO_FORMAT);

        File wavDir = new File(mWavFilePath);
        if (!wavDir.exists()) {
            wavDir.mkdirs();
        }
        File wavFile = new File(wavDir, System.currentTimeMillis() + ".wav");
        if (!wavFile.exists()) {
            try {
                boolean createSuccess = wavFile.createNewFile();
                if (createSuccess) {
                    pcm2Wav.pcmToWav(mPcmFilePath, wavFile.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getLastWavFilePath() {
        return mWavFilePath;
    }

    public String getLastPcmFilePath() {
        return mPcmFilePath;
    }

    public AudioCapture setAudioRecordListener(AudioRecordListener audioRecordListener) {
        this.mAudioRecordListener = audioRecordListener;
        return this;
    }

    /**
     * 录制状态
     *
     * @return true 处于录制状态，false 不在录制状态
     */
    public boolean isCaptureStarted() {
        return mIsCaptureStarted;
    }

    /**
     * 开始录制
     *
     * @param audioSource    音频输入源
     * @param sampleRateInHz 采样率
     * @param channelConfig  通道数
     * @param audioFormat    数据位宽
     * @return
     */
    public boolean startRecord(int audioSource, int sampleRateInHz, int channelConfig, int audioFormat) {
        if (mIsCaptureStarted) {
            Log.e(TAG, "Capture already started !");
            return false;
        }
        mMinBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);

        if (mMinBufferSize == AudioRecord.ERROR_BAD_VALUE) {
            Log.e(TAG, "Invalid parameter !");
            return false;
        }
        Log.d(TAG, "getMinBufferSize = " + mMinBufferSize + " bytes !");

        mAudioRecord = new AudioRecord(audioSource, sampleRateInHz,
                channelConfig, audioFormat, mMinBufferSize);

        if (mAudioRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
            Log.e(TAG, "AudioRecord initialize fail ! ");
            return false;
        }

        mAudioRecord.startRecording();

        mIsLoopExit = false;

        mCaptureThread = new Thread(new AudioCaptureRunnable());
        mCaptureThread.start();

        mHandler.sendEmptyMessage(STATE_START);
        Log.d(TAG, "开始录制 ...");
        mIsCaptureStarted = true;

        Log.d(TAG, "Start audio capture success !");


        return true;
    }


    private void setPcmFileOutputPath() {
        String pcmDirPath = Environment.getExternalStorageDirectory() + File.separator + "666";
        File pcmDir = new File(pcmDirPath);
        if (!pcmDir.exists()) {
            pcmDir.mkdirs();
        }
        try {
            File pcmFile = new File(pcmDirPath, "tempPcm");
            if (!pcmFile.exists()) {
                pcmFile.createNewFile();
            }
            mPcmFilePath = pcmFile.getAbsolutePath();
            mDos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(pcmFile)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class AudioCaptureRunnable implements Runnable {

        @Override
        public void run() {

            try {

                while (!mIsLoopExit) {

                    //byte[] buffer = new byte[mMinBufferSize];
                    short[] buffer = new short[mMinBufferSize];
                    int ret = mAudioRecord.read(buffer, 0, mMinBufferSize);

                    if (ret == AudioRecord.ERROR_INVALID_OPERATION) {
                        Log.e(TAG, "Error ERROR_INVALID_OPERATION");
                    } else if (ret == AudioRecord.ERROR_BAD_VALUE) {
                        Log.e(TAG, "Error ERROR_BAD_VALUE");
                    } else {
                        Log.d(TAG, "录制中 ... ");
                        mHandler.sendEmptyMessage(STATE_RECORDING);
                        Log.d(TAG, "OK, Captured " + ret + " bytes !");

                        for (int i = 0; i < ret; i++) {
                            mDos.writeShort(Short.reverseBytes(buffer[i]));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 销毁 页面调用
     */
    public void onDestory() {
        stopRecord();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

}
