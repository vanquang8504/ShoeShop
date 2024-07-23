package com.example.shopgiayvip.Controller;

import com.example.shopgiayvip.Entity.*;
import com.example.shopgiayvip.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GioHangController {
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

    @PostMapping("/checkToAdd")
    public ResponseEntity<String> checkToAdd(Authentication authentication, @RequestParam("idSP") Integer id){
        GiayChiTiet giayChiTiet = giayCTRepo.getReferenceById(id);
        if(authentication != null){
            NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
            NguoiDung nguoiDung = nguoiDungDetails.getUser();
            GioHang gioHangHienTai = gioHangRepo.gioHangHienTai(nguoiDung.getId());
            GioHangChiTiet sanPhamGioHang = gioHangChiTietRepo.sanPhamGioHang(id,gioHangHienTai.getId());
            System.out.println(gioHangHienTai.getId());
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setGioHang(gioHangHienTai);
            gioHangChiTiet.setGiaBan(giayChiTiet.getGiaBan());
            gioHangChiTiet.setGiayCT(giayChiTiet);
            gioHangChiTiet.setTrangThai("true");
            if(sanPhamGioHang == null){
                gioHangChiTiet.setGiaBan(giayChiTiet.getGiaBan());
                gioHangChiTiet.setSoLuongMua(1);
                gioHangChiTiet.setTongTien(giayChiTiet.getGiaBan() * gioHangChiTiet.getSoLuongMua());
                gioHangChiTietRepo.save(gioHangChiTiet);
            }else {
                gioHangChiTiet.setId(sanPhamGioHang.getId());
                gioHangChiTiet.setSoLuongMua(sanPhamGioHang.getSoLuongMua() + 1);
                gioHangChiTiet.setTongTien(gioHangChiTiet.getGiaBan() * gioHangChiTiet.getSoLuongMua());
                gioHangChiTietRepo.save(gioHangChiTiet);
            }
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.ok("Fail");
    }

    @GetMapping("/cartt")
    public ResponseEntity<List<GioHangChiTiet>> cartPage(Authentication authentication){
        if (authentication != null){
            NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
            NguoiDung nguoiDung = nguoiDungDetails.getUser();
            GioHang gioHangHienTai = gioHangRepo.gioHangHienTai(nguoiDung.getId());
            return ResponseEntity.ok(gioHangChiTietRepo.listGioHangChiTiet(gioHangHienTai.getId())); // cần update sau khi xong
        }
        return ResponseEntity.ok(null);
    }

    @PutMapping("/tangSL")
    public ResponseEntity<GioHangChiTiet> tangSL(@RequestParam("idGH") Integer id,@RequestParam("idSP") Integer idSP,@RequestParam("roll") String roll, Authentication authentication){
        NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
        NguoiDung nguoiDung = nguoiDungDetails.getUser();
        GioHang gioHangHienTai = gioHangRepo.gioHangHienTai(nguoiDung.getId()); //cần update sau khi xong
        GioHangChiTiet sanPhamCanUpdate = gioHangChiTietRepo.sanPhamCanUpdate(idSP,id);
        if(roll.equals("tang") && sanPhamCanUpdate.getSoLuongMua() >= 1){
            sanPhamCanUpdate.setSoLuongMua(sanPhamCanUpdate.getSoLuongMua()+1);
            sanPhamCanUpdate.setTongTien(sanPhamCanUpdate.getGiaBan() * sanPhamCanUpdate.getSoLuongMua());
            gioHangChiTietRepo.save(sanPhamCanUpdate);
        }else {
            if(sanPhamCanUpdate.getSoLuongMua() > 1){
                sanPhamCanUpdate.setSoLuongMua(sanPhamCanUpdate.getSoLuongMua()-1);
                sanPhamCanUpdate.setTongTien(sanPhamCanUpdate.getGiaBan() * sanPhamCanUpdate.getSoLuongMua());
                gioHangChiTietRepo.save(sanPhamCanUpdate);
            }
        }
        return ResponseEntity.ok(sanPhamCanUpdate);
    }

    @GetMapping("/tong-tien")
    public ResponseEntity<Integer> tongTien(Authentication authentication){
        if(authentication != null){
            NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
            NguoiDung nguoiDung = nguoiDungDetails.getUser();
            return ResponseEntity.ok(gioHangChiTietRepo.tongTien(nguoiDung.getId()));
        }
        return ResponseEntity.ok(gioHangChiTietRepo.tongTien(-1)); // cần update sau khi xong
    }
    @DeleteMapping("/deleteGioHangCT")
    public ResponseEntity<String> deleteSanPhamGioHang(@RequestParam("idGHCT") Integer idGHCT){
        GioHangChiTiet gioHangChiTietCanXoa = gioHangChiTietRepo.getReferenceById(idGHCT);
        gioHangChiTietRepo.delete(gioHangChiTietCanXoa);
        return ResponseEntity.ok("success");
    }
    @Autowired
    CountRepo countRepo;
    @GetMapping("/countBrand")
    public ResponseEntity<?> getCount(){
        return ResponseEntity.ok(countRepo.list());
    }

}
