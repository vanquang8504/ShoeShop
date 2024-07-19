package com.example.shopgiayvip.Repository;

import com.example.shopgiayvip.dto.CountBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountRepo extends JpaRepository<CountBrand, Integer> {
        @Query(value = "select ten_hang, COUNT(chi_tiet_giay.id) as 'so_luong' from hang_giay join chi_tiet_giay on hang_giay.id = chi_tiet_giay.id_hang_giay group by ten_hang", nativeQuery = true)
    List<CountBrand> list();
}
