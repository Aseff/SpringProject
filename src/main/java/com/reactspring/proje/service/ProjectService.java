package com.reactspring.proje.service;

import com.reactspring.proje.entity.Project;
import com.reactspring.proje.exception.ProjectIdException;
import com.reactspring.proje.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveorUpdateproject(Project project) {

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);

        } catch (Exception e) {
            throw new ProjectIdException("Project ID ' " + project.getProjectIdentifier().toUpperCase() + " ' was already used");

        }

    }

    public Project findProjectByProjectIdentifier(String projectId) {

        Project project = projectRepository.findProjectByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project ID'" + projectId + "' is not exist");

        }
        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project=projectRepository.findProjectByProjectIdentifier(projectId.toUpperCase());

        if(project==null){
            throw new ProjectIdException("Cannot Project with id ' " + projectId+ "'.The project does not exist");
        }
        projectRepository.delete(project);
    }


}
