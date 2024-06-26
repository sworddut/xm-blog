package com.example.controller;

/*
@author TanCheng
@create 2024 -03 -28    
*/

import cn.hutool.core.lang.Dict;
import com.example.common.Result;
import com.example.entity.CommonFiles;
import com.example.entity.DiskFiles;
import com.example.service.CommonFilesService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 文件资料前端操作接口
 **/
@RestController
@RequestMapping("/commonFiles")
@Api(tags = "文件共享接口")
public class CommonFilesController {

    @Resource
    private CommonFilesService commonFilesService;

    /**
     * 新增
     */
    @ApiOperation(value = "新增文件")
    @PostMapping("/add")
    public Result add(MultipartFile file, String name, String folder, String category,Integer folderId) {
        commonFilesService.add(file, name, folder,category, folderId);
        return Result.success();
    }


    /*
    下载
    * */
    @ApiOperation(value = "下载文件")
    @GetMapping("/download/{flag}")
    public void download(@PathVariable String flag, HttpServletResponse response) {
        commonFilesService.download(flag, response);
    }

    /*文件预览*/
    @ApiOperation(value = "文件预览")
    @GetMapping("/preview/{id}")
    public void preview(@PathVariable Integer id, HttpServletResponse response) {
        commonFilesService.preview(id, response);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "文件删除")
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        commonFilesService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        commonFilesService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改文件")
    @PutMapping("/update")
    public Result updateById(@RequestBody CommonFiles commonFiles) {
        commonFilesService.updateById(commonFiles);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        CommonFiles commonFiles = commonFilesService.selectById(id);
        return Result.success(commonFiles);
    }

    /**
     * 查询所有
     */
    @ApiOperation(value = "查询所有文件")
    @GetMapping("/selectAll")
    public Result selectAll(CommonFiles commonFiles) {
        List<CommonFiles> list = commonFilesService.selectAll(commonFiles);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询文件")
    @GetMapping("/selectPage")
    public Result selectPage(CommonFiles commonFiles,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<CommonFiles> page = commonFilesService.selectPage(commonFiles, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询所有父级的目录名称
     */
    @ApiOperation(value = "查询所有父级的目录")
    @GetMapping("/selectFolders")
    public Result selectFolders(Integer folderId) {
        List<CommonFiles> list = new ArrayList<>();
        if (folderId == null) {
            return Result.success(list);
        }
        commonFilesService.selectFolderNames(folderId, list);
        Collections.reverse(list);
        return Result.success(list);
    }


}
