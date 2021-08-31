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

echo "*** Docker create image ***"
sudo docker build -t timesheet-api .

echo "*** Docker create container ***"
sudo docker run -d --name timesheet-api -e APP_OPTIONS='--spring.profiles.active=local' -p 8080:8080 --network=host timesheet-api:latest
