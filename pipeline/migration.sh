echo "*** Migration Info ***"
./gradlew flywayInfo
echo "*** Migration Execution ***"
./gradlew migrateLocal
