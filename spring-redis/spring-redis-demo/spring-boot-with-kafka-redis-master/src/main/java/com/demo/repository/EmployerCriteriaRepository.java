package com.demo.repository;

import com.demo.model.Employer;
import com.demo.model.EmployerPage;
import com.demo.model.EmployerSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EmployerCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public EmployerCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Employer> findAllWithFilters(EmployerPage jobsPage,
                                             EmployerSearchCriteria jobsSearchCriteria){
        CriteriaQuery<Employer> criteriaQuery = criteriaBuilder.createQuery(Employer.class);
        Root<Employer> jobsRoot = criteriaQuery.from(Employer.class);
        Predicate predicate = getPredicate(jobsSearchCriteria, jobsRoot);
        criteriaQuery.where(predicate);
        setOrder(jobsPage, criteriaQuery, jobsRoot);

        TypedQuery<Employer> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(jobsPage.getPageNumber() * jobsPage.getPageSize());
        typedQuery.setMaxResults(jobsPage.getPageSize());

        Pageable pageable = getPageable(jobsPage);
        long employeesCount = getJobsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }

    private Predicate getPredicate (EmployerSearchCriteria jobsSearchCriteria, Root<Employer> jobsRoot){
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(jobsSearchCriteria.getPhoneNumber())){
            predicates.add(criteriaBuilder.equal(jobsRoot.get("phoneNumber"),jobsSearchCriteria.getPhoneNumber()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(EmployerPage jobsPage, CriteriaQuery<Employer> criteriaQuery, Root<Employer> jobsRoot) {
        if(jobsPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(jobsRoot.get(jobsPage.getSortBy())));
        }else{
            criteriaQuery.orderBy(criteriaBuilder.desc(jobsRoot.get(jobsPage.getSortBy())));
        }
    }

    private Pageable getPageable(EmployerPage jobsPage) {
        Sort sort = Sort.by(jobsPage.getSortDirection(), jobsPage.getSortBy());
        return PageRequest.of(jobsPage.getPageNumber(),jobsPage.getPageSize(), sort);
    }

    private long getJobsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Employer> countRoot = countQuery.from(Employer.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
