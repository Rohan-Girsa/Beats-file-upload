package com.atpl.service;

import com.atpl.res.StatusDescription;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectService {
    StatusDescription saveProjects(MultipartFile file);
    StatusDescription updateProjects(MultipartFile file);
}
