use master
drop database ShopGiay


create database ShopGiay

use ShopGiay

CREATE TABLE danh_muc (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten NVARCHAR(255) ,
    mo_ta TEXT,
    ngay_tao DATETIME,
    ngay_cap_nhat DATETIME,
	trang_thai NVARCHAR(50)
);

CREATE TABLE de_giay (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten NVARCHAR(255),
  loai NVARCHAR(50),
  do_day FLOAT,
  ngay_tao DATETIME,
  ngay_sua DATETIME,
  trang_thai NVARCHAR(50)
);

CREATE TABLE mau_sac_giay (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten_mau NVARCHAR(100),
  trang_thai NVARCHAR(50),
  ngay_tao DATETIME,
  ngay_sua DATETIME
);

CREATE TABLE hang_giay (
  id INT IDENTITY(1,1) PRIMARY KEY,
  ten_hang NVARCHAR(100),
  trang_thai NVARCHAR(50),
  ngay_tao DATE,
  ngay_sua DATE
);

CREATE TABLE size_giay (
  id INT IDENTITY(1,1) PRIMARY KEY,
  size_so INT,
  trang_thai NVARCHAR(50),
  ngay_tao DATETIME,
  ngay_sua DATETIME
);

CREATE TABLE giay (
  id INT IDENTITY(1,1) PRIMARY KEY,
  id_danh_muc INT,
  ma_giay NVARCHAR(20),
  ten_giay NVARCHAR(100),
  so_luong INT,
  trang_thai NVARCHAR(50),
  ngay_tao DATETIME,
  ngay_sua DATETIME,
   FOREIGN KEY (id_danh_muc) REFERENCES danh_muc(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE dot_giam_gia (
    id INT IDENTITY(1,1) PRIMARY KEY,
	ten_dot_giam_gia NVARCHAR(255),
    thoi_gian_bat_dau DATETIME,
    thoi_gian_ket_thuc DATETIME,
	trang_thai NVARCHAR(50),
    giam_gia INT CHECK (giam_gia >= 0)
);


CREATE TABLE chi_tiet_giay (
  id INT IDENTITY(1,1) PRIMARY KEY,
  id_giay INT,
  id_size_giay INT,
  id_de_giay INT,
  id_mau_sac_giay INT,
  id_hang_giay INT,
  ten_giay NVARCHAR(255),
  gia_ban INT,
  gia_giam INT,
  gia_moi INT,
  so_luong_ton INT NOT NULL,
  anh NVARCHAR(50),
  trang_thai NVARCHAR(50),
  ngay_tao DATETIME,
  ngay_sua DATETIME,
  FOREIGN KEY (id_giay) REFERENCES giay(id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (id_size_giay) REFERENCES size_giay(id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (id_de_giay) REFERENCES de_giay(id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (id_mau_sac_giay) REFERENCES mau_sac_giay(id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (id_hang_giay) REFERENCES hang_giay(id) ON UPDATE CASCADE ON DELETE CASCADE,
);

CREATE TABLE anh_giay_chi_tiet (
    id INT IDENTITY(1,1) PRIMARY KEY,
	id_giay_chi_tiet INT,
	hinh_anh NVARCHAR(255),
	FOREIGN KEY (id_giay_chi_tiet) REFERENCES chi_tiet_giay(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE chi_tiet_dot_giam_gia (
    id INT IDENTITY(1,1) PRIMARY KEY,
	id_dot_giam_gia INT,
	id_chi_tiet_giay INT,
	ten_giay NVARCHAR(255),
	gia_ban INT,
	giam_gia INT,
	gia_moi INT,
	trang_thai NVARCHAR(50),
	FOREIGN KEY (id_dot_giam_gia) REFERENCES dot_giam_gia(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (id_chi_tiet_giay) REFERENCES chi_tiet_giay(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE chuc_vu (
    id INT IDENTITY(1,1) PRIMARY KEY,
    chuc_vu NVARCHAR(255),
    trang_thai BIT,
	ngay_tao DATETIME,
	ngay_sua DATETIME
);

CREATE TABLE nguoi_dung (
    id INT IDENTITY(1,1) PRIMARY KEY,
	id_chuc_vu INT,
    ho_ten NVARCHAR(100),
    email NVARCHAR(100) UNIQUE,
    mat_khau NVARCHAR(100),
    so_dien_thoai NVARCHAR(20),
    anh NVARCHAR(255),
    trang_thai BIT,
    ngay_tao DATETIME,
    ngay_sua DATETIME,
    FOREIGN KEY (id_chuc_vu) REFERENCES chuc_vu(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE dia_chi(
	id INT IDENTITY(1,1) PRIMARY KEY,
	id_nguoi_dung INT,
	dia_chi Nvarchar(255),
	FOREIGN KEY (id_nguoi_dung) REFERENCES nguoi_dung(id) ON UPDATE CASCADE ON DELETE CASCADE
)

CREATE TABLE binh_luan(
	id_giay_chi_tiet INT PRIMARY KEY,
	id_nguoi_dung INT,
	binh_luan TEXT,
	FOREIGN KEY (id_giay_chi_tiet) REFERENCES chi_tiet_giay(id),
	FOREIGN KEY (id_nguoi_dung) REFERENCES nguoi_dung(id)
);

CREATE TABLE loai_phieu_giam_gia (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten_loai_phieu NVARCHAR(255),
    trang_thai NVARCHAR(50),
	ngay_tao DATETIME,
	ngay_sua DATETIME
);

CREATE TABLE phuong_thuc_thanh_toan (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten_phuong_thuc NVARCHAR(255),
    trang_thai NVARCHAR(50),
	ngay_tao DATETIME,
	ngay_sua DATETIME
);

CREATE TABLE phieu_giam_gia (
    id INT IDENTITY(1,1) PRIMARY KEY,
	id_loai_phieu_giam_gia INT,
	id_phuong_thuc_thanh_toan INT,
	id_nguoi_dung INT,
    ten_loai_phieu NVARCHAR(255),
    muc_giam_gia INT,
    muc_gia_toi_thieu INT,
	so_luong INT,
    ngay_bat_dau DATETIME,
    ngay_ket_thuc DATETIME,
	ngay_tao DATETIME,
    trang_thai NVARCHAR(50),
	FOREIGN KEY (id_loai_phieu_giam_gia) REFERENCES loai_phieu_giam_gia(id),
	FOREIGN KEY (id_nguoi_dung) REFERENCES nguoi_dung(id),
	FOREIGN KEY (id_phuong_thuc_thanh_toan) REFERENCES phuong_thuc_thanh_toan(id)
);

CREATE TABLE gio_hang (
  id INT IDENTITY(1,1) PRIMARY KEY,
  id_nguoi_dung INT,
  trang_thai NVARCHAR(255),
  ngay_tao DATETIME,
  ngay_sua DATETIME,
	FOREIGN KEY (id_nguoi_dung) REFERENCES nguoi_dung(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE hoa_don (
  id INT IDENTITY(1,1) PRIMARY KEY,
  id_khach_hang INT,
  id_phieu_giam_gia INT,
  id_phuong_thuc_thanh_toan INT,
  ten_khach_hang NVARCHAR(100),
  sdt_khach_hang NVARCHAR(10),
  dia_chi NVARCHAR(255),
  tong_tien INT,
  tien_giam_gia INT,
  total INT,
  trang_thai NVARCHAR(255),
  ngay_tao DATETIME,
  ngay_sua DATETIME,
   FOREIGN KEY (id_khach_hang) REFERENCES nguoi_dung(id),
  FOREIGN KEY (id_phieu_giam_gia) REFERENCES phieu_giam_gia(id),
  FOREIGN KEY (id_phuong_thuc_thanh_toan) REFERENCES phuong_thuc_thanh_toan(id)
);

CREATE TABLE gio_hang_chi_tiet (
	id INT IDENTITY(1,1) PRIMARY KEY,
	id_giay_chi_tiet INT,
	id_gio_hang INT,
	id_hoa_don INT,
	gia_ban INT,
	so_luong_mua INT,
	tong_tien INT,
	trang_thai NVARCHAR(255),
	FOREIGN KEY (id_giay_chi_tiet) REFERENCES chi_tiet_giay(id),
	FOREIGN KEY (id_gio_hang) REFERENCES gio_hang(id),
	FOREIGN KEY (id_hoa_don) REFERENCES hoa_don(id)
);


CREATE TABLE hoa_don_chi_tiet (
  id INT IDENTITY(1,1) PRIMARY KEY,
  id_hoa_don INT,
  id_giay_chi_tiet INT,
  gia_ban INT,
  so_luong_mua INT,
  tong_tien INT,
  trang_thai NVARCHAR(255),
  ngay_tao DATETIME,
  ngay_sua DATETIME,
  FOREIGN KEY (id_giay_chi_tiet) REFERENCES chi_tiet_giay(id),
  FOREIGN KEY (id_hoa_don) REFERENCES hoa_don(id)
);






INSERT INTO danh_muc (ten, mo_ta) VALUES
  (N'Giày thể thao', N'Giày dành cho các hoạt động thể thao'),
  (N'Giày da', N'Giày làm bằng da'),
  (N'Giày cao gót', N'Giày với gót cao dành cho nữ'),
  (N'Giày da nam', N'Giày da dành cho nam'),
  (N'Giày thể thao nữ', N'Giày thể thao dành cho nữ'),
  (N'Giày cao gót nữ', N'Giày cao gót dành cho nữ');

  INSERT INTO de_giay (ten, do_day, ngay_tao, ngay_sua, trang_thai) VALUES
  (N'Đế cao su 1', 2.5, '2023-04-15', NULL, N'Hoạt động'),
  (N'Đế da 1', 3.0, '2023-06-01', '2023-07-01', N'Hoạt động'),
  (N'Đế gỗ 1', 1.8, '2022-11-20', NULL, N'Ngừng sản xuất'),
  (N'Đế cao su 2', 2.2, '2023-05-10', NULL, N'Hoạt động'),
  (N'Đế da 2', 2.8, '2023-08-01', NULL, N'Hoạt động'),
  (N'Đế gỗ 2', 2.0, '2022-09-15', '2023-01-01', N'Ngừng sản xuất'),
  (N'Đế cao su 3', 2.7, '2023-07-01', NULL, N'Hoạt động'),
  (N'Đế da 3', 3.2, '2023-10-01', NULL, N'Hoạt động'),
  (N'Đế gỗ 3', 1.9, '2022-12-01', NULL, N'Ngừng sản xuất'),
  (N'Đế cao su 4', 2.4, '2023-09-01', '2023-10-15', N'Hoạt động');

  INSERT INTO hang_giay (ten_hang, trang_thai, ngay_tao, ngay_sua) VALUES
  (N'Adidas', N'Hoạt động', '2023-01-01', NULL),
  (N'Nike', N'Hoạt động', '2022-06-15', '2023-03-20'),
  (N'Converse', N'Ngừng kinh doanh', '2021-11-10', NULL),
  (N'Puma', N'Đang mở rộng', '2020-08-01', '2022-12-31');

  INSERT INTO size_giay (size_so, trang_thai, ngay_tao, ngay_sua) VALUES
  (35, N'Còn hàng', '2023-01-01', NULL),
  (36, N'Còn hàng', '2023-01-01', NULL),
  (37, N'Còn hàng', '2023-01-01', NULL),
  (38, N'Còn hàng', '2023-01-01', NULL),
  (39, N'Còn hàng', '2022-06-15', '2023-03-20'),
  (40, N'Còn hàng', '2023-01-01', NULL),
  (41, N'Còn hàng', '2023-01-01', NULL),
  (42, N'Hết hàng', '2021-11-10', NULL),
  (44, N'Ngừng sản xuất', '2020-08-01', '2022-12-31');

  INSERT INTO giay (id_danh_muc,ma_giay, ten_giay, trang_thai, ngay_tao, ngay_sua) VALUES
  (5,N'G005', N'Giày lười OKK', N'Ngừng kinh doanh', '2021-08-01', '2022-12-31'),
  (1,N'G001', N'Giày thể thao ABC', N'Còn hàng', '2023-05-01', NULL),
  (2,N'G002', N'Giày cao gót XYZ', N'Còn hàng', '2023-03-15', '2023-05-20'),
  (3,N'G003', N'Giày sandan QRS', N'Hết hàng', '2022-11-01', NULL),
  (4,N'G004', N'Giày lười PQR', N'Ngừng kinh doanh', '2021-08-01', '2022-12-31');
INSERT INTO dot_giam_gia (ten_dot_giam_gia, thoi_gian_bat_dau, thoi_gian_ket_thuc, trang_thai, giam_gia)
VALUES
    (N'Giảm giá mùa hè', '2023-06-01 00:00:00', '2023-06-30 23:59:59', N'Đang diễn ra', 20000),
    (N'Giảm giá cuối năm', '2023-07-15 00:00:00', '2023-07-31 23:59:59', N'Sắp diễn ra', 15000),
    (N'Giảm giá tháng 8', '2023-08-01 00:00:00', '2023-08-31 23:59:59', N'Đang diễn ra', 25000),
    (N'Giảm giá mùa thu', '2023-09-01 00:00:00', '2023-09-30 23:59:59', N'Kết thúc', 10000),
    (N'Giảm giá tháng 10', '2023-10-01 00:00:00', '2023-10-31 23:59:59', N'Đang diễn ra', 18000);

INSERT INTO mau_sac_giay (ten_mau, trang_thai, ngay_tao, ngay_sua)
VALUES
  (N'Trắng', N'Đang bán', '2023-06-01', '2023-06-01'),
  (N'Đen', N'Ngừng bán', '2023-06-05', '2023-06-05'),
  (N'Xanh', N'Đang bán', '2023-06-10', '2023-06-10'),
  (N'Đỏ', N'Đang bán', '2023-06-15', '2023-06-15'),
  (N'Vàng', N'Ngừng bán', '2023-06-20', '2023-06-20');

  INSERT INTO chi_tiet_giay (
  id_giay, id_size_giay, id_de_giay, id_mau_sac_giay, id_hang_giay, 
  ten_giay, gia_ban, so_luong_ton, trang_thai, ngay_tao, ngay_sua
) VALUES
  (1, 1, 1, 1, 1, N'Giày Thể Thao Adidas Ultraboost', 2499, 20, N'Còn hàng', '2023-05-01', '2023-05-15'),
  (1, 2, 1, 1, 1, N'Giày Thể Thao Adidas Ultraboost', 2499, 15, N'Còn hàng', '2023-05-01', '2023-05-15'),
  (1, 3, 1, 1, 1, N'Giày Thể Thao Adidas Ultraboost', 2499, 10, N'Còn hàng', '2023-05-01', '2023-05-15'),
  (2, 1, 2, 2, 2, N'Giày Cao Gót Nữ Skechers', 999, 8, N'Còn hàng', '2023-06-01', '2023-06-10'),
  (2, 2, 2, 2, 2, N'Giày Cao Gót Nữ Skechers', 999, 6, N'Còn hàng', '2023-06-01', '2023-06-10'),
  (2, 3, 2, 2, 2, N'Giày Cao Gót Nữ Skechers', 999, 4, N'Còn hàng', '2023-06-01', '2023-06-10'),
  (3, 1, 3, 3, 3, N'Giày Lười Nam Crocs', 599, 20, N'Còn hàng', '2023-04-15', '2023-04-20'),
  (3, 2, 3, 3, 3, N'Giày Lười Nam Crocs', 599, 15, N'Còn hàng', '2023-04-15', '2023-04-20'),
  (3, 3, 3, 3, 3, N'Giày Lười Nam Crocs', 599, 10, N'Còn hàng', '2023-04-15', '2023-04-20'),
  (4, 1, 1, 4, 4, N'Giày Thể Thao Nữ Nike Air Force 1', 1799, 12, N'Còn hàng', '2023-07-01', '2023-07-10'),
  (4, 2, 1, 4, 4, N'Giày Thể Thao Nữ Nike Air Force 1', 1799, 8, N'Còn hàng', '2023-07-01', '2023-07-10'),
  (4, 3, 1, 4, 4, N'Giày Thể Thao Nữ Nike Air Force 1', 1799, 5, N'Còn hàng', '2023-07-01', '2023-07-10'),
  (5, 1, 2, 5, 4, N'Giày Cao Gót Nữ Steve Madden', 1499, 6, N'Còn hàng', '2023-08-01', '2023-08-15'),
  (5, 2, 2, 5, 4, N'Giày Cao Gót Nữ Steve Madden', 1499, 4, N'Còn hàng', '2023-08-01', '2023-08-15'),
  (5, 3, 2, 5, 4, N'Giày Cao Gót Nữ Steve Madden', 1499, 2, N'Còn hàng', '2023-08-01', '2023-08-15'),
  (5, 1, 3, 5, 4, N'Giày Lười Nam Ecco', 899, 18, N'Còn hàng', '2023-06-15', '2023-06-20'),
  (5, 2, 3, 5, 4, N'Giày Lười Nam Ecco', 899, 13, N'Còn hàng', '2023-06-15', '2023-06-20'),
  (5, 3, 3, 5, 4, N'Giày Lười Nam Ecco', 899, 8, N'Còn hàng', '2023-06-15', '2023-06-20'),
  (5, 1, 1, 5, 4, N'Giày Thể Thao Nam Puma', 1399, 14, N'Còn hàng', '2023-09-01', '2023-09-10'),
  (5, 2, 1, 5, 4, N'Giày Thể Thao Nam Puma', 1399, 10, N'Còn hàng', '2023-09-01', '2023-09-10'),
  (5, 3, 1, 5, 4, N'Giày Thể Thao Nam Puma', 1399, 77, N'Còn hàng', '2023-09-01', '2023-09-10');

  INSERT INTO chi_tiet_dot_giam_gia (id_dot_giam_gia, id_chi_tiet_giay, ten_giay, gia_ban, giam_gia, gia_moi, trang_thai)
VALUES
  (1, 1, N'Giày Thể Thao', 2499, 500, 1, N'Đang áp dụng'),
  (1, 2, N'Giày Da', 999, 100, 700000, N'Đang áp dụng'),
  (1, 3, N'Giày Cao Gót', 599, 30, 1, N'Đang áp dụng'),
  (1, 4, N'Giày Sneaker', 1799, 40, 1, N'Đang áp dụng'),
  (1, 5, N'Giày Lười', 1499, 250, 1, N'Đang áp dụng');

INSERT INTO chuc_vu (chuc_vu, trang_thai, ngay_tao, ngay_sua)
VALUES
    ('Admin', 0, '2023-06-01 10:00:00', '2023-06-01 10:00:00'),
    ('User', 1, '2023-06-01 10:00:00', '2023-06-01 10:00:00');

INSERT INTO nguoi_dung (ho_ten, email, mat_khau, so_dien_thoai, anh, id_chuc_vu, trang_thai, ngay_tao)
VALUES 
    (N'Nguyen Van A', 'a@example.com', '$2a$10$JAiltWdbWeEMWK/mrxhWW.mFU5j1R6LVT6YV8WiQNYcHfHmOX2DHi', '0123456789', 'avatar_a.jpg', 1, 1, '2023-06-01 09:00:00'),
    (N'Tran Thi B', 'b@example.com', '$2a$10$JAiltWdbWeEMWK/mrxhWW.mFU5j1R6LVT6YV8WiQNYcHfHmOX2DHi', '0987654321', 'avatar_b.jpg', 2, 1, '2023-06-02 10:30:00'),
    (N'Le Van C', 'c@example.com', '$2a$10$JAiltWdbWeEMWK/mrxhWW.mFU5j1R6LVT6YV8WiQNYcHfHmOX2DHi', '0456789012', 'avatar_c.jpg', 2, 1, '2023-06-03 14:15:00'),
    (N'Pham Thi D', 'd@example.com', '$2a$10$JAiltWdbWeEMWK/mrxhWW.mFU5j1R6LVT6YV8WiQNYcHfHmOX2DHi', '0789012345', 'avatar_d.jpg', 1, 0, '2023-06-04 16:45:00'),
    (N'Hoang Van E', 'e@example.com', '$2a$10$JAiltWdbWeEMWK/mrxhWW.mFU5j1R6LVT6YV8WiQNYcHfHmOX2DHi', '0159753951', 'avatar_e.jpg', 2, 0, '2023-06-05 11:20:00');

INSERT INTO dia_chi VALUES 
	(2,N'Lê đức thọ, Hà nội'),
	(2,N'Cà mau, Sài gòn')


INSERT INTO loai_phieu_giam_gia VALUES 
(N'Giảm giá theo tiền','Hoạt động','2023-06-30',NULL),
(N'Giảm giá theo phần trăm','Hoạt động','2023-06-30',NULL);

INSERT INTO phuong_thuc_thanh_toan (ten_phuong_thuc, trang_thai, ngay_tao)
VALUES
	(N'Mọi hình thức', N'Hoạt động', '2023-06-01 09:00:00'),
    (N'Tài khoản ngân hàng', N'Hoạt động', '2023-06-01 09:00:00'),
    (N'Thanh toán tiền mặt', N'Hoạt động', '2023-06-02 10:30:00'),
	(N'Chuyển khoản và tiền mặt', N'Hoạt động', '2023-06-02 10:30:00');

INSERT INTO phieu_giam_gia (id_loai_phieu_giam_gia,id_nguoi_dung,id_phuong_thuc_thanh_toan, ten_loai_phieu, muc_giam_gia, muc_gia_toi_thieu, so_luong, ngay_bat_dau, ngay_ket_thuc, trang_thai)
VALUES
    (1,1,1, N'Phiếu giảm 100.000 đ', 1000, 5000, 50, '2023-06-15 00:00:00', '2023-06-30 23:59:59', N'Đang áp dụng'),
    (1,1,1, N'Phiếu giảm 100.000 đ', 1000, 5000, 30, '2023-07-01 00:00:00', '2023-07-15 23:59:59', N'Đang áp dụng'),
    (2,1,1, N'Phiếu giảm 15%', 15, 3000, 20, '2023-08-01 00:00:00', '2023-08-31 23:59:59', N'Đang áp dụng'),
    (2,1,1, N'Phiếu giảm 15%', 15, 3000, 25, '2023-09-01 00:00:00', '2023-09-30 23:59:59', N'Đang áp dụng'),
    (2,1,1, N'Phiếu giảm 15%', 15, 3000, 40, '2023-10-01 00:00:00', '2023-10-31 23:59:59', N'Đang áp dụng');
INSERT INTO gio_hang VALUES
	(1,N'true','1-1-2004','1-1-2004'),
	(2,N'true','1-1-2004','1-1-2004');

	delete from hoa_don
	select * from hoa_don
	select * from gio_hang_chi_tiet
	select * from chi_tiet_giay
	select * from gio_hang_chi_tiet where id_gio_hang = 2 

/*	select * from gio_hang
	select ctg.id_giay, ctg.id_size_giay, ctg.id_de_giay, ctg.id_mau_sac_giay, ctg.id_hang_giay, 
	  ctg.ten_giay, ctg.gia_ban,ctdgg.giam_gia,ctdgg.gia_moi, ctg.so_luong_ton,ctg.anh, ctg.trang_thai, ctg.ngay_tao, ctg.ngay_sua
	  from dot_giam_gia dgg join chi_tiet_dot_giam_gia ctdgg 
	  on dgg.id = ctdgg.id_dot_giam_gia right join chi_tiet_giay ctg 
	  on ctdgg.id_chi_tiet_giay = ctg.id 

	  select * from gio_hang where id_nguoi_dung =2 and id = 1

	  select * from gio_hang where id_nguoi_dung = 2 and id_giay_chi_tiet = 2

	select ho_ten, gio_hang.id as 'ID giỏ hàng', chi_tiet_giay.ten_giay from nguoi_dung join gio_hang on nguoi_dung.id = gio_hang.id_nguoi_dung join chi_tiet_giay on gio_hang.id_giay_chi_tiet = chi_tiet_giay.id where nguoi_dung.id = 2
*/







