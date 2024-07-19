package com.example.shopgiayvip.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CountBrand {
    @Id
    private String tenHang;
    private Integer soLuong;
}
