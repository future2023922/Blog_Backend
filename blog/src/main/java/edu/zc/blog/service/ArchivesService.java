package edu.zc.blog.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: keeplooking
 * @since: 2022/02/28 - 3:05
 */
public interface ArchivesService {
    /**
     * 博客归档数据
     * @return map
     */
    Map<String,Object> getArchivesList(HttpServletRequest request);
}
