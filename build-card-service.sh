name="gundam-card-service"
echo "🗃🗃🗃🗃️ BUILDING CARD SERVICE  🗃🗃🗃🗃"
./gradlew build
docker build -t $name .