package io.prots.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.prots.app.model.dto.ChartData;
import io.prots.app.model.dto.ManagerProjectData;
import io.prots.app.model.dto.ProjectCommitInfoDTO;
import io.prots.app.model.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	@Query(name = "List.getProjectStatus", nativeQuery = true)
	public abstract List<ChartData> getProjectStatus();
	
	@Query(name = "ProjectCommitInfoDTO.findByUsernameAndProjectId", nativeQuery = true)
	public abstract Optional<ProjectCommitInfoDTO> findByUsernameAndProjectId(@Param("username") final String username, @Param("projectId") final int projectId);
	
	@Query(name = "List<ManagerProjectData>.findByEmployeeId", nativeQuery = true)
	public abstract List<ManagerProjectData> findByEmployeeId(@Param("employeeId") final int employeeId);
	
}








