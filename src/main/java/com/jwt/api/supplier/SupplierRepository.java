package com.jwt.api.supplier;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
    Supplier getSupplierByBpsaddeml(String bpsaddeml);
    Supplier getSupplierByBpsnum(String bpsnum);
}
