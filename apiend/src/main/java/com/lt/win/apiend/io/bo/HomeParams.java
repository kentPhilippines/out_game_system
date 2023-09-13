package com.lt.win.apiend.io.bo;

import com.lt.win.apiend.io.dto.platform.GameListDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author admin
 */
public interface HomeParams {
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
     * 首页接口出参
     */
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class IndexResDto {
        @ApiModelProperty(name = "imageServerUrl", value = "图片服务地址")
        private String imageServerUrl;
    }

    /**
     * 游戏列表(分组出参)
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class GameIndexResDto {
        @ApiModelProperty(name = "id", value = "游戏组ID", example = "1")
        public Integer id;

        @ApiModelProperty(name = "name", value = "游戏组名称", example = "体育赛事")
        public String name;

        @ApiModelProperty(name = "nameAbbr", value = "名称缩写", example = "体育")
        public String nameAbbr;

        @ApiModelProperty(name = "rebate", value = "最高反水", example = "1%")
        public String rebate;

        @ApiModelProperty(name = "list", value = "子游戏列表")
        public List<GameListDto> list;
    }

    /**
     * 公告入参
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class NoticeReqDto {
        @ApiModelProperty(name = "category", value = "通知种类:1-系统公告 2-站内信 3-体育预告 4-活动", example = "1")
        @NotNull
        public Integer category;
    }

    /**
     * 公告出参
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class NoticeResDto {
        @ApiModelProperty(name = "id", value = "ID", example = "101")
        public Integer id;
        @ApiModelProperty(name = "title", value = "标题", example = "红包客服")
        public String title;
        @ApiModelProperty(name = "content", value = "内容", example = "欢迎红包在线24小时QQ客服: 3300123123")
        public String content;
        @ApiModelProperty(name = "uids", value = "系统公告制定用户", example = "101,102,103")
        public String uids;
        @ApiModelProperty(name = "category", value = "类型:1-系统公告 2-站内信 3-体育预告 4-活动", example = "1")
        public Integer category;
        @ApiModelProperty(name = "createAt", value = "创建时间", example = "1587224410")
        public Integer createdAt;
    }

    /**
     * 游戏列表出参
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class GameListResDto {
        @ApiModelProperty(name = "id", value = "游戏组ID", example = "1")
        public Integer id;
        @ApiModelProperty(name = "name", value = "游戏组名称", example = "体育赛事")
        public String name;
        @ApiModelProperty(name = "nameAbbr", value = "名称缩写", example = "体育")
        public String nameAbbr;
        @ApiModelProperty(name = "list", value = "子游戏列表")
        public List<GameListDto> list;
    }

    /**
     * 公告入参
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class DelNoticeReqDto {
        @ApiModelProperty(name = "id", value = "公告", example = "1")
        @NotNull
        public String id;
    }

    /**
     * 公告入参
     */
    @Data
    class BannerListRes {
        @ApiModelProperty(name = "img", value = "轮播图地址", example = "http://")
        public String img;
        @ApiModelProperty(name = "href", value = "跳转地址", example = "https://static.xinbosports.com/group1/M03/00/04/wKh2AV-AYCeAAEHHAAnnHOXEpvE085.jpg")
        private String href;
    }
}
