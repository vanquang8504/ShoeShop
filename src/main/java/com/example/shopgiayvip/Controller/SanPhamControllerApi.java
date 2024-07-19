package com.example.shopgiayvip.Controller;

import com.example.shopgiayvip.Entity.GiayChiTiet;
import com.example.shopgiayvip.Repository.GiayCTRepo;
import com.example.shopgiayvip.Repository.HangGiayRepo;
import com.example.shopgiayvip.Repository.MauSacRepo;
import com.example.shopgiayvip.Repository.SizeGiayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SanPhamControllerApi {
    @Autowired
    MauSacRepo mauSacRepo;
    @Autowired
    SizeGiayRepo sizeGiayRepo;
    @Autowired
    HangGiayRepo hangGiayRepo;
    @Autowired
    GiayCTRepo giayCTRepo;

    @GetMapping("/admin/getMauSac")
    public ResponseEntity<?> getMauSac(){
        return ResponseEntity.ok(mauSacRepo.findAll());
    }
    @GetMapping("/admin/getSize")
    public ResponseEntity<?> getSize(){
        return ResponseEntity.ok(sizeGiayRepo.findAll());
    }
    @GetMapping("/admin/getHangGiay")
    public ResponseEntity<?> getHangGiay(){
        return ResponseEntity.ok(hangGiayRepo.findAll());
    }
    @GetMapping("/admin/getSP")
    public ResponseEntity<?> getSP(){
        return ResponseEntity.ok(giayCTRepo.findAll());
    }
    @GetMapping("/admin/xoaSP")
    public ResponseEntity<?> xoaSP(@RequestParam("id") Integer id){
        GiayChiTiet giayChiTiet = giayCTRepo.getReferenceById(id);
        giayChiTiet.setTrangThai("Ngừng hoạt động");
        return ResponseEntity.ok(giayCTRepo.findByTrangThai("Còn hàng"));
    }
    @PostMapping("/admin/add")
    public ResponseEntity<?> add(GiayChiTiet giayChiTiet,
                                 @RequestParam("idhang") Integer idHang,
                                 @RequestParam("idSize") Integer idSize,
                                 @RequestParam("idMau") Integer idMau,
                                 @RequestParam("soluong") Integer soLuong,
                                 @RequestParam("tenGiay") String tenGiay,
                                 @RequestParam("giaBan") Integer giaBan
                                 ){
        giayChiTiet.setTrangThai("Còn hàng");
        giayChiTiet.setHangGiay(hangGiayRepo.getReferenceById(idHang));
        giayChiTiet.setSizeGiay(sizeGiayRepo.getReferenceById(idSize));
        giayChiTiet.setMauSacGiay(mauSacRepo.getReferenceById(idMau));
        giayChiTiet.setSoLuongTon(soLuong);
        giayChiTiet.setTenGiay(tenGiay);
        GiayChiTiet gct = giayCTRepo.findByTrangThaiAndHangGiayAndSizeGiayAndMauSacGiayAndTenGiay("Còn hàng",hangGiayRepo.getReferenceById(idHang),sizeGiayRepo.getReferenceById(idSize),mauSacRepo.getReferenceById(idMau),tenGiay);
        if(gct == null){
            return ResponseEntity.ok(giayCTRepo.save(giayChiTiet));
        }else {
            giayChiTiet.setId(gct.getId());
            giayChiTiet.setGiaBan(giaBan);
            gct.setSoLuongTon(gct.getSoLuongTon() + giayChiTiet.getSoLuongTon());
            gct.setGiaBan(gct.getGiaBan());
            giayCTRepo.save(gct);
            return ResponseEntity.ok("update");
        }
    }

}
