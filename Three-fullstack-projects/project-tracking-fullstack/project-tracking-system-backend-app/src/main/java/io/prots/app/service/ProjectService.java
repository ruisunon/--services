package io.prots.app.service;

import java.util.List;

import io.prots.app.model.dto.ChartData;
import io.prots.app.model.dto.ManagerProjectData;
import io.prots.app.model.dto.ProjectCommitInfoDTO;
import io.prots.app.model.dto.ProjectDTO;
import io.prots.app.model.dto.collection.DtoCollection;
import io.prots.app.model.entity.Project;

public interface ProjectService {
	
	DtoCollection<Project> findAll();
	Project findById(final Integer projectId);
	ProjectDTO findProjectDtoById(final Project project);
	Project save(final Project project);
	Project save(final ProjectDTO projectDTO);
	Project update(final Project project);
	Project update(final Integer projectId, final ProjectDTO projectDTO);
	void deleteById(final Integer projectId);
	List<ChartData> getProjectStatus();
	ProjectCommitInfoDTO findByUsernameAndProjectId(final String username, final Integer projectId);
	List<ManagerProjectData> findByEmployeeId(final int employeeId);
	
}








