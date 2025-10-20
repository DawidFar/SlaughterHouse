# Slaughterhouse gRPC Service (PostgreSQL)

This project implements a gRPC service for the PRO3 assignment (Part 2).  
It provides two RPCs:
- `GetAnimalsByProduct(productId)` — returns registration numbers of animals whose parts are included in the product.
- `GetProductsByAnimal(animalId)` — returns products that contain parts from a given animal.

## Tech stack
- Java 17
- gRPC
- Protobuf
- PostgreSQL
- Maven

## Quick setup (PostgreSQL)
1. Start a PostgreSQL server (e.g., locally or Docker).
2. Create a database:
   ```bash
   createdb slaughterhouse
   ```
3. Run the SQL initialization script to create tables and sample data:
   ```bash
   psql -d slaughterhouse -f scripts/init_db.sql
   ```
   Or, if using Docker, copy the script into the container and run it.

4. Set environment variables (optional — defaults shown):
   - `DB_HOST` (default `localhost`)
   - `DB_PORT` (default `5432`)
   - `DB_NAME` (default `slaughterhouse`)
   - `DB_USER` (default `postgres`)
   - `DB_PASS` (default `postgres`)
   - `GRPC_PORT` (default `9090`)

## Build & run
1. Build the project:
   ```bash
   mvn clean package
   ```
   This will also generate Java sources from the `.proto` file.

2. Run the server:
   ```bash
   java -jar target/slaughterhouse-grpc-1.0-SNAPSHOT.jar
   ```
   or run from your IDE by executing `com.slaughterhouse.server.ServerMain`.

## Testing
- A sample JUnit test is included (requires DB).
- Alternatively, use BloomRPC / kreya / evans to call the service.

### Example requests (JSON for BloomRPC)
- `GetAnimalsByProduct`:
```json
{ "productId": 1 }
```

- `GetProductsByAnimal`:
```json
{ "animalId": 1 }
```

## Notes
- Tests assume a running PostgreSQL instance; adjust DB env vars as needed.
- For production use, add connection pooling (HikariCP) and better error handling.
