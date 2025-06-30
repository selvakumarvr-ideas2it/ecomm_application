package com.product.catalog.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.product.catalog.model.Product;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

@Service
public class CsvService {
    private static final String CSV_FILE_PATH = "products.csv";

    public List<Product> readProductsFromCsv() {
        try (Reader reader = new FileReader(CSV_FILE_PATH)) {
            CsvToBean<Product> csvToBean = new CsvToBeanBuilder<Product>(reader)
                    .withType(Product.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV file: " + e.getMessage());
        }
    }

    public void writeProductsToCsv(List<Product> products) {
        try (Writer writer = new FileWriter(CSV_FILE_PATH)) {
            StatefulBeanToCsv<Product> beanToCsv = new StatefulBeanToCsvBuilder<Product>(writer)
                    .withApplyQuotesToAll(false)
                    .build();

            beanToCsv.write(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write to CSV file: " + e.getMessage());
        }
    }
} 