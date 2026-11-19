# Campus Manager - Hệ thống quản lý sinh viên

## Cấu trúc dự án

- `src/App.java`: điểm khởi chạy chương trình.
- `src/com/qlsv/view/`: giao diện đăng nhập, trang tổng quan và các màn hình quản lý.
- `src/com/qlsv/dao/`: các lớp truy vấn SQL Server.
- `src/com/qlsv/model/`: các lớp dữ liệu.
- `lib/`: JDBC Driver cho SQL Server.
- `bin/`: các file `.class` được biên dịch.

## Chạy bằng Visual Studio Code

1. Cài JDK 21 hoặc phiên bản tương thích.
2. Mở thư mục `do_an_java` bằng VS Code.
3. Đợi Java Extension nhận diện các thư mục `src` và `lib`.
4. Kiểm tra cấu hình SQL Server trong `src/com/qlsv/dao/DatabaseConnection.java` để bảo đảm URL, tên database và tài khoản/mật khẩu đúng với máy đang chạy.
5. Mở `src/App.java` và chọn **Run**.

## Ghi chú nâng cấp giao diện

Giao diện đã được làm mới theo một bộ màu thống nhất: màn hình đăng nhập hai cột, trang tổng quan có thanh điều hướng, cửa sổ quản lý có tiêu đề và các tab, các form CRUD có thẻ nội dung, thanh thao tác và bảng dữ liệu dễ đọc hơn.

Các lớp DAO, model, câu truy vấn và luồng xử lý thêm/sửa/xóa/tìm kiếm được giữ lại. `UITheme.java` chứa màu sắc và tiện ích định dạng giao diện dùng chung.
