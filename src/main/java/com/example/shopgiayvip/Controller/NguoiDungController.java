package com.example.shopgiayvip.Controller;

import com.example.shopgiayvip.Entity.DiaChi;
import com.example.shopgiayvip.Entity.NguoiDung;
import com.example.shopgiayvip.Entity.NguoiDungDetails;
import com.example.shopgiayvip.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NguoiDungController {
    @Autowired
    GiayCTRepo giayCTRepo;
    @Autowired
    GioHangRepo gioHangRepo;
    @Autowired
    PhieuGiamGiaRepo phieuGiamGiaRepo;
    @Autowired
    GioHangChiTietRepo gioHangChiTietRepo;
    @Autowired
    com.example.shopgiayvip.Repository.totalRepo totalRepo;
    @Autowired
    DiaChiRepo diaChiRepo;
    @Autowired
    PhuongThucThanhToanRepo phuongThucThanhToanRepo;
    @Autowired
    NguoiDungRepo nguoiDungRepo;
    @Autowired
    HoaDonRepo hoaDonRepo;


    @GetMapping("/diaChiNguoiDung")
    public ResponseEntity<DiaChi> nguoiDungHienTai(Authentication authentication){
        if(authentication != null){
            NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
            NguoiDung nguoiDung = nguoiDungDetails.getUser();
            return ResponseEntity.ok(diaChiRepo.diaChiNguoiDung(nguoiDung.getId()));
        }
        return ResponseEntity.ok(null);
    }
    @GetMapping("/danhSachDiaChi")
    public ResponseEntity<List<DiaChi>> danhSachDiaChi(Authentication authentication){
        if(authentication != null){
            NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
            NguoiDung nguoiDung = nguoiDungDetails.getUser();
            return ResponseEntity.ok(diaChiRepo.danhSachDiaChi(nguoiDung.getId()));
        }
        return ResponseEntity.ok(null);
    }
    @GetMapping("/chonDiaChi")
    public ResponseEntity<DiaChi> chonDiaChi(@RequestParam("idDC") Integer idDC){
        DiaChi diaChi = diaChiRepo.getReferenceById(idDC);
        return ResponseEntity.ok(diaChi);
    }

}
