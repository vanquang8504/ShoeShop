package com.example.shopgiayvip.Repository;

import com.example.shopgiayvip.Entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NguoiDungRepo extends JpaRepository<NguoiDung, Integer> {
    NguoiDung findByEnmail(String email);
}
