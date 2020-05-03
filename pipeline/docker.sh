echo "*** Delete container if exists ***"
docker ps -q -f name=timesheet-api
if [ $? -eq 0 ];
then
     echo "*** Remove container ***"
     docker rm -f timesheet-api
fi

echo "*** Delete image if exists ***"
docker images -q timesheet-api
if [ $? -eq 0 ];
then
     echo "*** Remove image ***"
     docker rmi -f timesheet-api
fi

docker login --username rafaalberto17 --password 'dockertest'

echo "*** Docker create image ***"
docker build -t timesheet-api .

echo "*** Docker create container ***"
docker run -d --name timesheet-api -e APP_OPTIONS='--spring.datasource.url=jdbc:postgresql://localhost:5432/timesheet' -p 8090:8080 --network=host timesheet-api

docker logout