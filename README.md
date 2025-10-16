#web_lab3

## Запуск
```bash
./gradlew build
```
```bash
docker-compose up -d
```
- `http:localhost:55090`: Configuration > Subsystems > Datasources & Drivers > Datasources (+) > Add Datasource
- там найти бд. URL для соединения: поменять `localhost` на `db`, `<DATABASE_NAME>` поменять на `pointsdb`
- запустить .war с приложением
