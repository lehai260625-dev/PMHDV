@echo off
setlocal

set ROOT=%~dp0
cd /d "%ROOT%"

if not exist "%ROOT%logs" mkdir "%ROOT%logs"

echo [1/5] Starting auth-service...
start "auth-service" /D "%ROOT%auth-service" cmd /c "mvnw.cmd spring-boot:run > %ROOT%logs\auth-service.log 2>&1"

timeout /t 5 /nobreak >nul

echo [2/5] Starting course-services...
start "course-services" /D "%ROOT%course-services" cmd /c "mvnw.cmd spring-boot:run > %ROOT%logs\course-services.log 2>&1"

timeout /t 5 /nobreak >nul

echo [3/5] Starting registration-service...
start "registration-service" /D "%ROOT%registration-service" cmd /c "mvnw.cmd spring-boot:run > %ROOT%logs\registration-service.log 2>&1"

timeout /t 5 /nobreak >nul

echo [4/5] Starting api-gateway...
start "api-gateway" /D "%ROOT%api-gateway" cmd /c "mvnw.cmd spring-boot:run > %ROOT%logs\api-gateway.log 2>&1"

timeout /t 8 /nobreak >nul

echo [5/5] Starting frontend...
cd /d "%ROOT%crs-frontend"
call npm install
start "crs-frontend" cmd /k "cd /d %ROOT%crs-frontend && npm run dev -- --host 0.0.0.0"

echo.
echo ======================================
echo All services are starting...
echo.
echo Auth service:     http://localhost:8081
echo Course service:   http://localhost:8082
echo Registration:     http://localhost:8083
echo API Gateway:      http://localhost:8080
echo Frontend:         http://localhost:5173
echo Logs folder:      %ROOT%logs
echo ======================================
endlocal
