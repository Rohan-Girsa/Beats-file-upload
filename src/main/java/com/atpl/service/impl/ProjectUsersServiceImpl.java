package com.atpl.service.impl;

import com.atpl.entity.ProjectUsers;
import com.atpl.entity.Users;
import com.atpl.helper.ExcelFileUpload;
import com.atpl.model.CreateProjectUsers;
import com.atpl.repo.ProjectUsersRepo;
import com.atpl.repo.UserRepo;
import com.atpl.res.StatusDescription;
import com.atpl.service.ExpenseApproversService;
import com.atpl.service.ProjectUsersService;
import com.atpl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProjectUsersServiceImpl implements ProjectUsersService {
    @Value("${CompanyId}")
    private int companyId;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProjectUsersRepo projectUsersRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private ExpenseApproversService expenseApproversService;
    @Override
    public void saveProjectUsers(int projectId, String projectUsers, List<String> users) {
        log.info("<--- Creating Project Users --->");
        int hierarchy = 1;
        int count = 0;
        try {
            String[] pUsers = projectUsers.split(",");
            for(String u: pUsers){
                Users user = userRepo.findTopByEmail(u.trim());
                if(user == null){
                    userService.saveUsers(u);
                    Users users1 = userRepo.findTopByEmail(u.trim());
                    count = findProjectUsers(projectId, users1.getId());
                }else{
                    count = findProjectUsers(projectId, user.getId());
                }
            }
            for(String email:users){
                Users user = userRepo.findTopByEmail(email.trim());
                if(user == null){
                    userService.saveUsers(email);
                    Users u = userRepo.findTopByEmail(email.trim());
                    count = findProjectUsers(projectId,u.getId());
                    expenseApproversService.saveExpenseApprovers(projectId,u.getId(),hierarchy);
                }else{
                    count = findProjectUsers(projectId, user.getId());
                    expenseApproversService.saveExpenseApprovers(projectId,user.getId(),hierarchy);
                }
                hierarchy++;
            }
            log.info(new Date() +" TOTAL Users added into project "+count);
        }catch (Exception e){
            log.info("Project Users Exception "+e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void updateProjectUsers(int projectId, String email, int userHierarchy) {
        log.info("<--- Updating Project Users --->");
        int count = 0;
        try {
            Users user = userRepo.findTopByEmail(email.trim());
            if(user == null){
                userService.saveUsers(email);
                Users u = userRepo.findTopByEmail(email.trim());
                count = findProjectUsers(projectId,u.getId());
                expenseApproversService.updateExpenseApprovers(projectId,u.getId(),userHierarchy);
            } else{
                count = findProjectUsers(projectId,user.getId());
                expenseApproversService.updateExpenseApprovers(projectId,user.getId(),userHierarchy);
            }
            log.info(new Date() +" TOTAL Users added into project "+count);
        }catch (Exception e){
            log.info("Project Users Exception "+e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public StatusDescription appProjectUsers(MultipartFile file) {
        log.info("<--- Adding Project Users --->");
        StatusDescription statusDescription = new StatusDescription();
        int insertCount = 0;
        try {
            List<CreateProjectUsers> lists = ExcelFileUpload.createProjectUsersList(file.getInputStream());
            for(CreateProjectUsers list: lists){
                if(list != null){
                    for(String str: list.getUsers()){
                        Users user = userRepo.findTopByEmail(str.trim());
                        if(user != null){
                            insertCount += findProjectUsers(list.getProjectId(), user.getId());
                            log.info("User "+user.getEmail()+" added into project "+list.getProjectId());
                        }else{
                            userService.saveUsers(str.trim());
                            Users u = userRepo.findTopByEmail(str.trim());
                            insertCount += findProjectUsers(list.getProjectId(), u.getId());
                            log.info("User "+u.getEmail()+" added into project "+list.getProjectId());
                        }
                    }
                }
            }
            statusDescription.setStatusCode(200);
            statusDescription.setStatusMessage("Success");
            statusDescription.setInsertCount(insertCount);
        } catch (Exception e){
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusMessage("Failed");
            return statusDescription;
        }
        return statusDescription;
    }

    private ProjectUsers createProjectUser(int projectId, int userId) {
        ProjectUsers p = new ProjectUsers();
        p.setProjectId(projectId);
        p.setCompanyId(companyId);
        p.setUserId(userId);
        p.setIstype((byte) 2);
        p.setDefaultEmail((byte) 1);
        p.setDtVisited(new Date());
        p.setRoleId(3);
        return p;
    }

    private int findProjectUsers(int projectId, int userId) {
        ProjectUsers pu = projectUsersRepo.findTopByProjectIdAndUserId(projectId,userId);
        int count = 0;
        if(pu == null){
            ProjectUsers p = createProjectUser(projectId, userId);
            projectUsersRepo.save(p);
            log.info("User "+userId+" added into project "+projectId);
            count++;
        }else{
            log.info("User "+userId+" already in project "+projectId);

        }
        return count;
    }
}
