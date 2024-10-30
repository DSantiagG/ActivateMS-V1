# ---------- RabbitMQ ------------------

docker compose down
docker rmi rabbitmq:latest

docker compose up -d
read -p "RabbitMQ is running. Press enter to continue..."

# ---------- User Service ------------------
cd user_management_ms
docker compose down
docker rmi user_management_ms-user-server:latest

#Crear .jar
mvn clean package -DskipTests

#Levantar contenedor
docker compose up -d
read -p "User Microservice is running. Press enter to continue..."
cd ..

# ---------- Notification Service ------------------
cd notification_ms
docker compose down
docker rmi notification_ms-notification-server:latest

#Crear .jar
mvn clean package -DskipTests

#Levantar contenedor
docker compose up -d
read -p "Notification Microservice is running. Press enter to continue..."
cd ..

# ---------- Recommendation Service ------------------
cd recommendation_ms
docker compose down
docker rmi recommendation_ms-recommendation-server:latest

#Crear .jar
mvn clean package -DskipTests

#Levantar contenedor
docker compose up -d
read -p "Recommendation Microservice is running. Press enter to continue..."
cd ..

# ---------- Event Service ------------------
cd gestion_evento_microservicio
docker compose down
docker rmi gestion_evento_microservicio-event-server:latest

#Crear .jar
mvn clean package -DskipTests

#Levantar contenedor
docker compose up -d
read -p "Event Microservice is running. Press enter to continue..."
cd ..