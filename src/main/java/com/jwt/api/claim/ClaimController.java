package com.jwt.api.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("claims")
public class ClaimController {
    private final ClaimService claimService;

    @Autowired
    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @CrossOrigin
    @PostMapping("{supplier_id}/addClaim")
    public ResponseEntity<String> addClaim(@PathVariable Integer supplier_id, @RequestBody Claim claim)
    {
        return this.claimService.addClaim(claim.getEntete(),claim.getMessage(),supplier_id);
    }
}
