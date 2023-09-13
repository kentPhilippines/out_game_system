package com.lt.win.dao.generator.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 游戏收藏列表
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@TableName("win_game_slot_favorite")
public class GameSlotFavorite extends Model<GameSlotFavorite> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * UID
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 游戏类型ID
     */
    @TableField("game_id")
    private Integer gameId;

    /**
     * 游戏(子老虎机)ID
     */
    @TableField("game_slot_id")
    private String gameSlotId;

    @TableField("created_at")
    private Integer createdAt;

    @TableField("updated_at")
    private Integer updatedAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameSlotId() {
        return gameSlotId;
    }

    public String getGameSlotIdAndId() {
        return gameSlotId.concat("-").concat(String.valueOf(gameId));
    }

    public void setGameSlotId(String gameSlotId) {
        this.gameSlotId = gameSlotId;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "GameSlotFavorite{" +
        "id=" + id +
        ", uid=" + uid +
        ", gameId=" + gameId +
        ", gameSlotId=" + gameSlotId +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
