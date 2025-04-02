# YelpClone

A full-stack application that replicates core features of Yelp, allowing users to search for and review businesses.

## Features

- User authentication and authorization with JWT
- Business listing and search
- Review system with ratings
- Business hours management
- Password reset functionality
- Responsive design

## Tech Stack

### Backend
- Java Spring Boot
- Spring Security with JWT
- Spring Data JPA
- PostgreSQL
- Maven

### Frontend (Coming Soon)
- React
- Redux
- Material UI
- Axios

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven
- PostgreSQL
- Node.js and npm (for frontend)

### Backend Setup

1. Clone the repository
```
git clone https://github.com/yourusername/YelpClone.git
cd YelpClone
```

2. Configure the database
- Create a PostgreSQL database named `yelpclone`
- Update the database credentials in `src/main/resources/application.properties`

3. Configure email settings
- Update the email configuration in `src/main/resources/application.properties`

4. Build and run the application
```
mvn clean install
mvn spring-boot:run
```

The backend will be available at `http://localhost:8080`

### API Endpoints

#### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token
- `POST /api/auth/forgot-password` - Request password reset
- `POST /api/auth/reset-password` - Reset password with token

#### Businesses
- `GET /api/businesses` - Get all businesses
- `GET /api/businesses/{id}` - Get business by ID
- `POST /api/businesses` - Create a new business
- `PUT /api/businesses/{id}` - Update a business
- `DELETE /api/businesses/{id}` - Delete a business
- `GET /api/businesses/search?query={query}` - Search businesses
- `GET /api/businesses/category/{category}` - Get businesses by category
- `GET /api/businesses/top-rated` - Get top rated businesses
- `GET /api/businesses/most-reviewed` - Get most reviewed businesses

#### Reviews
- `GET /api/reviews/business/{businessId}` - Get reviews for a business
- `GET /api/reviews/user/{userId}` - Get reviews by a user
- `POST /api/reviews` - Create a new review
- `PUT /api/reviews/{id}` - Update a review
- `DELETE /api/reviews/{id}` - Delete a review

## License

This project is licensed under the MIT License - see the LICENSE file for details. 