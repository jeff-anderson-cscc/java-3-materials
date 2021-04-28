package edu.cscc.hibernate;

import edu.cscc.hibernate.models.Company;
import edu.cscc.hibernate.models.InsurancePolicy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class InsurancePolicyRepository implements CrudRepository<InsurancePolicy, Long> {

    EntityManagerFactory entityManagerFactory;

    public InsurancePolicyRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public Optional<InsurancePolicy> findById(Long primaryKey) {
        InsurancePolicy insurancePolicy = entityManagerFactory.createEntityManager().find(
                InsurancePolicy.class, primaryKey);
        if (insurancePolicy != null) {
            return Optional.of(insurancePolicy);
        }
        return Optional.empty();
    }

    @Override
    public List<InsurancePolicy> findAll() {
        return entityManagerFactory.createEntityManager().createQuery(
                "select ip from InsurancePolicy ip" )
                .getResultList();
    }

    @Override
    public InsurancePolicy create(InsurancePolicy entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public void update(InsurancePolicy entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Long primaryKey) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        InsurancePolicy policy = entityManager.find(InsurancePolicy.class, primaryKey);
        if (policy == null) {
            throw new IllegalArgumentException("Entity does not exist");
        }
        entityManager.getTransaction().begin();
        entityManager.remove(policy);
        entityManager.getTransaction().commit();
    }
}
