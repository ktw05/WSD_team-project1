// domain/MessageVO.java

package org.example.wsdfinalteamproject.domain;

import java.time.LocalDateTime;

public class MessageVO {
    // DB 테이블 필드: id, post_id, user_id, comment_text, created_at
    private int id;
    private int postId;
    private int userId;
    private String commentText;
    private LocalDateTime createdAt;

    // Getter, Setter, toString, Constructors... (생략)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MessageVO{" +
                "id=" + id +
                ", postId=" + postId +
                ", userId=" + userId +
                ", commentText='" + commentText + '\'' +
                '}';
    }
}