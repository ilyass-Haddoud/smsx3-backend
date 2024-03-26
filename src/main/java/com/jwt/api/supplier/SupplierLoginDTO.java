package com.jwt.api.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class SupplierLoginDTO {
    @NotBlank(message = "email field cant be blank")
    @NotNull(message = "email field cant be null")
    @Email(message = "Not a valid email address")
    private String bpsaddeml;
    @NotBlank(message = "password field cant be blank")
    @NotNull(message = "password field cant be null")
    private String bpspasse;

    public String getBpsaddeml() {
        return bpsaddeml;
    }

    public void setBpsaddeml(String bpsaddeml) {
        this.bpsaddeml = bpsaddeml;
    }

    public String getBpspasse() {
        return bpspasse;
    }

    public void setBpspasse(String bpspasse) {
        this.bpspasse = bpspasse;
    }
    @Override
    public String toString() {
        return "SupplierLoginDTO{" +
                "bpsaddeml='" + bpsaddeml + '\'' +
                ", bpspasse='" + bpspasse + '\'' +
                '}';
    }
}
