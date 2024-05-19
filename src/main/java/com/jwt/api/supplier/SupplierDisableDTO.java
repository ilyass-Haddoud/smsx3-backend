package com.jwt.api.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class SupplierDisableDTO {

    private String bpsaddeml;

    private boolean disactivated;

    public String getBpsaddeml() {
        return bpsaddeml;
    }

    public void setBpsaddeml(String bpsaddeml) {
        this.bpsaddeml = bpsaddeml;
    }

    public boolean isDisactivated() {
        return disactivated;
    }

    public void setDisactivated(boolean disactivated) {
        this.disactivated = disactivated;
    }

}
