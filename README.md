## 🐄 Slaughterhouse gRPC Service (PRO3 Assignment Part 2)

This project is part of the **PRO3 course assignment**.  
The task was to implement a **gRPC-based service** that can fetch data about animals and products from a slaughterhouse system using a **PostgreSQL** database.

The system can:
- Find all **animals** involved in a given **product**
- Find all **products** that contain parts from a given **animal**

Everything is implemented using **Java**, **gRPC**, and **PostgreSQL**.

---

### 🧠 How it works

The slaughterhouse data is stored in a PostgreSQL database with these tables:
- `animal` – holds registration numbers and weight  
- `part` – represents parts cut from animals  
- `tray` – contains parts of the same type  
- `product` – final packaged product  
- `producttray` – link between products and trays

The gRPC service connects to the database and exposes two methods:

| RPC | Description |
|-----|--------------|
| `GetAnimalsByProduct(ProductRequest)` | Returns all animals that have parts included in a given product |
| `GetProductsByAnimal(AnimalRequest)` | Returns all products that include parts from a given animal |

---

### ⚙️ Setup (PostgreSQL)

1. **Create the database**
   ```bash
   createdb slaughterhouse
   ```

2. **Run the SQL script**  
   The script creates all tables and inserts some sample data.
   ```bash
   psql -d slaughterhouse -f scripts/init_db.sql
   ```

3. **Set environment variables** (optional – defaults are below):
   ```
   DB_HOST=localhost
   DB_PORT=5432
   DB_NAME=slaughterhouse
   DB_USER=postgres
   DB_PASS=postgres
   GRPC_PORT=9090
   ```

---

### 🏗️ Build and run

Build the project with Maven:
```bash
mvn clean package
```

Run the server:
```bash
java -jar target/slaughterhouse-grpc-1.0-SNAPSHOT.jar
```

If everything works, you should see:
```
gRPC server started on port 9090
```

---

### 🧪 Testing

You can test the service in **BloomRPC** or **Kreya** using the `.proto` file found at:
```
src/main/proto/slaughterhouse.proto
```

Example test calls:

#### Get animals by product
```json
{ "productId": 1 }
```

#### Get products by animal
```json
{ "animalId": 1 }
```

You can also run the included JUnit test with:
```bash
mvn test
```

---

### 📦 What’s included

- `src/` – Java source code and proto files  
- `scripts/init_db.sql` – Database schema and sample data  
- `pom.xml` – Maven build setup  
- `start.sh` – Helper script to build and run  
- `README.md` – This file  

---

### 💬 Notes

- The service uses a direct PostgreSQL connection (no ORM).  
- Error handling is basic but functional for assignment purposes.
