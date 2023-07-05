package com.rycoding.ecommerce.repository;

import com.rycoding.ecommerce.entities.Employee;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeRepository {
//    Persistence contexts are available in two types:
//    Transaction-scoped persistence context
//    Extended-scoped persistence context, flushes the cached entities into persistent storage when used within the transaction.

    @PersistenceContext(type = PersistenceContextType.TRANSACTION) // type = PersistenceContextType.EXTENDED
    private EntityManager em;

    @Transactional
    public void save(Employee employee) {
        em.persist(employee);
    }

    @Transactional
    public void remove(int id) {
        Employee employee = findById(id);
        em.remove(employee);
    }

    public Employee findById(int id) {
        return em.find(Employee.class, id);
    }

    public Employee findByJPQL(int id) {
        return em.createQuery("SELECT u FROM Employee AS u JOIN FETCH u.phones WHERE u.id=:id", Employee.class)
                .setParameter("id", id).getSingleResult();
    }

    public Employee findByEntityGraph(int id) {
        EntityGraph<Employee> entityGraph = em.createEntityGraph(Employee.class);
        entityGraph.addAttributeNodes("name", "phones");
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        return em.find(Employee.class, id, properties);
    }
}
