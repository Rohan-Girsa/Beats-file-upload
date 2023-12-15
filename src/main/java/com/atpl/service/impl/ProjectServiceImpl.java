package com.atpl.service.impl;

import com.atpl.entity.Projects;
import com.atpl.helper.ExcelFileUpload;
import com.atpl.repo.ProjectsRepo;
import com.atpl.model.CreateProject;
import com.atpl.model.ProjectHierarchy;
import com.atpl.res.StatusDescription;
import com.atpl.service.ProjectService;
import com.atpl.service.ProjectUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectsRepo projectsRepo;
    @Autowired
    private ProjectUsersService projectUsersService;
    @Value("${ProjectUserId}")
    private Integer projectUserId;
    @Value("${CompanyId}")
    private Integer companyId;
    @Override
    public StatusDescription saveProjects(MultipartFile file) {
        log.info("<--- Starting Project Creation --->");
        int insertCount = 0;
        int duplicateCount = 0;
        StatusDescription statusDescription = new StatusDescription();
        try {
            List<CreateProject> lists = ExcelFileUpload.createProjectList(file.getInputStream());
            for(CreateProject list: lists){
                if(list != null){
                    int count = projectsRepo.countByName(list.getName());
                    if (count == 0){
                        Projects p = createNewProject(list.getName(),list.getShortName());
                        projectsRepo.save(p);
                        insertCount++;
                        Projects p1 = projectsRepo.findTopByName(list.getName());
                        log.info("Created Project "+p1.getName()+" with project ID "+p.getId());
                        projectUsersService.saveProjectUsers(p.getId(),list.getProjectUsers(),list.getExpenseApprovers());
                    }else{
                        log.info("Project "+list.getName()+" already exists in database");
                        duplicateCount++;
                    }
                }else {
                    System.out.println("We got a Null List");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("Error During saving projects "+e.getMessage());
            statusDescription.setStatusCode(500);
            statusDescription.setStatusMessage("Failed");
            return statusDescription;
        }
        if(insertCount != 0){
            log.info("Sucess "+insertCount + " projects inserted in database." + "\n" + "Duplicate projects count: " + duplicateCount);
            statusDescription.setStatusCode(200);
            statusDescription.setStatusMessage("Success");
            statusDescription.setInsertCount(insertCount);
            statusDescription.setDuplicateCount(duplicateCount);
            return statusDescription;
        }else{
            statusDescription.setStatusCode(200);
            statusDescription.setStatusMessage("No Insertion in DB");
            statusDescription.setInsertCount(insertCount);
            statusDescription.setDuplicateCount(duplicateCount);
            return statusDescription;
        }
    }

    @Override
    public StatusDescription updateProjects(MultipartFile file) {
        log.info("<--- Project Approvers Updation Started --->");
        StatusDescription statusDescription = new StatusDescription();
        try {
            List<ProjectHierarchy> lists = ExcelFileUpload.updateProjectHierarchy(file.getInputStream());
            for(ProjectHierarchy list: lists){
                if(list != null){
                    Projects p = projectsRepo.findTopById(list.getId());
                    if(p != null){
                        projectUsersService.updateProjectUsers(p.getId(),list.getEmail(),list.getHierarchy());
                    }
                }
            }
            statusDescription.setStatusCode(200);
            statusDescription.setStatusMessage("Success");
            return statusDescription;
        }catch (Exception e){
            e.printStackTrace();
            log.info("Error During Project Approvers Updation: "+e.getMessage());
            statusDescription.setStatusCode(500);
            statusDescription.setStatusMessage("Failed");
            return statusDescription;
        }
    }

    private Projects createNewProject(String name, String shortName) {
        Projects p = new Projects();
        p.setUserId(projectUserId);
        p.setCompanyId(companyId);
        p.setInitiator(projectUserId);
        p.setDefaultAssign(projectUserId);
        p.setManager(projectUserId);
        p.setName(name);
        p.setShortName(shortName);
        p.setDescription(name);
        p.setWorkflowId(0);
        p.setProjectType((byte) 1);
        p.setIsactive((byte) 1);
        p.setDtCreated(new Date());
        p.setUniqId(projectsRepo.genCompanyUniqId());
        return p;
    }
}
