package com.javainaction.entities;

import com.javainaction.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinancialTransaction {

    private final Currency currency;
    private final double value;
}
