package com.example.shopgiayvip.Repository;

import com.example.shopgiayvip.dto.total;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface totalRepo extends JpaRepository<total, Integer> {
    @Query(value = "select\n" +
            "IIF(muc_giam_gia<=100,(select sum(tong_tien) from gio_hang_chi_tiet where id_gio_hang = ?1 and trang_thai like '%true%')/muc_giam_gia,muc_giam_gia) as 'muc_giam_gia',\n" +
            "IIF(muc_giam_gia <=100,(select sum(tong_tien) from gio_hang_chi_tiet where id_gio_hang = ?1 and trang_thai like '%true%')-(select sum(tong_tien) from gio_hang_chi_tiet where id_gio_hang = ?1 and trang_thai like '%true%')/muc_giam_gia,\n" +
            "(select sum(tong_tien) from gio_hang_chi_tiet where id_gio_hang = ?1 and trang_thai like '%true%')-muc_giam_gia) as 'tong_tien' from phieu_giam_gia where id = ?2", nativeQuery = true)
    total tongTienCanTra(Integer idGioHang, Integer idPGG);
}
