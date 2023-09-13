package com.lt.win.service.io.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 龙游注单
 *
 * @author fangzs
 * @date 2022/7/28 15:43
 */
@Data
public class BetBo implements Serializable {
    private static final long serialVersionUID = -6289215680801052878L;

    private String token;
    @JSONField(name = "account_id")
    private String accountId;
    /**
     * Amount to be debited from player’s wallet.
     */
    private BigDecimal amount;
    /**
     * 现金，奖金，免费旋转，活动免费旋转
     * Indicate type of amount type. It can be cash / bonus / freespin/ promo_freespin.
     */
    @JSONField(name = "amount_type")
    @JsonProperty(value = "amount_type")
    private String amountType;
    /**
     * Currency of the player
     */
    private String currency;
    /**
     * 游戏id
     */
    @JSONField(name = "game_id")
    @JsonProperty(value = "game_id")
    private String gameId;
    /**
     * Unique id of the transaction
     */
    @JSONField(name = "transaction_id")
    @JsonProperty(value = "transaction_id")
    private String transactionId;
    /**
     * Round ID of each play / spin
     */
    @JSONField(name = "round_id")
    @JsonProperty(value = "round_id")
    private String roundId;
    /**
     * A transaction ID which is related to respective debit transaction.
     */
    @JSONField(name = "original_transaction_id")
    @JsonProperty(value = "original_transaction_id")
    private String originalTransactionId;
    /**
     * Type of the game. slots / table_games / scratch_cards / bingo
     */
    @JSONField(name = "game_type")
    @JsonProperty(value = "game_type")
    private String gameType;
    /**
     * Name of the game
     */
    @JSONField(name = "game_name")
    @JsonProperty(value = "game_name")
    private String gameName;
    /**
     * Extra information about the debit request
     */
    private String note;
    /**
     *
     * Provider will send the bonus / campaign ID which is related to the promotional freespins. It will be null when it is in real mode.
     */
    @JSONField(name = "bonus_id")
    @JsonProperty(value = "bonus_id")
    private String bonusId;
    /**
     * true indicates round is closed false indicates round is open
     */
    @JSONField(name = "round_end_state")
    @JsonProperty(value = "round_end_state")
    private String roundEndState;


}
