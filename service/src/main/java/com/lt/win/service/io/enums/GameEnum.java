package com.lt.win.service.io.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

/**
 * @author: David
 * @date: 15/06/2020
 */
public interface GameEnum {
    @Getter
    @NoArgsConstructor
    enum GAME {
        /**
         * 平台参数
         */
        SBO_SPORTS(101, "SBOSports", "SBO", "SboSports", "sports_type", "username"),
        IBC_SPORTS(105, "ShaBaSports", "IBC", "ShabaSports", "sport_type", "vendor_member_id"),
        BBIN_GAMES(201, "BBINGame", "BBIN", "BbinGame", "game_type", "username"),
        HB_GAMES(202, "HabaneroGame", "Habanero", "Hb", "brand_game_id", "username"),
        MG_GAMES(203, "MGGame", "MG", "Mg", "game_code", "player_id"),
        UPG_GAMES(204, "UPGGame", "UPG", "Upg", "game_code", "player_id"),
        WM_LIVE(301, "WMLive", "WM", "Wm", "gid", "user"),
        BBIN_LIVE(302, "BBINLive", "BBIN", "BbinLive", "game_type", "username"),
        DG_LIVE(303, "DGLive", "DG", "Dg", "game_type", "username"),
        AG_LIVE(304, "AGLive", "AG", "Ag", "game_type", "play_name"),
        SEXY_LIVE(305, "SexyLive", "AE", "AeSexy", "game_code", "play_name"),
        GG_FINISH(401, "GGFishingGame", "GG", "Gg", "game_id", "account_no"),
        BBIN_EXPERT_FINISH(402, "BBINFishingExpert", "BBIN", "BbinFishingExpert", "game_type", "username"),
        BBIN_MASTER_FINISH(403, "BBINFishingMasters", "BBIN", "BbinFishingMaster", "game_type", "username"),
        DS_CHESS(501, "DSChess", "DS", "Ds", "game_id", "member"),
        KING_MAKER_CHESS(502, "KingMaker", "AE", "AeKingMaker", "game_code", "user_id"),
        TCG_LOTTERY(701, "TCGLottery", "TCG", "Tcg", "game_code", "username"),
        SLOTTO_LOTTERY(702, "SLottoLottery", "SLOTTO", "Slotto", "bet_type", "login_name"),
        S128_ANIMAL(801, "S128CockFight", "S128", "S128", "arena_name_cn", "login_id"),
        ;
        Integer id;
        String model;
        String parent;
        String betslips;
        String gameIdField;
        String playerIdField;

        GAME(Integer id, String model, String parent, String betslips, String gameIdField, String playerIdField) {
            this.id = id;
            this.parent = parent;
            this.model = model;
            this.betslips = betslips;
            this.gameIdField = gameIdField;
            this.playerIdField = playerIdField;
        }

        @Nullable
        public static GAME getGameEnumById(Integer id) {
            for (GAME game : GAME.values()) {
                if (id.equals(game.getId())) {
                    return game;
                }
            }
            return null;
        }
    }
}
