package com.atpl.model;

import lombok.Data;

import java.util.List;

@Data
public class CreateProjectUsers {
    private int projectId;
    private List<String> users;
}
