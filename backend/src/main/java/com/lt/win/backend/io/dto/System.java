package com.lt.win.backend.io.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 系统设置
 * </p>
 *
 * @author andy
 * @since 2020/10/5
 */
public interface System {

    /**
     * Banner图 -> 列表查询 reqBody
     */
    @Data
    class BannerListReqBody {
        @ApiModelProperty(name = "language", value = "语言", example = "zh")
        private String language;
        @ApiModelProperty(name = "device", value = "设备:m-手机 d-电脑 ANDROID-安卓 IOS-苹果", example = "m")
        private String device;
    }

    /**
     * Banner图 -> 列表查询 resBody
     */
    @Data
    class BannerListResBody {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Integer id;
        @ApiModelProperty(name = "language", value = "语言", example = "zh")
        private String language;
        @ApiModelProperty(name = "device", value = "设备:m-手机 d-电脑 ANDROID-安卓 IOS-苹果", example = "m")
        private String device;
        @ApiModelProperty(name = "img", value = "图片地址", example = "https://static.xinbosports.com/group1/M03/00/04/wKh2AV-AYCeAAEHHAAnnHOXEpvE085.jpg")
        private String img;
        @ApiModelProperty(name = "href", value = "跳转地址", example = "https://static.xinbosports.com/group1/M03/00/04/wKh2AV-AYCeAAEHHAAnnHOXEpvE085.jpg")
        private String href;
        @ApiModelProperty(name = "sort", value = "排序(从高到低)", example = "100")
        private Integer sort;
        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用", example = "1")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1594992495")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "更新时间", example = "1594992495")
        private Integer updatedAt;
    }

    /**
     * Banner图 -> 新增 reqBody
     */
    @Data
    class AddBannerReqBody {
        @ApiModelProperty(name = "language", value = "语言", example = "zh")
        private String language;
        @ApiModelProperty(name = "device", value = "设备:m-手机 d-电脑 ANDROID-安卓 IOS-苹果", example = "m")
        private String device;
        @ApiModelProperty(name = "img", value = "图片地址", example = "https://static.xinbosports.com/group1/M03/00/04/wKh2AV-AYCeAAEHHAAnnHOXEpvE085.jpg")
        private String img;
        @ApiModelProperty(name = "href", value = "跳转地址", example = "https://static.xinbosports.com/group1/M03/00/04/wKh2AV-AYCeAAEHHAAnnHOXEpvE085.jpg")
        private String href;
        @ApiModelProperty(name = "sort", value = "排序(从高到低)", example = "100")
        private Integer sort;
        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用", example = "1")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1594992495")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "更新时间", example = "1594992495")
        private Integer updatedAt;
    }

    /**
     * Banner图 -> 新增 reqBody
     */
    @Data
    class UpdateBannerReqBody {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Integer id;
        @ApiModelProperty(name = "language", value = "语言", example = "zh")
        private String language;
        @ApiModelProperty(name = "device", value = "设备:m-手机 d-电脑 ANDROID-安卓 IOS-苹果", example = "m")
        private String device;
        @ApiModelProperty(name = "img", value = "图片地址", example = "https://static.xinbosports.com/group1/M03/00/04/wKh2AV-AYCeAAEHHAAnnHOXEpvE085.jpg")
        private String img;
        @ApiModelProperty(name = "href", value = "跳转地址", example = "https://static.xinbosports.com/group1/M03/00/04/wKh2AV-AYCeAAEHHAAnnHOXEpvE085.jpg")
        private String href;
        @ApiModelProperty(name = "sort", value = "排序(从高到低)", example = "100")
        private Integer sort;
        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用", example = "1")
        private Integer status;
    }

    /**
     * Banner图 -> 删除 reqBody
     */
    @Data
    class DelBannerReqBody {
        @NotNull(message = "不能为空")
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Integer id;
    }

    /**
     * 预设开奖列表-> 请求实体
     */
    @Data
    @Builder
    class GetOpenPresetListReqBody {
        @ApiModelProperty(name = "lotteryId", value = "彩种ID", example = "100")
        private Integer lotteryId;
        @ApiModelProperty(name = "actionNo", value = "期号", example = "20201003275")
        private Long actionNo;
        @ApiModelProperty(name = "isShow", value = "是否显示历史数据；0-不显示，1-显示", example = "0")
        private Integer isShow = 0;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1590735421")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1590735421")
        private Integer endTime;
    }

    /**
     * 预设开奖新增/修改-> 请求实体
     */
    @Data
    @Builder
    class SaveOrUpdateOpenPresetReqBody {

        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Integer id;

        @NotNull(message = "lotteryId不能为空")
        @ApiModelProperty(name = "lotteryId", value = "彩种ID[必填]", example = "100", required = true)
        private Integer lotteryId;

        @NotNull(message = "actionNo不能为空")
        @ApiModelProperty(name = "actionNo", value = "期号[必填]", example = "20201003275", required = true)
        private Long actionNo;

        @ApiModelProperty(name = "data", value = "开奖结果[必填]", example = "1", required = true)
        private String data;
    }

    /**
     * 预设开奖删除-> 请求实体
     */
    @Data
    class DeleteOpenPresetReqBody {
        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Integer id;
    }

    /**
     * 预设开奖列表->ResBody
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class GetOpenPresetListResBody {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Integer id;

        @ApiModelProperty(name = "platId", value = "平台ID", example = "1000")
        private Integer platId;

        @ApiModelProperty(name = "lotteryId", value = "彩种ID", example = "100")
        private Integer lotteryId;

        @ApiModelProperty(name = "actionNo", value = "期号", example = "20201003275")
        private Long actionNo;

        @ApiModelProperty(name = "data", value = "开奖结果", example = "1")
        private String data;

        @ApiModelProperty(name = "info", value = "补充信息")
        private String info;

        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1")
        private Integer createdAt;

        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1")
        private Integer updatedAt;

        @ApiModelProperty(name = "status", value = "状态：0-未使用，1-使用", example = "1")
        private Integer status;
    }

    /**
     * 首页INIT 参数出参
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class InitResDto {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        public Integer id;

        @ApiModelProperty(name = "title", value = "编码(英文)", example = "static_server")
        public String title;

        @ApiModelProperty(name = "titleZh", value = "名称(中文)", example = "静态服务器")
        public String titleZh;

        @ApiModelProperty(name = "value", value = "值", example = "http://staic.xinbocaipiao.com")
        public String value;
    }


    /**
     * 预设开奖->导出 resBody
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class ExportOpenPresetListResBody {

        @ApiModelProperty(name = "lotteryId", value = "彩种ID", example = "100")
        private Integer lotteryId;

        @ApiModelProperty(name = "lotteryName", value = "彩种名称", example = "100")
        private String lotteryName;

        @ApiModelProperty(name = "actionNo", value = "期号", example = "20201003275")
        private Long actionNo;

        @ApiModelProperty(name = "data", value = "开奖结果", example = "1")
        private String data;

        @ApiModelProperty(name = "info", value = "补充信息")
        private String info;
    }

    /**
     * 预设开奖->获取当前期号信息
     */
    @Data
    class GetLotteryActionNoResBody {
        @ApiModelProperty(name = "lotteryId", value = "ID", example = "1000")
        public Integer lotteryId;
        @ApiModelProperty(name = "actionNo", value = "期号")
        public Long actionNo;
        @ApiModelProperty(name = "actionNum", value = "期数")
        public Integer actionNum;
    }

    /**
     * 预设开奖->新增批量预设-> 请求实体
     */
    @Data
    @Builder
    class SaveOrUpdateBatchOpenPresetReqBody {

        @NotNull(message = "lotteryId不能为空")
        @ApiModelProperty(name = "lotteryId", value = "彩种ID[必填]", example = "100", required = true)
        private Integer lotteryId;

        @NotNull(message = "actionNo不能为空")
        @ApiModelProperty(name = "actionNo", value = "期号[必填]", example = "20201003275", required = true)
        private Long actionNo;

        @NotNull(message = "nums不能为空")
        @ApiModelProperty(name = "nums", value = "预设期数[必填,数字类型,范围1-480]", example = "10", required = true)
        private Integer nums;
    }

    /**
     * 预设开奖->获取当前期号信息-> 请求实体
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class GetLotteryActionNoReqBody {
        @NotNull
        @ApiModelProperty(name = "lotteryId", value = "ID", example = "100", required = true)
        public Integer lotteryId;
    }

    /**
     * 预设开奖->导出-> 请求实体
     */
    @Data
    @Builder
    class ExportOpenPresetListReqBody {
        @ApiModelProperty(name = "lotteryId", value = "彩种ID", example = "100")
        private Integer lotteryId;
        @ApiModelProperty(name = "actionNo", value = "期号", example = "20201003275")
        private Long actionNo;
        @ApiModelProperty(name = "startTime", value = "开始时间,不能跨天", example = "1590735421")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间,不能跨天", example = "1590735421")
        private Integer endTime;
        @ApiModelProperty(name = "isShow", value = "是否显示历史数据；0-不显示，1-显示", example = "0")
        private Integer isShow = 0;

    }

    @Data
    @ApiModel(value = "BannerByLangReqDto", description = "语言获取Banner信息请求实体类")
    class BannerByLangReqDto {
        @ApiModelProperty(name = "id", value = "记录Id")
        private Integer id;
        @ApiModelProperty(name = "lang", value = "语言")
        private String lang;
    }

    @Data
    @Builder
    @ApiModel(value = "BannerByLangResDto", description = "语言获取Banner信息响应实体类")
    class BannerByLangResDto {
        @ApiModelProperty(name = "imgH5", value = "H5图片地址", example = "https://static.xinbosports.com/group1/M03/00/04/wKh2AV-AYCeAAEHHAAnnHOXEpvE085.jpg")
        private String imgH5;
        @ApiModelProperty(name = "imgPC", value = "PC图片地址", example = "https://static.xinbosports.com/group1/M03/00/04/wKh2AV-AYCeAAEHHAAnnHOXEpvE085.jpg")
        private String imgPC;

        @ApiModelProperty(name = "href", value = "外链地址")
        private String href;
    }


    @Data
    @ApiModel(value = "CommissionRule", description = "获取佣金规则信息响应实体类")
    class CommissionRuleDto {
        @ApiModelProperty(name = "settleType", value = "结算方式：0-自然天结算，1-自然周结算，2-自然月结算")
        private Integer settleType;
        @ApiModelProperty(name = "effectType", value = "生效方式：0-次月生效，1-立即生效（次日）")
        private Integer effectType;
        @ApiModelProperty(name = "CommissionRateList", value = "佣金比例集合")
        private List<CommissionRateDto> commissionRateList;
        @ApiModelProperty(name = "minCoin", value = "最小提款金额", example = "0.05")
        private BigDecimal minCoin;
        @ApiModelProperty(name = "maxCoin", value = "最大提款金额", example = "100")
        private BigDecimal maxCoin;
        @ApiModelProperty(name = "fees", value = "提款手续费", example = "100")
        private BigDecimal fees;
        @ApiModelProperty(name = "depositMultiple", value = "充值流水倍数", example = "1")
        private BigDecimal depositMultiple;
        @ApiModelProperty(name = "transferMultiple", value = "系统调账流水倍数", example = "1")
        private BigDecimal transferMultiple;
//        @ApiModelProperty(name = "coinRangeList", value = "快捷金额", example = "[100,200,300]")
//        private List<Integer> coinRangeList;
    }


    @Data
    @ApiModel(value = "CommissionRateDto", description = "代理佣金分成比例实例")
    class CommissionRateDto {
        @ApiModelProperty(name = "agent_level", value = "代理登记", example = "1")
        private Integer agentLevel;
        @ApiModelProperty(name = "agentLevelRate", value = "佣金比例", example = "0.05")
        private BigDecimal agentLevelRate;
    }

    @Data
    @ApiModel(value = "RegisterLoginConfigDto", description = "注册登录配置应实体类")
    class PlatConfigDto {
        @ApiModelProperty(name = "title", value = "平台名称", example = "BWG")
        private String title;
        @ApiModelProperty(name = "platLogo", value = "平台logo", example = "BWg")
        private String platLogo;
        @ApiModelProperty(name = "wsServer", value = "Socket地址", example = "wss://testwss.xinbosports.com:4431/ws")
        private String wsServer;
        @ApiModelProperty(name = "sms", value = "短信通道", example = "{}")
        private String sms;
        @ApiModelProperty(name = "registerMobile", value = "手机号码：0-显示，1-不显示", example = "1")
        private Integer registerMobile;
        @ApiModelProperty(name = "registerInviteCode", value = "邀请码：0-显示(选填)，1-显示(必填)，2-不显示", example = "0")
        private Integer registerInviteCode;
        @ApiModelProperty(name = "loginType", value = "登录方式：0-用户名登录，1-手机号登录", example = "0,1")
        private String loginType;
        @ApiModelProperty(name = "verificationOfGoogle", value = "谷歌验证码", example = "0")
        private String verificationOfGoogle;
    }


    @Data
    @ApiModel(value = "OpenRateDistributeReqDto", description = "开奖概率分布请求参数")
    class OpenRateDistributeReqDto {
        @NotNull(message = "不能为空")
        @ApiModelProperty(name = "actionNo", value = "期号", required = true, example = "20201003275")
        private Long actionNo;

        @NotNull(message = "不能为空")
        @ApiModelProperty(name = "lotteryId", value = "彩种ID", required = true, example = "102")
        private Integer lotteryId;

        @ApiModelProperty(name = "platId", value = "平台ID", example = "1001")
        private Integer platId;

        @ApiModelProperty(name = "openCode", value = "预设开奖号码", example = "1")
        private Integer openCode;
    }

    @Data
    @ApiModel(value = "OpenRateDistributeResDto", description = "开奖概率分布响应参数")
    class OpenRateDistributeResDto {
        @ApiModelProperty(name = "betCoin", value = "投注金额", example = "1000.00")
        private BigDecimal betCoin = BigDecimal.ZERO;
        @ApiModelProperty(name = "openCoin", value = "开奖金额", example = "700.00")
        private BigDecimal openCoin = BigDecimal.ZERO;
        @ApiModelProperty(name = "openRate", value = "中奖概率", example = "70%")
        private String openRate = "0.00%";
        @ApiModelProperty(name = "openResult", value = "开奖结果", example = "")
        private List<OpenRate> openResult;
    }

    @Data
    @ApiModel(value = "OpenRate", description = "开奖结果请求参数")
    class OpenRate {
        @ApiModelProperty(name = "code", value = "开奖结果", example = "1")
        private Integer code;
        @ApiModelProperty(name = "coin", value = "开奖金额", example = "200.00")
        private BigDecimal coin = BigDecimal.ZERO;
        @ApiModelProperty(name = "rate", value = "开奖概率", example = "20%")
        private String rate = "0.00%";
    }


    @Data
    @ApiModel(value = "OnlineServiceConfigDto", description = "在线客户配置请求参数")
    class OnlineServiceConfigDto {
        @ApiModelProperty(name = "pcUrl", value = "pc端/在线客户", example = "https://lin.ee/catAQlC")
        private String pcUrl;
        @ApiModelProperty(name = "h5Url", value = "移动端/在线客户", example = "https://lin.ee/catAQlC")
        private String h5Url;
        @ApiModelProperty(name = "pcCategory", value = "地址类型；1-跳转 ，2-扫码", example = "1")
        private Integer pcCategory;
        @ApiModelProperty(name = "h5Category", value = "地址类型； 0-网页替换，1-跳转", example = "0")
        private Integer h5Category;
        @ApiModelProperty(name = "mailKefu", value = "客服邮箱", example = "BWG9488@gmail.com")
        private String mailKefu;
        @ApiModelProperty(name = "mailHelp", value = "帮助邮箱", example = "BestWinnerGaming666@gmail.com")
        private String mailHelp;
        @ApiModelProperty(name = "mailSuggest", value = "投诉邮箱", example = "BestWinnerGaming666@gmail.com")
        private String mailSuggest;
        @ApiModelProperty(name = "mobile", value = "合营部联系电话", example = "（+66）0990682746")
        private String mobile;
        @ApiModelProperty(name = "telegram", value = "合营部Telegram", example = "（+66）0990682746")
        private String telegram;
        @ApiModelProperty(name = "telegramUrl", value = "合营部TelegramURL", example = "https://lin.ee/catAQlC")
        private String telegramUrl;
        @ApiModelProperty(name = "skype", value = "合营部Skype", example = "BWG9488@gmail.com")
        private String skype;
        @ApiModelProperty(name = "skypeUrl", value = "合营部skypeUrl", example = "https://lin.ee/catAQlC")
        private String skypeUrl;
        @ApiModelProperty(name = "line", value = "合营部line", example = "https://lin.ee/E3ZeUfy,qrcode")
        private String line;
        @ApiModelProperty(name = "lineUrl", value = "合营部lineURL", example = "https://lin.ee/catAQlC")
        private String lineUrl;
        @ApiModelProperty(name = "whatsapp", value = "合营部Whatsapp", example = "09158711888")
        private String whatsapp;
        @ApiModelProperty(name = "whatsappUrl", value = "合营部WhatsappUrl", example = "https://lin.ee/catAQlC")
        private String whatsappUrl;
    }
}
