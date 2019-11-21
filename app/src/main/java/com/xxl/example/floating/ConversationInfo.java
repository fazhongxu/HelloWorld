package com.xxl.example.floating;

import java.io.Serializable;

/**
 * 会话信息,测试类
 *
 * @author xxl.
 * @date 2019/11/21.
 */
public class ConversationInfo implements Serializable {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 会话类型
     */
    private int conversationType;

    /**
     * 会话id
     */
    private String targetId;

    public ConversationInfo(String nickname,
                            String avatar,
                            int conversationType,
                            String targetId) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.conversationType = conversationType;
        this.targetId = targetId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getConversationType() {
        return conversationType;
    }

    public void setConversationType(int conversationType) {
        this.conversationType = conversationType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
}
