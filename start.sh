# ---------- RabbitMQ ------------------

docker compose down
docker rmi rabbitmq:latest

docker compose up -d
read -p "RabbitMQ is running. Press enter to continue..."

# ---------- Event Service ------------------
cd gestion_evento_microservicio
docker compose down
docker rmi event-server:latest

#Crear .jar
mvn clean package -DskipTests

#Levantar contenedor
docker compose up -d
read -p "Event Service is running. Press enter to continue..."
cd ..