package com.lt.win.backend.controller;

import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lt.win.backend.aop.annotation.UnCheckLog;
import com.lt.win.backend.aop.annotation.UnCheckToken;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.utils.FileUtils;
import com.lt.win.utils.IdUtils;
import com.lt.win.utils.components.response.CodeInfo;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * <p>
 * 文件上传下载控制类
 * </p>
 *
 * @author andy
 * @since 2020/6/1
 */
@Slf4j
@RestController
@RequestMapping("/v1/upload")
@Api(tags = "文件上传")
public class UploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @UnCheckLog
    @ApiOperationSupport(author = "Andy", order = 1)
    @PostMapping("/uploadFile")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public Result<FileInfo> uploadFile(@RequestParam("file") MultipartFile file) {
        FileInfo fileInfo = Optional.ofNullable(fileStorageService.of(file)
                //关联对象id，为了方便管理，不需要可以不写
                .setObjectId(IdUtils.getSnowFlakeId())
                //关联对象类型，为了方便管理，不需要可以不写
                .setObjectType(FileUtils.getFileExpandedName(file.getOriginalFilename()))
                .upload()).orElseThrow(() -> new BusinessException(CodeInfo.UPLOAD_IMAGE_FAIL));
        fileInfo.setThFilename(fileInfo.getBasePath() + fileInfo.getPath() + fileInfo.getFilename());
        return Result.ok(fileInfo);
    }
}
