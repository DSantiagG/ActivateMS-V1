@echo off

:: ---------- RabbitMQ ------------------
docker-compose down
docker rmi rabbitmq:latest
docker-compose up -d
echo "RabbitMQ is running"
pause

:: ---------- User Service ------------------
cd user_management_ms

docker-compose down
docker rmi user_management_ms-user-server:latest

:: Crear .jar en un subproceso
start /wait cmd /c "mvn clean package -DskipTests"

:: Levantar contenedor
docker-compose up -d
cd ..
echo "User Service is running"
pause

:: ---------- Notification Service ------------------
call cd notification_ms
docker-compose down
docker rmi notification_ms-notification-server:latest

:: Crear .jar
start /wait cmd /c "mvn clean package -DskipTests"
call pause

:: Levantar contenedor
docker-compose up -d
echo "Notification Service is running"
call pause
call cd ..

:: ---------- Recommendation Service ------------------
call cd recommendation_ms
docker-compose down
docker rmi recommendation_ms-recommendation-server:latest

:: Crear .jar
start /wait cmd /c "mvn clean package -DskipTests"
call pause

:: Levantar contenedor
docker-compose up -d
echo "Recommendation Service is running"
call pause
call cd ..

:: ---------- Event Service ------------------
call cd gestion_evento_microservicio
docker-compose down
docker rmi gestion_evento_microservicio-event-server:latest

:: Crear .jar
start /wait cmd /c "mvn clean package -DskipTests"
call pause

:: Levantar contenedor
docker-compose up -d
echo "Event Service is running"
call pause
call cd ..

:: Mantener la consola abierta
cmd /k