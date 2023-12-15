package com.atpl.controller;


import com.atpl.helper.ExcelFileUpload;
import com.atpl.res.StatusDescription;
import com.atpl.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping(value = "/create-project", produces = "application/json")
    public ResponseEntity<StatusDescription> createProject(@RequestBody MultipartFile file){
        StatusDescription statusDescription = new StatusDescription();
        try {
            if(ExcelFileUpload.checkExcelFile(file)){
                statusDescription = projectService.saveProjects(file);
                return new ResponseEntity<StatusDescription>(statusDescription, HttpStatus.OK);
            } else{
                statusDescription.setStatusCode(400);
                statusDescription.setStatusMessage("Please upload Excel file");
                return new ResponseEntity<StatusDescription>(statusDescription, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusMessage("Internal Server Error");
            return new ResponseEntity<StatusDescription>(statusDescription, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update-project-hierarchy",produces = "application/json")
    public ResponseEntity<StatusDescription> updateProject(@RequestBody MultipartFile file){
        StatusDescription statusDescription = new StatusDescription();
        try {
            if(ExcelFileUpload.checkExcelFile(file)){
                statusDescription = projectService.updateProjects(file);
                return new ResponseEntity<StatusDescription>(statusDescription, HttpStatus.OK);
            }else{
                statusDescription.setStatusCode(400);
                statusDescription.setStatusMessage("Please upload Excel file");
                return new ResponseEntity<StatusDescription>(statusDescription, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusMessage("Internal Server Error");
            return new ResponseEntity<StatusDescription>(statusDescription, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
