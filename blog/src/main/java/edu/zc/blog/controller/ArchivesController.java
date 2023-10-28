package edu.zc.blog.controller;

import edu.zc.blog.service.ArchivesService;
import edu.zc.globalException.Results;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: keeplooking
 * @since: 2022/02/28 - 3:11
 */

@Api(tags = "归档模块")
@RequestMapping("/archives")
@RestController
@CrossOrigin
public class ArchivesController {

    @Resource
    private ArchivesService archivesService;

    @GetMapping("/getArchivesList")
    public Results getArchivesList(HttpServletRequest request) {
        return Results.success().data(archivesService.getArchivesList(request));
    }
}
