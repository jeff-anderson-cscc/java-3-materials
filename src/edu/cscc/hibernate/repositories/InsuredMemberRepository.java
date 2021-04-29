package edu.cscc.hibernate.repositories;

import edu.cscc.hibernate.models.InsuredMember;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class InsuredMemberRepository implements CrudRepository<InsuredMember, Long> {

    EntityManagerFactory entityManagerFactory;

    public InsuredMemberRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Optional<InsuredMember> findById(Long primaryKey) {
        InsuredMember insuredMember = entityManagerFactory.createEntityManager().find(
                InsuredMember.class, primaryKey);
        if (insuredMember != null) {
            return Optional.of(insuredMember);
        }
        return Optional.empty();
    }

    @Override
    public List<InsuredMember> findAll() {
        return entityManagerFactory.createEntityManager().createQuery(
                "select im from InsuredMember im" )
                .getResultList();
    }

    @Override
    public InsuredMember create(InsuredMember entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public void update(InsuredMember entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Long primaryKey) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        InsuredMember insuredMember = entityManager.find(
                InsuredMember.class, primaryKey);
        if (insuredMember == null) {
            throw new IllegalArgumentException("Entity does not exist");
        }
        entityManager.getTransaction().begin();
        entityManager.remove(insuredMember);
        entityManager.getTransaction().commit();
    }
}