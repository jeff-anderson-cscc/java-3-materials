package edu.cscc.hibernate.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "insurance_policies")
public class InsurancePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private InsurancePolicyType type;
    @ManyToOne
    private Company company;

    public InsurancePolicy() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InsurancePolicyType getType() {
        return type;
    }

    public void setType(InsurancePolicyType type) {
        this.type = type;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        InsurancePolicy insurancePolicy = (InsurancePolicy) obj;
        return Objects.equals(id, insurancePolicy.id);
    }

    @Override
    public String toString() {
        return "InsurancePolicy{" +
                "id=" + id +
                ", type='" + type.name() + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
