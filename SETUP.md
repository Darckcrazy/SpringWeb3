# Blogging App - Exercise #1 Implementation

This project implements the required features for Exercise #1:

- ✅ Payload validation with correct error messages
- ✅ Cloudinary integration for avatar and cover uploads
- ✅ Email confirmation on author creation
- ✅ Secure configuration management

## Features Implemented

### 1. Payload Validation

- Created `AuthorPayload` DTO with validation annotations
- Enhanced `BlogPostPayload` DTO with comprehensive validation
- Added `@Valid` annotations to controllers for automatic validation
- Custom error messages for all validation rules

### 2. Cloudinary Integration

- Added Cloudinary dependency to `pom.xml`
- Created `CloudinaryConfig` for configuration
- Implemented `CloudinaryService` for file uploads
- Added upload endpoints:
  - `POST /authors/{id}/avatar` - Upload author avatar
  - `POST /blogs/{id}/cover` - Upload blogpost cover

### 3. Email Service

- Added Spring Boot Mail dependency
- Created `EmailService` for sending confirmation emails
- Automatic email sent when new author is created
- Graceful error handling (email failure doesn't break author creation)

### 4. Security & Configuration

- Environment variables for sensitive data
- Default values in `application.properties`
- File upload size limits (10MB max)

## Setup Instructions

### 1. Environment Variables

Create a `.env` file in the project root with the following variables:

```bash
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/Blogg
DB_USERNAME=postgres
DB_PASSWORD=your_db_password

# Cloudinary Configuration
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret

# Email Configuration
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
MAIL_FROM=noreply@yourblog.com
```

### 2. Cloudinary Setup

1. Create a free account at [Cloudinary](https://cloudinary.com/)
2. Get your Cloud Name, API Key, and API Secret from the dashboard
3. Update the environment variables with your credentials

### 3. Email Setup (Gmail)

1. Enable 2-factor authentication on your Gmail account
2. Generate an App Password for this application
3. Use the App Password (not your regular password) in the `MAIL_PASSWORD` variable

### 4. Database Setup

1. Make sure PostgreSQL is running
2. Create a database named `Blogg`
3. Update database credentials in environment variables

## API Endpoints

### Authors

- `POST /authors` - Create author (with validation)
- `GET /authors` - Get all authors
- `GET /authors/{id}` - Get author by ID
- `PUT /authors/{id}` - Update author
- `DELETE /authors/{id}` - Delete author
- `POST /authors/{id}/avatar` - Upload author avatar

### Blogs

- `POST /blogs` - Create blog post (with validation)
- `GET /blogs` - Get all blogs (paginated)
- `GET /blogs/all` - Get all blogs (no pagination)
- `GET /blogs/{id}` - Get blog by ID
- `PUT /blogs/{id}` - Update blog
- `DELETE /blogs/{id}` - Delete blog
- `POST /blogs/{id}/cover` - Upload blog cover

## Validation Rules

### AuthorPayload

- `name`: Required, 2-50 characters
- `surname`: Required, 2-50 characters
- `email`: Required, valid email format
- `dateOfBirth`: Optional, must be in the past

### BlogPostPayload

- `title`: Required, 3-100 characters
- `content`: Required, minimum 10 characters
- `readingTime`: Required, 1-120 minutes
- `authorId`: Required, valid UUID
- `category`: Optional
- `cover`: Optional

## File Upload

- Maximum file size: 10MB
- Supported formats: All image formats
- Files are organized in Cloudinary folders:
  - Author avatars: `authors/avatars/`
  - Blog covers: `blogs/covers/`

## Running the Application

1. Set up environment variables
2. Run the application: `mvn spring-boot:run`
3. Application will be available at `http://localhost:3001`

## Testing

You can test the API using tools like Postman or curl. Make sure to:

1. Create an author first
2. Use the author ID when creating blog posts
3. Test file uploads with the upload endpoints
4. Verify email functionality by creating a new author
