package com.jwt.api.claim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ClaimRepository extends JpaRepository<Claim, Integer> {
}
