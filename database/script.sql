CREATE DATABASE ecommerce_db_v1
GO

USE ecommerce_db_v1
GO

CREATE TABLE Categories (
	id INT IDENTITY(1,1) PRIMARY KEY,
	slug NVARCHAR(20),
	name NVARCHAR(50),
)
GO

CREATE TABLE Suppliers (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	name NVARCHAR(50)
)
GO


CREATE TABLE Origins (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	name NVARCHAR(50)
)
GO

CREATE TABLE Products (
	id INT IDENTITY(1,1) PRIMARY KEY,
	name NVARCHAR(255),
	category_id INT,
	origin_id INT,
	supplier_id INT,
	price MONEY,
	discount MONEY,
	quantity INT,
	import_date DATETIME,
	image NVARCHAR(255),
	CONSTRAINT FK_Product_Category FOREIGN KEY (category_id) REFERENCES Categories(id),
	CONSTRAINT FK_Product_Origin FOREIGN KEY (origin_id) REFERENCES Origins(id),
	CONSTRAINT FK_Product_Supplier FOREIGN KEY (supplier_id) REFERENCES Suppliers(id)
)
GO

CREATE TABLE Descriptions (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	product_id INT,
	content NVARCHAR(MAX),
	CONSTRAINT FK_Description_Product FOREIGN KEY (product_id) REFERENCES Products(id),
)
GO


CREATE TABLE Users (
	id INT IDENTITY(1,1) PRIMARY KEY,
	username NVARCHAR(50),
	password NVARCHAR(100),
	first_name NVARCHAR(100),
	last_name NVARCHAR(100),
	gender bit,
	email NVARCHAR(100),
	phone_number NVARCHAR(20),
	image NVARCHAR(50),
	CONSTRAINT AK_username UNIQUE(username)
)
GO

CREATE TABLE Roles (
	id INT IDENTITY(1,1) PRIMARY KEY,
	name NVARCHAR(50)
)
GO

CREATE TABLE Users_Roles (
	user_id INT,
	role_id INT,
	CONSTRAINT PK_Users_Roles PRIMARY KEY (user_id, role_id),
	CONSTRAINT FK_ofUsers FOREIGN KEY (user_id) REFERENCES Users(id),
	CONSTRAINT FK_ofRoles FOREIGN KEY (role_id) REFERENCES Roles(id)
)
GO

CREATE TABLE DeliveryInfo (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	recipient_name NVARCHAR(20) NOT NULL,
	phone_number VARCHAR(15) NOT NULL,
	email NVARCHAR(50),
	address NVARCHAR(MAX) NOT NULL
)
GO

CREATE TABLE Carts (
	id INT IDENTITY(1,1) PRIMARY KEY,
	customer_id INT,
	delivery_info_id INT,
	note NVARCHAR(255),
	purchase_date DATETIME,
	CONSTRAINT FK_Cart_DeliveryInfo FOREIGN KEY (delivery_info_id) REFERENCES DeliveryInfo(id),
	CONSTRAINT FK_Cart_ofUser FOREIGN KEY (customer_id) REFERENCES Users(id),
)
GO

CREATE TABLE CartDetails(
	id INT IDENTITY(1,1) PRIMARY KEY,
	cart_id INT,
	product_id INT,
	quantity INT,
	CONSTRAINT FK_CartDetail_Cart FOREIGN KEY (cart_id) REFERENCES Carts(id),
	CONSTRAINT FK_CartDetail_Product FOREIGN KEY (product_id) REFERENCES Products(id),
)
GO
INSERT INTO Categories (slug, name)
VALUES ('lt','Laptop'),
		('dtdd', N'Điện thoại di động'),
		('pk', N'Phụ kiện'),
		('dhtm', N'Đồng hồ thông minh'),
		('pc', 'PC'),
		('loa', 'Loa'),
		('ntm', N'Nhà thông minh'),
		('tn', 'Tai nghe'),
		('mh', N'Màn hình')
GO
INSERT INTO Suppliers (name) --Nhà cung cấp
VALUES ('Apple'),
		('Xiaomi'),
		('Huawei'),
		('Samsung'),
		('Nokia'),
		('Realme'),
		('Lenovo'),
		('ASUS'),
		('ACER'),
		('Dell'),
		('Anker'),
		('JBL')
