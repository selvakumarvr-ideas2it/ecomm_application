package com.product.catalog.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @CsvBindByName(column = "username")
    private String username;

    @CsvBindByName(column = "password")
    private String password;
} 