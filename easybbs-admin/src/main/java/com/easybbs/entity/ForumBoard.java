package com.easybbs.entity;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 文章板块信息
 * @TableName forum_board
 */
@Data
@TableName(value = "forum_board")
public class ForumBoard implements Serializable {
    /**
     * 板块ID
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "board_id")
    private Integer boardId;

    /**
     * 父级板块ID
     */
    @TableField(value = "p_board_id")
    private Integer pboardId;

    /**
     * 板块名
     */
    @TableField(value = "board_name")
    private String boardName;

    /**
     * 封面
     */
    @TableField(value = "cover")
    private String cover;

    /**
     * 描述
     */
    @TableField(value = "board_desc")
    private String boardDesc;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 0:只允许管理员发帖 1:任何人可以发帖
     */
    @TableField(value = "post_type")
    private Integer postType;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ForumBoard other = (ForumBoard) that;
        return (this.getBoardId() == null ? other.getBoardId() == null : this.getBoardId().equals(other.getBoardId()))
            && (this.getPboardId() == null ? other.getPboardId() == null : this.getPboardId().equals(other.getPboardId()))
            && (this.getBoardName() == null ? other.getBoardName() == null : this.getBoardName().equals(other.getBoardName()))
            && (this.getCover() == null ? other.getCover() == null : this.getCover().equals(other.getCover()))
            && (this.getBoardDesc() == null ? other.getBoardDesc() == null : this.getBoardDesc().equals(other.getBoardDesc()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getPostType() == null ? other.getPostType() == null : this.getPostType().equals(other.getPostType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBoardId() == null) ? 0 : getBoardId().hashCode());
        result = prime * result + ((getPboardId() == null) ? 0 : getPboardId().hashCode());
        result = prime * result + ((getBoardName() == null) ? 0 : getBoardName().hashCode());
        result = prime * result + ((getCover() == null) ? 0 : getCover().hashCode());
        result = prime * result + ((getBoardDesc() == null) ? 0 : getBoardDesc().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getPostType() == null) ? 0 : getPostType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", boardId=").append(boardId);
        sb.append(", pBoardId=").append(pboardId);
        sb.append(", boardName=").append(boardName);
        sb.append(", cover=").append(cover);
        sb.append(", boardDesc=").append(boardDesc);
        sb.append(", sort=").append(sort);
        sb.append(", postType=").append(postType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}