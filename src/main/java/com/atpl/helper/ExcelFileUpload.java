package com.atpl.helper;

import com.atpl.model.CreateProject;
import com.atpl.model.CreateProjectUsers;
import com.atpl.model.ProjectHierarchy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class ExcelFileUpload {
    public static boolean checkExcelFile(MultipartFile file){
        String contentType = file.getContentType();
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType);
    }
    public static List<CreateProject> createProjectList(InputStream stream){
        log.info("<--- Reading Excel File for Project Creation  --->"+new Date());
        List<CreateProject> list = new ArrayList<>();
        try(Workbook workbook = new XSSFWorkbook(stream)) {
            for (Sheet sheet: workbook){
                String sheetName = sheet.getSheetName();
                log.info("Sheet Name: "+sheetName);
                for(int index = 1; index < sheet.getPhysicalNumberOfRows();index++){
                    Row row = sheet.getRow(index);
                    CreateProject project = new CreateProject();
                    List<String> data = new ArrayList<>();
                    for(Cell cell:row){
                        int columnIndex = cell.getColumnIndex();
                        switch(columnIndex){
                            case 0:
                                String val = cell.getStringCellValue();
                                if(isNotNullAndEmpty(val)){
                                    project.setName(val.replace(" ","_")+"_"+sheetName.replace(" ","_"));
                                    project.setShortName(val.substring(0,3).trim());
                                }
                                break;
                            case 1:
                                String val1 = cell.getStringCellValue();
                                if(isNotNullAndEmpty(val1)){
                                    project.setProjectUsers(val1);
                                }
                                break;
                            default:
                                String val2 = cell.getStringCellValue();
                                if(isNotNullAndEmpty(val2)){
                                    data.add(val2);
                                }
                                break;
                        }
                    }
                    project.setExpenseApprovers(data);
                    if (isNotNullAndEmpty(project.getName()) && isNotNullAndEmpty(project.getProjectUsers()) && !project.getExpenseApprovers().isEmpty()){
                        list.add(project);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static List<ProjectHierarchy> updateProjectHierarchy(InputStream stream){
        log.info("<--- Reading Excel File For Project Hierarchy Update  --->"+new Date());
        List<ProjectHierarchy> list = new ArrayList<>();
        try(Workbook workbook = new XSSFWorkbook(stream)){
            Sheet sheet = workbook.getSheet("data");
            for (int index = 1; index < sheet.getPhysicalNumberOfRows(); index++){
                Row row = sheet.getRow(index);
                ProjectHierarchy hierarchy = new ProjectHierarchy();
                for(Cell cell:row){
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex){
                        case 0:
                            int val = (int) cell.getNumericCellValue();
                            if(isNotNullAndEmpty(val)){
                                hierarchy.setId(val);
                            }
                            break;
                        case 1:
                            int val1 = (int) cell.getNumericCellValue();
                            if(isNotNullAndEmpty(val1)){
                                hierarchy.setHierarchy(val1);
                            }
                            break;
                        case 2:
                            String val2 = cell.getStringCellValue();
                            if(isNotNullAndEmpty(val2)){
                                hierarchy.setEmail(val2);
                            }
                            break;
                        default:
                            break;
                    }
                }
                if (isNotNullAndEmpty(hierarchy.getId()) && isNotNullAndEmpty(hierarchy.getHierarchy()) && isNotNullAndEmpty(hierarchy.getEmail())){
                    list.add(hierarchy);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static List<CreateProjectUsers> createProjectUsersList(InputStream stream){
        log.info("<--- Reading Excel File for Project Users Creation  --->"+new Date());
        List<CreateProjectUsers> list = new ArrayList<>();
        try(Workbook workbook = new XSSFWorkbook(stream)){
            Sheet sheet = workbook.getSheet("data");
            for (int index = 1; index < sheet.getPhysicalNumberOfRows(); index++){
                Row row = sheet.getRow(index);
                CreateProjectUsers projectUsers = new CreateProjectUsers();
                List<String> data = new ArrayList<>();
                for(Cell cell:row){
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex){
                        case 0:
                            int val = (int) cell.getNumericCellValue();
                            if(isNotNullAndEmpty(val)){
                                projectUsers.setProjectId(val);
                            }
                            break;
                        default:
                            String val1 = cell.getStringCellValue();
                            if(isNotNullAndEmpty(val1)){
                                data.add(val1);
                            }
                            break;
                    }
                }
                projectUsers.setUsers(data);
                if (isNotNullAndEmpty(projectUsers.getProjectId()) && !projectUsers.getUsers().isEmpty()){
                    list.add(projectUsers);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    private static boolean isNotNullAndEmpty(String value){
        return value != null && !value.isEmpty();
    }
    private static boolean isNotNullAndEmpty(int value){
        return value !=0;
    }
}
