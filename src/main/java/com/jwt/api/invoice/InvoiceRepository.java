package com.jwt.api.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
