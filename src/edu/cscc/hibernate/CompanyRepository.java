package edu.cscc.hibernate;

import edu.cscc.hibernate.models.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.Optional;

public class CompanyRepository implements CrudRepository<Company, Long> {

    EntityManagerFactory entityManagerFactory;

    public CompanyRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Optional<Company> findById(Long primaryKey) {
        Company company = entityManagerFactory.createEntityManager().find(Company.class, primaryKey);
        if (company != null) {
            return Optional.of(company);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Company> findAll() {
        return entityManagerFactory.createEntityManager().createQuery(
                "select c from Company c" )
                .getResultList();
    }

    // https://www.baeldung.com/hibernate-save-persist-update-merge-saveorupdate

    @Override
    public Company create(Company entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public void update(Company entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Long primaryKey) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Company company = entityManager.find(Company.class, primaryKey);
        if (company == null) {
            throw new IllegalArgumentException("Entity does not exist");
        }
        entityManager.getTransaction().begin();
        entityManager.remove(company);
        entityManager.getTransaction().commit();
    }


    public Iterable<Company> findByName(String companyName) {
        return entityManagerFactory.createEntityManager().createQuery(
                "select c from Company c where c.name like :name" )
                .setParameter("name", companyName)
                .getResultList();
    }

}