GO
INSERT INTO Origins (name) --Xuất xứ
VALUES (N'Mỹ'),
		(N'Pháp'),
		(N'Hàn Quốc'),
		(N'Nhật Bản'),
		(N'Đức'),
		(N'Việt Nam'),
		(N'Trung Quốc'),
		(N'Nga'),
		(N'Đài Loan'),
		(N'Phần Lan')
GO
INSERT INTO Users (username, password, first_name, last_name, gender, email, phone_number, image)
VALUES ('takimtai123', '$2a$10$FBXl.3orPpnv8grWSz.lbe3rnlr.MEHwmHHXaaM.H3hUqscJ5Okx2', N'Tạ', N'Kim Tài', 1, 'takimtai123@gmai.com', '0909285266', 'kimtai.jpg'),
		('tranthanhhai', '$2a$10$FBXl.3orPpnv8grWSz.lbe3rnlr.MEHwmHHXaaM.H3hUqscJ5Okx2', N'Trần', N'Thanh Hải', 1, 'tranthanhhai@gmai.com', '0909285277', 'thanhhai.jpg'),
		('kieutanchien', '$2a$10$FBXl.3orPpnv8grWSz.lbe3rnlr.MEHwmHHXaaM.H3hUqscJ5Okx2', N'Kiều', N'Tân Chiến', 1, 'kieutanchien@gmai.com', '0909285288', 'tanchien.jpg'),
		('voduchuy', '$2a$10$FBXl.3orPpnv8grWSz.lbe3rnlr.MEHwmHHXaaM.H3hUqscJ5Okx2', N'Võ', N'Đức Huy', 0, 'voduchuy@gmai.com', '0909245299', 'duchuy.jpg'),
		('buinhathoang', '$2a$10$FBXl.3orPpnv8grWSz.lbe3rnlr.MEHwmHHXaaM.H3hUqscJ5Okx2', N'Bùi', N'Nhật Hoàng', 0, 'buinhathoang@gmai.com', '0909255299', 'nhathoang.jpg'),
		('tranthanhtienhai', '$2a$10$FBXl.3orPpnv8grWSz.lbe3rnlr.MEHwmHHXaaM.H3hUqscJ5Okx2', N'Trần', N'Thanh Tiến Hải', 0, 'tranthanhtienhai@gmai.com', '0909265299', 'tienhai.jpg')
GO
INSERT INTO Roles (name)
VALUES ('ROLE_CUSTOMER'),
		('ROLE_ADMIN'),
		('ROLE_MANAGER')
		
GO
INSERT INTO Users_Roles (user_id, role_id)
VALUES (1,1),
		(2,1),
		(3,2),
		(3,3)
		(4,2),
		(5,2),
		(6,2)
GO
INSERT INTO DeliveryInfo (recipient_name, phone_number, email, address)
VALUES (N'Tạ Kim Tài','0909285266',N'takimtai123@gmail.com',N'6/5 Võ Trường Toản, Phường 12, Quận 5'),
		(N'Bùi Nhật Hoàng','0909255266',N'buinhathoang@gmail.com',N'215 Hậu Giang, Phường 11, Quận 6'),
		(N'Trần Thanh Hải','0909235266',N'tranthanhhai@gmail.com',N'439 Tung Sơn, Phường 10, Quận 7'),
		(N'Võ Đức Huy','0909215266',N'voduchuy@gmail.com',N'319 Phạm Thế Hiển, Phường 9, Quận 8'),
		(N'Kiều Tân Chiến','0909205266',N'kieutanchien@gmail.com',N'765 Minh Phụng, Phường 8, Quận 9'),
		(N'Trần Thanh Tiến Hải','0909205266',N'tranthanhtienhai@gmail.com',N'765 Lý Thường Kiệt, Phường 7, Quận 10')
GO
INSERT INTO Carts (customer_id, note, delivery_info_id)
VALUES (1, 'ABCD', 1),
		(2, 'ABCH', 2),
		(3, 'ABCE', 3),
		(4, 'ABCF', 4),
		(5, 'ABCG', 5),
		(6, 'ABCT', 6)
