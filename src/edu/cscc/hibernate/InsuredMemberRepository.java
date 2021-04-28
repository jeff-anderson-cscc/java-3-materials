package edu.cscc.hibernate;

import edu.cscc.hibernate.models.InsurancePolicy;
import edu.cscc.hibernate.models.InsuredMember;

import javax.persistence.EntityManagerFactory;
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
    public Iterable<InsuredMember> findAll() {
        return entityManagerFactory.createEntityManager().createQuery(
                "select im from InsuredMember im" )
                .getResultList();
    }

    @Override
    public InsuredMember create(InsuredMember entity) {
        return null;
    }

    @Override
    public void update(InsuredMember entity) {

    }

    @Override
    public void delete(Long primaryKey) {

    }
}
