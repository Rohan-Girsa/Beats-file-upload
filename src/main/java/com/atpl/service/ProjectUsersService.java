package com.atpl.service;

import com.atpl.res.StatusDescription;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectUsersService {
    void saveProjectUsers(int projectId, String projectUsers, List<String> users);
    void updateProjectUsers(int projectId, String email,int userHierarchy);
    StatusDescription appProjectUsers(MultipartFile file);
}
