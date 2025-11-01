# Adaptive Learning Platform

## Overview
This is a full-stack adaptive learning platform that generates AI-powered questionnaires using OpenAI's API. The application consists of:
- **Backend**: Spring Boot (Java) REST API with JWT authentication
- **Frontend**: Angular 20 Single Page Application  
- **Database**: H2 in-memory database (development), Oracle Database (production)
- **AI Integration**: OpenAI API for generating adaptive questionnaires

## Project Structure
```
├── api/                    # Spring Boot backend
│   ├── src/main/java/     # Java source code
│   │   ├── config/        # Security, Mail, and OpenAPI configuration
│   │   ├── controller/    # REST API controllers
│   │   ├── dto/           # Data Transfer Objects
│   │   ├── model/         # JPA entities
│   │   ├── repository/    # Database repositories
│   │   ├── service/       # Business logic
│   │   └── utils/         # Utility classes
│   ├── src/main/resources/
│   │   ├── application.yml        # Production configuration
│   │   ├── application-dev.yml    # Development configuration
│   │   └── static/                # Angular build output
│   └── build.gradle       # Backend build configuration
├── ui/                    # Angular frontend
│   ├── src/app/          # Angular components and services
│   ├── package.json      # Frontend dependencies
│   ├── angular.json      # Angular configuration
│   └── build.gradle      # Frontend build configuration with node-gradle plugin
├── build.gradle          # Root build configuration
└── settings.gradle       # Multi-project Gradle settings
```

## Technology Stack
- **Backend**: Spring Boot 3.4.2, Java 19, Spring Security, Spring Data JPA
- **Frontend**: Angular 20, RxJS, TypeScript
- **Build Tools**: Gradle 9.1.0, Node.js 22
- **Database**: H2 (dev), Oracle (prod), MySQL (supported)
- **Authentication**: JWT tokens with Spring Security
- **AI**: OpenAI API integration

## Recent Changes
- **2025-11-01**: Imported from GitHub and configured for Replit environment
  - Configured Spring Boot to run on port 5000 for Replit webview
  - Set up Angular build to copy output to Spring Boot static resources
  - Updated Spring Security configuration to allow frontend static assets
  - Created HomeController to forward root path to Angular app
  - Configured OPENAI_API_KEY environment variable for AI features
  - Updated Java version requirement from 17 to 19 (Replit environment)
  - Fixed frontend asset serving by copying from browser subdirectory
  - Updated Angular proxy configuration to target port 5000 (matching backend)

## Running the Application

### Development Mode
The application is configured to run automatically via the workflow:
1. The workflow builds both frontend and backend
2. Spring Boot serves the Angular application on port 5000
3. Backend API is available at `/api/*` endpoints
4. Frontend is served at the root `/` path

### Building Manually
```bash
# Build entire project (frontend + backend)
./gradlew clean build -x test

# Build only backend
cd api && ../gradlew build -x test

# Build only frontend  
cd ui && npm run build
```

### Running Manually
```bash
# Run the application
java -jar api/build/libs/api.war --spring.profiles.active=dev
```

## Environment Configuration

### Required Secrets
- `OPENAI_API_KEY`: OpenAI API key for generating questionnaires

### Development Configuration
- Port: 5000 (configured in `application-dev.yml` and `ui/proxy.conf.json`)
- Database: H2 in-memory database
- Profile: dev
- OpenAI API: Enabled via environment variable
- Note: Both backend server and Angular proxy must use port 5000

### Production Configuration  
- Database: Oracle Database (requires TNS configuration)
- Mail: SMTP configuration required
- Profile: prod (set in `application.yml`)

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `POST /api/auth/verify-otp` - Verify OTP

### Questionnaires
- `POST /api/questionnaire/generate` - Generate new questionnaire (requires authentication)
- `POST /api/questionnaire/adaptive/next` - Get next adaptive question
- `POST /api/questionnaire/submit` - Submit questionnaire answers

## User Preferences
- Angular configured to run on port 5000 with all hosts allowed for Replit proxy
- Gradle builds are automated via workflow
- Development mode uses H2 database (no external DB setup needed)

## Architecture Notes

### Build Process
1. Angular frontend is built first (`ui:build` task)
2. Frontend output (`ui/dist/ui/browser/*`) is copied to Spring Boot `static/` directory
3. Spring Boot is packaged as an executable WAR file
4. The WAR file contains both the API and the frontend

### Security Configuration
- JWT-based authentication for API endpoints
- Public access to static frontend assets
- CORS disabled for development
- HTTP Basic authentication available as fallback

### Database Schema
- **users**: User authentication and profile data
- **questionnaire**: Generated questionnaires with metadata
- **question**: Individual questions within questionnaires  
- **question_option**: Multiple choice options for questions
- **student_answer**: Student responses to questions

## Deployment
The application is configured for Replit autoscale deployment:
- **Build**: Gradle clean build (excluding tests)
- **Run**: Java executable WAR file with dev profile
- **Port**: 5000 (exposed for webview)
- **Type**: Autoscale (stateless web application)

## Development Notes
- LSP diagnostics for Java files may show errors due to IDE configuration, but Gradle builds successfully
- The application uses Spring Boot's default static resource handling
- Angular routing is handled client-side with HTML5 history mode
- API base URL is configured as relative path `/api` in Angular environment files
