package com.reactspring.proje.controller;


import com.reactspring.proje.entity.Project;
import com.reactspring.proje.service.MapValidationErrorService;
import com.reactspring.proje.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;


    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapvalidationcheckingerror(result);
        if (errorMap != null) {
            return errorMap;
        }

        Project projectNew = projectService.saveorUpdateproject(project);
        return new ResponseEntity<Project>(projectNew, HttpStatus.CREATED);


    }

    @GetMapping("/{projectId}")
     public  ResponseEntity<?> getProjectById(@PathVariable String projectId) {

        Project project = projectService.findProjectByProjectIdentifier(projectId);
        return new  ResponseEntity<Project>(project,HttpStatus.OK);


    }

    @GetMapping("/all")
    public Iterable<Project>  getAllProjects(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?>  deleteProject(@PathVariable String projectId){
        projectService.deleteProjectByIdentifier(projectId);

        return new ResponseEntity<String>("Project with id ' "+ projectId+"' was deleted", HttpStatus.OK);


    }

}
