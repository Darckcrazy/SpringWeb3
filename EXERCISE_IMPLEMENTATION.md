# Spring Boot Blogging Application - Exercise Implementation

This document describes the implementation of the required modifications for the blogging application exercise.

## ✅ Completed Requirements

### 1. PostgreSQL Support

- Added `spring-boot-starter-data-jpa` and `postgresql` dependencies to `pom.xml`
- Configured PostgreSQL connection in `application.properties`
- Set up JPA configuration with Hibernate dialect

### 2. Entity Relationships

- **Author Entity**: Converted to JPA entity with UUID primary key
  - Fields: `id`, `name`, `surname`, `email`, `dateOfBirth`, `avatar`
  - One-to-Many relationship with Blogpost
- **Blogpost Entity**: Converted to JPA entity with UUID primary key
  - Fields: `id`, `category`, `title`, `cover`, `content`, `readingTime`, `createdAt`
  - Many-to-One relationship with Author
  - Automatic timestamp creation with `@PrePersist`

### 3. Error Handling

- Enhanced `NotFoundException` to support UUID and entity type
- Created `GlobalExceptionHandler` for centralized error handling
- Proper HTTP status codes and error responses

### 4. Pagination and Sorting

- Added pagination support to blog posts endpoint
- Configurable page size, sorting field, and sort direction
- Default sorting by `createdAt` in descending order

### 5. BlogPostPayload DTO

- Created `BlogPostPayload` class as specified in the exercise
- Fields: `title`, `content`, `readingTime`, `authorId`
- Used for creating new blog posts with author relationship

## 🚀 API Endpoints

### Authors

- `POST /authors` - Create author
- `GET /authors` - Get all authors
- `GET /authors/{id}` - Get author by UUID
- `PUT /authors/{id}` - Update author
- `DELETE /authors/{id}` - Delete author

### Blog Posts

- `POST /blogs` - Create blog post (requires BlogPostPayload)
- `GET /blogs` - Get blog posts with pagination and sorting
- `GET /blogs/all` - Get all blog posts (no pagination)
- `GET /blogs/{id}` - Get blog post by UUID
- `PUT /blogs/{id}` - Update blog post
- `DELETE /blogs/{id}` - Delete blog post

## 📝 Example Usage

### Create Author

```bash
POST /authors
Content-Type: application/json

{
  "name": "Mario",
  "surname": "Rossi",
  "email": "mario.rossi@example.com",
  "dateOfBirth": "1990-01-15"
}
```

### Create Blog Post

```bash
POST /blogs
Content-Type: application/json

{
  "title": "Titolo",
  "content": "Contenuto del post",
  "readingTime": 10,
  "authorId": "0b76b4b5-76e9-415c-b8d6-b2656f092566"
}
```

### Get Blog Posts with Pagination

```bash
GET /blogs?page=0&size=5&sortBy=createdAt&sortDir=desc
```

## 🗄️ Database Configuration

Make sure PostgreSQL is running with:

- Database: `blogging_app`
- Username: `postgres`
- Password: `postgres`
- Port: `5432`

## 🏃‍♂️ Running the Application

1. Start PostgreSQL server
2. Create database: `blogging_app`
3. Run the application: `./mvnw spring-boot:run`
4. Application will be available at: `http://localhost:3001`

## 📊 Key Features

- **UUID Primary Keys**: All entities use UUID for better distributed system support
- **Automatic Timestamps**: Blog posts automatically get `createdAt` timestamp
- **Avatar Generation**: Authors get automatic avatar URLs if not provided
- **Cover Image**: Blog posts get random cover images if not provided
- **Relationship Management**: Proper JPA relationships between Author and Blogpost
- **Error Handling**: Comprehensive error handling with proper HTTP status codes
- **Pagination**: Efficient pagination with sorting for large datasets