GO
INSERT INTO Products (name, category_id, origin_id, supplier_id, price, discount, quantity, image) --Xuất xứ
VALUES ('Samsung Galaxy Tab S7 FE', 2, 3, 4, 13990000,2390000, 100, 'samsung1.jpg'),
		('Samsung Galaxy Tab A7 Lite', 2, 3, 4, 4490000,450000, 100, 'samsung2.jpg'),
		('Samsung Galaxy A22', 2, 3, 4, 5290000,240000, 100, 'samsung3.jpg'),
		('Samsung Galaxy Z Fold2 5G', 2, 3, 4, 50000000,0, 100, 'samsung4.jpg'),
		('Samsung Galaxy Z Fold3 5G 512GB', 2, 3, 4, 44990000,290000, 100, 'samsung5.jpg'),

		(N'Huawei MatePad T10s (Nền tảng Huawei Mobile Service)', 2, 7, 3, 5140000,400000, 100, 'huawei1.jpg'),
		(N'Huawei MatePad 64GB (Nền tảng Huawei Mobile Service)', 2, 7, 3, 6590000,259000, 100, 'huawei2.jpg'),
		(N'Huawei MatePad 11', 2, 7, 3, 13990000,230000, 100, 'huawei3.jpg'),
		(N'Huawei MatePad 128GB (Nền tảng Huawei Mobile Service)', 2, 7, 3, 7490000,200000, 100, 'huawei4.jpg'),
		(N'Huawei MatePad T8 (Nền tảng Huawei Mobile Service)', 2, 7, 3, 3090000,200000, 100, 'huawei5.jpg'),

		('Lenovo Tab M10 - FHD Plus', 2, 7, 7, 5890000,300000, 100, 'lenovo1.jpg'),
		('Lenovo Tab M10 - Gen 2', 2, 7, 7, 4690000,230000, 100, 'lenovo2.jpg'),
		('Lenovo Tab M8 (TB-8505X)', 2, 7, 7, 3690000,150000, 100, 'lenovo3.jpg'),
		('Lenovo Tab3 8 Plus', 2, 7, 7, 4690000,230000, 100, 'lenovo4.jpg'),
		('Lenovo Tab 2 A7-10', 2, 7, 7, 5890000,300000, 100, 'lenovo5.jpg'),

		('iPad Pro M1 12.9 inch WiFi Cellular 256GB (2021)', 2, 1, 1, 38490000,2865000, 100, 'apple1.jpg'),
		('iPad Pro M1 12.9 inch WiFi Cellular 128GB (2021)', 2, 1, 1, 35290000,1425000, 100, 'apple2.jpg'),
		('iPad Pro M1 12.9 inch WiFi 256GB (2021)', 2, 1, 1, 33290000,850000, 100, 'apple3.jpg'),
		('iPad Pro M1 12.9 inch WiFi 128GB (2021)', 2, 1, 1, 30790000,350000, 100, 'apple4.jpg'),
		('iPhone 12', 2, 1, 1, 20500000,350000, 100, 'apple5.jpg'),
		
		('Xiaomi Redmi 10 (6GB/128GB)', 2, 7, 2, 4560000,300000, 100, 'xiaomi1.jpg'),
		('Xiaomi Redmi Note 10 5G 8GB', 2, 7, 2, 5490000,150000, 100, 'xiaomi2.jpg'),
		('Xiaomi Mi 11 5G', 2, 7, 2, 16520000,50000, 100, 'xiaomi3.jpg'),
		('Xiaomi Mi 10T Pro 5G', 2, 7, 2, 12560000,670000, 100, 'xiaomi4.jpg'),
		('Xiaomi Mi 11 Lite', 2, 7, 2, 7560000,120000, 100, 'xiaomi5.jpg'),

		('Nokia 105 4G', 2, 10, 5, 710000,0, 100, 'nokia1.jpg'),
		('Nokia 6300 4G', 2, 10, 5, 1290000,200000, 100, 'nokia2.jpg'),
		('Nokia C20', 2, 10, 5, 1990000,0, 100, 'nokia3.jpg'),
		('Nokia C30', 2, 10, 5, 2790000,279000, 100, 'nokia4.jpg'),
		('Nokia 105 Single SIM', 2, 10, 5, 390000,0, 100, 'nokia5.jpg'),

		(N'Realme Watch 2 pro dây silicone', 4, 7, 6, 2690000,0, 100, 'realme1.jpg'),
		(N'Realme Watch 2 dây silicone', 4, 7, 6, 1890000,200000, 100, 'realme2.jpg'),

		('Asus ExpertBook P2451FA i5 10210U (EK2772T)', 1, 9, 8, 17590000,1000000, 100, 'asus1.jpg'),
		('Asus VivoBook A415EA i3 1115G4 (EB568T)', 1, 9, 8, 15900000,450000, 100, 'asus2.jpg'),
		('Asus VivoBook A415EA i3 1115G4 (EB559T)', 1, 9, 8, 15690000,0, 100, 'asus3.jpg'),
		('Asus VivoBook A415EA i5 1135G7 (AM889T)', 1, 9, 8, 17690000,279000, 100, 'asus4.jpg'),
		('Asus VivoBook X415EA i5 1135G7 (EB637T)', 1, 9, 8, 19690000,0, 100, 'asus5.jpg'),

		('Dell Gaming G3 15 i7 10750H (P89F002BWH)', 1, 1, 10, 17590000,1000000, 100, 'dell1.jpg'),
		('Dell Inspiron 7400 i5 1135G7 (N4I5134W)', 1, 1, 10, 15900000,450000, 100, 'dell2.jpg'),
		('Dell XPS 13 9310 i7 1165G7 (JGNH62)', 1, 1, 10, 15690000,0, 100, 'dell3.jpg'),
		('Dell XPS 13 9310 i5 1135G7 (70231343)', 1, 1, 10, 17690000,279000, 100, 'dell4.jpg'),
		('Dell Gaming G15 5515 R7 5800H (70258051)', 1, 1, 10, 19690000,0, 100, 'dell5.jpg'),

		('Acer Nitro 5 Gaming A515 55 72R2 i7 10870H', 1, 9, 9, 26590000,1000000, 100, 'acer1.jpg'),
		('Acer Nitro 5 Gaming AN515 57 74NU i7 11800H', 1, 9, 9, 28900000,450000, 100, 'acer2.jpg'),
		('Acer Nitro 5 Gaming AN515 57 5831 i5 11400H', 1, 9, 9, 31690000,0, 100, 'acer3.jpg'),
		('Acer Nitro 5 Gaming AN515 57 50FT i5 11400H', 1, 9, 9, 26690000,279000, 100, 'acer4.jpg'),
		('Acer Nitro 5 AN515 57 51G6 i5 11400H (NH.QD8SV.002)', 1, 9, 9, 24690000,0, 100, 'acer5.jpg'),

		(N'Sạc nhanh Anker Powerport III Nano 20W A2633', 3, 7, 11, 360000,0, 100, 'phukien1.jpg'),
		
		('iMac 24 inch 2021 4.5K M1 8GPU (MGPL3SA/A) Blue', 5, 1, 1, 44000000,1500000, 100, 'pc1.jpg'),

		(N'Robot hút bụi Xiaomi Vacuum Mop Pro', 7, 7, 2, 9900000,200000, 100, 'nhathongminh1.jpg'),

		(N'Tai nghe Bluetooth Apple AirPods 2 VN/A', 8, 1, 1, 3300000,200000, 100, 'tainghe1.jpg'),

		(N'Asus LCD ProArt PA247CV 23.8 inch Full HD', 9, 9, 8, 6900000,200000, 100, 'manhinh1.jpg')
GO
INSERT INTO Descriptions (product_id, content) --Mô tả sản phẩm
VALUES (1, N'Màn hình'),
		(1,N'Công nghệ màn hình: TFT LCD'),
		(1,N'Độ phân giải: 1600 x 2560 Pixels'),
		(1,N'Màn hình rộng: 12.4"'),
		(1,N'Hệ điều hành & CPU'),
		(1,N'Hệ điều hành: Android 11'),
		(1,N'Chip xử lý (CPU): Snapdragon 750G'),
		(1,N'Tốc độ CPU: 2 nhân 2.2 GHz & 6 nhân 1.8 GHz'),
		(1,N'Chip đồ hoạ (GPU): Adreno 619')
GO
INSERT INTO CartDetails (cart_id, product_id, quantity)
VALUES (1, 1, 10),
		(2, 2, 8),
		(3, 3, 12),
		(4, 4, 5),
		(5, 5, 7),
		(6, 6, 9)
GO