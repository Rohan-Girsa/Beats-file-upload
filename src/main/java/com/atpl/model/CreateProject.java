package com.atpl.model;

import lombok.Data;

import java.util.List;

@Data
public class CreateProject {
    private String name;
    private String shortName;
    private String projectUsers;
    private List<String> expenseApprovers;
}
