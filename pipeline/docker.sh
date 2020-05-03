echo "*** Delete container if exists ***"
docker rm -f timesheet-api

echo "*** Delete image if exists ***"
docker rmi -f timesheet-api

echo "*** Docker create image ***"
docker build -t timesheet-api .

echo "*** Docker create container ***"
docker run -d --name timesheet-api -e APP_OPTIONS='--spring.datasource.url=jdbc:postgresql://localhost:5432/timesheet' -p 8080:8080 --network=host timesheet-api