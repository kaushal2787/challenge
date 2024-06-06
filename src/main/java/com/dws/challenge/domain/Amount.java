package com.dws.challenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Amount {

    @NotNull
    @Min(value = 0, message = "amount to be transfered must be positive.")
    private BigDecimal amount;

    @JsonCreator
    public Amount(@JsonProperty("amount") BigDecimal amount) {
        this.amount = amount;
    }

    public @NotNull @Min(value = 0, message = "amount to be transfered must be positive.") BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NotNull @Min(value = 0, message = "amount to be transfered must be positive.") BigDecimal amount) {
        this.amount = amount;
    }
}
