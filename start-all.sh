#!/usr/bin/env bash

set -u

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
LOG_DIR="$ROOT_DIR/.logs"
mkdir -p "$LOG_DIR"

start_service() {
  local name="$1"
  local dir="$2"
  local log="$LOG_DIR/${name}.log"
  local pid_file="$LOG_DIR/${name}.pid"

  echo "Starting $name..."
  (
    cd "$ROOT_DIR/$dir" || exit 1
    ./mvnw spring-boot:run > "$log" 2>&1
  ) &

  echo $! > "$pid_file"
}

start_frontend() {
  local log="$LOG_DIR/frontend.log"
  local pid_file="$LOG_DIR/frontend.pid"

  echo "Starting frontend..."
  (
    cd "$ROOT_DIR/crs-frontend" || exit 1
    npm install > "$LOG_DIR/frontend-install.log" 2>&1 || true
    npm run dev -- --host 0.0.0.0 > "$log" 2>&1
  ) &

  echo $! > "$pid_file"
}

start_service "auth-service" "auth-service"
sleep 5

start_service "course-services" "course-services"
sleep 5

start_service "registration-service" "registration-service"
sleep 5

start_service "api-gateway" "api-gateway"
sleep 8

start_frontend

echo ""
echo "========================================"
echo "All services are starting in background."
echo "Ports:"
echo "  - Auth service: http://localhost:8081"
echo "  - Course service: http://localhost:8082"
echo "  - Registration service: http://localhost:8083"
echo "  - API gateway: http://localhost:8080"
echo "  - Frontend: http://localhost:5173"
echo "========================================"
echo "Logs are in: $LOG_DIR"
