## Instruction to create a PostgreSQL Docker container and load data into the DB

#### Create docker container
``` bash
docker run --name db-#APP_ABBREVIATION -p 55432:5432 -e POSTGRES_USER=#APP_ABBREVIATION -e POSTGRES_PASSWORD=#APP_ABBREVIATION -e POSTGRES_DB=#DATABASE_NAME -d postgres:15.1-alpine -c 'max_connections=500'
```

