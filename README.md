# CRS - Course Registration System

Project này là một hệ thống đăng ký khóa học theo kiến trúc microservices, gồm backend, API gateway và frontend web. Hệ thống cho phép người dùng đăng nhập, quản lý khóa học và thực hiện đăng ký khóa học qua giao diện web.

## Giới thiệu về web app

Web app của dự án được xây dựng bằng React + Vite và giao tiếp với các service phía sau thông qua API Gateway. Người dùng có thể truy cập giao diện tại địa chỉ:

- Frontend: http://localhost:5173
- Gateway/API: http://localhost:8080

Giao diện web bao gồm các chức năng chính như:
- Đăng nhập / đăng ký tài khoản
- Xem danh sách khóa học
- Tìm kiếm và lọc khóa học
- Đăng ký / hủy khóa học
- Quản lý dữ liệu theo role người dùng

## Kiến trúc hệ thống

Dự án gồm các module chính:

- `auth-service`: xử lý xác thực, đăng nhập, JWT
- `course-services`: quản lý khóa học
- `registration-service`: quản lý đăng ký khóa học
- `api-gateway`: tổng hợp API và route request tới service tương ứng
- `crs-frontend`: ứng dụng web frontend (React)

Các service backend chạy trên các cổng sau:

- Auth service: http://localhost:8081
- Course service: http://localhost:8082
- Registration service: http://localhost:8083
- API Gateway: http://localhost:8080

## Yêu cầu hệ thống

Trước khi chạy dự án, bạn cần cài đặt:

- JDK 21
- Maven hoặc Maven Wrapper (`mvnw`/`mvnw.cmd`)
- Node.js 18+
- npm
- MySQL 8+

## Cài đặt môi trường

### 1. Cài đặt Java

Tải và cài đặt JDK 21 theo hệ điều hành của bạn.

Kiểm tra:

```bash
java -version
```

### 2. Cài đặt Node.js và npm

Tải và cài đặt Node.js 18+.

Kiểm tra:

```bash
node -v
npm -v
```

### 3. Cài đặt MySQL

Tạo 3 database sau trên MySQL:

```sql
CREATE DATABASE auth_db;
CREATE DATABASE course_db;
CREATE DATABASE registration_db;
```

Nếu bạn dùng tài khoản khác, hãy cập nhật lại thông tin trong từng file `application.properties` tương ứng.

## Cấu hình database

Các file cấu hình backend hiện đang dùng:

- `auth-service/src/main/resources/application.properties`
- `course-services/src/main/resources/application.properties`
- `registration-service/src/main/resources/application.properties`

Mặc định, các service đang kết nối tới MySQL localhost với tài khoản:

- username: `root`
- password: rỗng

Nếu máy bạn có mật khẩu MySQL khác, cập nhật các dòng:

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Chạy ứng dụng

### Bước 1: Chạy backend services

Mở terminal ở thư mục gốc của project và chạy từng service theo thứ tự:

#### Windows PowerShell

```powershell
./mvnw.cmd -f .\auth-service\pom.xml spring-boot:run
```

```powershell
./mvnw.cmd -f .\course-services\pom.xml spring-boot:run
```

```powershell
./mvnw.cmd -f .\registration-service\pom.xml spring-boot:run
```

```powershell
./mvnw.cmd -f .\api-gateway\pom.xml spring-boot:run
```

#### macOS / Linux

```bash
./mvnw -f ./auth-service/pom.xml spring-boot:run
```

```bash
./mvnw -f ./course-services/pom.xml spring-boot:run
```

```bash
./mvnw -f ./registration-service/pom.xml spring-boot:run
```

```bash
./mvnw -f ./api-gateway/pom.xml spring-boot:run
```

### Bước 2: Chạy frontend web

```bash
cd crs-frontend
npm install
npm run dev
```

Sau đó mở trình duyệt và truy cập:

```text
http://localhost:5173
```

## Cấu hình frontend

File `.env` trong thư mục `crs-frontend` đang chứa:

```env
VITE_API_BASE_URL=http://localhost:8080
```

Đây là địa chỉ API Gateway mà frontend sẽ gọi để truy cập đến các service backend.

## API Gateway

Tất cả request từ frontend được route qua gateway ở port `8080`:

- `/api/auth/**` -> `auth-service`
- `/api/courses/**` -> `course-service`
- `/api/registrations/**` -> `registration-service`

## Một số lưu ý

- Đảm bảo MySQL đang chạy trước khi start các service backend.
- Nếu bạn thay đổi port hoặc cấu hình database, cập nhật cả file `application.properties` và `crs-frontend/.env` tương ứng.
- Dự án sử dụng JWT và khóa bí mật đã được định nghĩa ở các service backend. Nếu bạn đổi secret thì cần đồng bộ ở tất cả service liên quan.

## Cấu trúc thư mục

```text
PMHDV/
├── api-gateway/
├── auth-service/
├── course-services/
├── crs-frontend/
├── registration-service/
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── .gitignore
```

## Liên hệ / hỗ trợ

Nếu bạn cần hỗ trợ chạy dự án hoặc debug khi khởi động, hãy kiểm tra trước các file cấu hình `application.properties` và đảm bảo tất cả service backend đã khởi động thành công.

---

README này được tạo để giúp bạn cài đặt, chạy và hiểu tổng quan về web app của dự án.
