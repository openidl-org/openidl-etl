cd ..;
./zip.sh;
cd test;
./deploy.sh;
sleep 30s
./delete.sh;
./trigger.sh
