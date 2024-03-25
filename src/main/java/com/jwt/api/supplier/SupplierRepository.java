package com.jwt.api.supplier;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SupplierRepository extends JpaRepository<Supplier,Integer> {
    Supplier getSupplierByBpsaddeml(String email);
}
