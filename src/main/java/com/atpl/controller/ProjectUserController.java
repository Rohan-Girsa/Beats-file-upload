package com.atpl.controller;


import com.atpl.helper.ExcelFileUpload;
import com.atpl.res.StatusDescription;
import com.atpl.service.ProjectUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/project-users")
@CrossOrigin("*")
public class ProjectUserController {
    @Autowired
    private ProjectUsersService projectUsersService;

    @PostMapping(value = "/add-project-users", produces = "application/json")
    public ResponseEntity<StatusDescription> addProjectUsers(@RequestBody MultipartFile file){
        StatusDescription statusDescription = new StatusDescription();
        try {
            if(ExcelFileUpload.checkExcelFile(file)){
                statusDescription = projectUsersService.appProjectUsers(file);
                return new ResponseEntity<StatusDescription>(statusDescription, HttpStatus.OK);
            }else{
                statusDescription.setStatusCode(400);
                statusDescription.setStatusMessage("Please upload Excel file");
                return new ResponseEntity<StatusDescription>(statusDescription, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusMessage("Internal Server Error");
            return new ResponseEntity<StatusDescription>(statusDescription, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
