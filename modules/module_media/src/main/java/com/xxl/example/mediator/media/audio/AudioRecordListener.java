package com.xxl.example.mediator.media.audio;

/**
 * @author xxl
 * @date 19/6/13.
 * <p>
 * Description 音频录制监听
 **/
public interface AudioRecordListener {
    /**
     * 录制开始
     */
    void onRecordStart();

    /**
     * 录制中
     */
    void onRecording();

    /**
     * 录制结束
     */
    void onRecordStop();
}
