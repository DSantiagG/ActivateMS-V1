docker compose down
docker rmi event-server:latest

#Crear .jar
mvn clean install -f ./gestion_evento_microservicio/pom.xml -Dmaven.test.skip=true
read -p ".jar created. Press enter to continue..."

#Construir imagen
docker build -t event-server:latest -f ./gestion_evento_microservicio/Dockerfile ./gestion_evento_microservicio
read -p "Image created. Press enter to continue..."

#Levantar contenedor
docker compose up -d
echo "Docker compose up."




#echo "Docker compose up. Waiting to run migrations"
#sleep 10

#mvn flyway:migrate -f ./gestion_evento_microservicio/pom.xml

#echo "Migrations completed. Process finished."