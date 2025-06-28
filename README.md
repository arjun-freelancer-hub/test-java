# Todo API

A RESTful API for managing todo items built with Spring Boot and Java 17.

## Features

- ✅ CRUD operations for todo items
- ✅ Search functionality
- ✅ Filter by completion status
- ✅ Statistics endpoint
- ✅ Input validation
- ✅ H2 database for development
- ✅ PostgreSQL for production
- ✅ Docker support
- ✅ Ready for Render deployment

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (Development)
- **PostgreSQL** (Production)
- **Maven**
- **Docker**

## Local Setup

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd transport
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:tododb`
     - Username: `sa`
     - Password: `password`

## API Endpoints

### Base URL: `http://localhost:8080/api/todos`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Get all todos |
| GET | `/{id}` | Get todo by ID |
| POST | `/` | Create new todo |
| PUT | `/{id}` | Update todo |
| PATCH | `/{id}/status?completed={boolean}` | Update todo completion status |
| DELETE | `/{id}` | Delete todo |
| GET | `/status/{completed}` | Get todos by completion status |
| GET | `/search?q={searchTerm}` | Search todos by title or description |
| GET | `/title?title={title}` | Get todos by title |
| GET | `/description?description={description}` | Get todos by description |
| GET | `/statistics` | Get todo statistics |
| GET | `/health` | Health check |

### Request/Response Examples

#### Create Todo
```bash
POST /api/todos
Content-Type: application/json

{
  "title": "Buy groceries",
  "description": "Milk, bread, eggs, and vegetables"
}
```

#### Update Todo
```bash
PUT /api/todos/1
Content-Type: application/json

{
  "title": "Buy groceries",
  "description": "Milk, bread, eggs, vegetables, and fruits"
}
```

#### Update Todo Status
```bash
PATCH /api/todos/1/status?completed=true
```

#### Search Todos
```bash
GET /api/todos/search?q=grocery
```

#### Get Statistics
```bash
GET /api/todos/statistics
```

Response:
```json
{
  "totalTodos": 8,
  "completedTodos": 2,
  "pendingTodos": 6
}
```

## Database Schema

The application uses the following database schema:

```sql
CREATE TABLE todos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
```

## Docker Setup

### Build Docker Image
```bash
docker build -t todo-api .
```

### Run Docker Container
```bash
docker run -p 8080:8080 todo-api
```

## Deployment to Render

### Prerequisites
- Render account
- Git repository with your code

### Steps

1. **Push your code to GitHub**
   ```bash
   git add .
   git commit -m "Initial commit"
   git push origin main
   ```

2. **Connect to Render**
   - Go to [render.com](https://render.com)
   - Sign up/Login
   - Click "New +" and select "Web Service"

3. **Configure the service**
   - **Name**: `todo-api`
   - **Environment**: `Java`
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -jar target/todo-api-1.0.0.jar`
   - **Plan**: Free

4. **Set Environment Variables**
   - `SPRING_PROFILES_ACTIVE`: `prod`
   - `PORT`: `8080`

5. **Create Database**
   - Click "New +" and select "PostgreSQL"
   - Name: `todo-db`
   - Plan: Free
   - Copy the database URL

6. **Link Database to Service**
   - Go back to your web service
   - Add the database URL as environment variable: `DATABASE_URL`
   - Add database credentials if needed

7. **Deploy**
   - Click "Create Web Service"
   - Render will automatically build and deploy your application

### Environment Variables for Production

Make sure to set these environment variables in Render:

- `SPRING_PROFILES_ACTIVE`: `prod`
- `DATABASE_URL`: Your PostgreSQL connection string
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `PORT`: `8080`

## Testing

### Using curl

```bash
# Get all todos
curl http://localhost:8080/api/todos

# Create a todo
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title": "Test todo", "description": "Test description"}'

# Get todo by ID
curl http://localhost:8080/api/todos/1

# Update todo
curl -X PUT http://localhost:8080/api/todos/1 \
  -H "Content-Type: application/json" \
  -d '{"title": "Updated todo", "description": "Updated description"}'

# Mark todo as completed
curl -X PATCH "http://localhost:8080/api/todos/1/status?completed=true"

# Delete todo
curl -X DELETE http://localhost:8080/api/todos/1

# Search todos
curl "http://localhost:8080/api/todos/search?q=test"

# Get statistics
curl http://localhost:8080/api/todos/statistics
```

### Using Postman

Import the following collection:

```json
{
  "info": {
    "name": "Todo API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Get All Todos",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/todos"
      }
    },
    {
      "name": "Create Todo",
      "request": {
        "method": "POST",
        "url": "http://localhost:8080/api/todos",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"title\": \"Test todo\",\n  \"description\": \"Test description\"\n}"
        }
      }
    }
  ]
}
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── todoapi/
│   │               ├── TodoApiApplication.java
│   │               ├── controller/
│   │               │   └── TodoController.java
│   │               ├── service/
│   │               │   └── TodoService.java
│   │               ├── repository/
│   │               │   └── TodoRepository.java
│   │               ├── model/
│   │               │   └── Todo.java
│   │               └── dto/
│   │                   └── TodoRequest.java
│   └── resources/
│       ├── application.properties
│       ├── application-prod.properties
│       └── data.sql
└── test/
    └── java/
        └── com/
            └── example/
                └── todoapi/
                    └── TodoApiApplicationTests.java
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License. 