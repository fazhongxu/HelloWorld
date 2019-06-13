package com.xxl.module.media.audio;

/**
 * @author xxl
 * @date 19/6/13.
 * <p>
 * Description 音频播放监听
 **/
public interface AudioPlayListener {
    /**
     * 播放开始
     */
    void onAudioPlayStart();

    /**
     * 播放中
     */
    void onAudioPlaying();

    /**
     * 播放停止
     */
    void onAudioPlayStop();

}
