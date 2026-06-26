wh# System Design Document: Room Rental Management System

This document outlines the overall system architecture and functional modules of the Room Rental Management System. This project is built using Spring Boot, Thymeleaf, and Oracle Database.

## 1. System Architecture

The application follows a standard **Model-View-Controller (MVC)** layered architecture. This separates the application's concerns into three main layers:

- **Model Layer**: Represents the business logic and data state. Composed of Entities (mapped to the Oracle Database) and Spring Data JPA Repositories.
- **View Layer**: The presentation tier built with Thymeleaf templates. It dynamically renders HTML based on data provided by the Controllers.
- **Controller Layer**: Handles incoming HTTP requests, interacts with Services to process business rules, and returns the appropriate View.

### MVC Architecture Diagram

```mermaid
flowchart TD
    %% Define Styles
    classDef client fill:#ffcccb,stroke:#333,stroke-width:2px;
    classDef view fill:#f9d0c4,stroke:#333,stroke-width:2px;
    classDef ctrl fill:#d4e157,stroke:#333,stroke-width:2px;
    classDef srvc fill:#81d4fa,stroke:#333,stroke-width:2px;
    classDef repo fill:#ce93d8,stroke:#333,stroke-width:2px;
    classDef db fill:#b0bec5,stroke:#333,stroke-width:2px;

    Client((User Browser)):::client
    DB[(Oracle Database)]:::db

    subgraph "Spring Boot Application"
        View["View Layer (Thymeleaf Templates)"]:::view
        Controller["Controller Layer (@Controller)"]:::ctrl
        Service["Service Layer (@Service)"]:::srvc
        Repository["Repository Layer (@Repository)"]:::repo
        Model["Entity Layer (@Entity)"]
    end

    Client <-->|HTTP GET/POST| Controller
    Controller -->|Forwards Data & Renders| View
    View -->|Displays to| Client
    
    Controller <-->|Calls Business Logic| Service
    Service <-->|CRUD Operations| Repository
    Repository <-->|SQL Queries| DB
    
    %% Entity Usage
    Service -. uses .-> Model
    Repository -. maps .-> Model
```

## 2. Technology Stack

- **Backend Framework**: Spring Boot 3.x (Java 17)
- **Frontend Template Engine**: Thymeleaf (HTML5, CSS)
- **Database**: Oracle Database (via `ojdbc11`)
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Other Utilities**: Lombok, Spring Boot Mail (for notifications)

---

## 3. Data Model (Entity-Relationship)

The system revolves around four main entities: `User`, `Room`, `Appointment`, and `Wishlist`.

```mermaid
erDiagram
    USER {
        Long id PK
        String name
        String email
        String password
        String phone
        String gender
    }
    
    ROOM {
        Long id PK
        String title
        String type
        Double price
        String location
        String status
        String description
        Integer bedrooms
        Integer bathrooms
        Boolean fullyFurnished
        Long owner_id FK
        Long current_tenant_id FK
    }

    APPOINTMENT {
        Long id PK
        LocalDateTime dateTime
        String status
        String notes
        String rejectReason
        LocalDateTime suggestedTimeFrom
        LocalDateTime suggestedTimeTo
        Long room_id FK
        Long tenant_id FK
    }

    WISHLIST {
        Long id PK
        Long user_id FK
        Long room_id FK
    }

    USER ||--o{ ROOM : "owns"
    USER ||--o{ ROOM : "rents"
    USER ||--o{ APPOINTMENT : "makes"
    USER ||--o{ WISHLIST : "saves"
    ROOM ||--o{ APPOINTMENT : "receives"
    ROOM ||--o{ WISHLIST : "added to"
```

---

## 4. Functional Modules

### 4.1 Authentication & User Management
- **User Registration & Login**: Users can sign up with their email, name, password, and phone number.
- **Profile Management**: Users can update their personal information and view their public profile.
- **Role Flexibility**: A user can act as both an **Owner** (listing rooms) and a **Tenant** (viewing rooms and making appointments).

### 4.2 Room Management (Owner Module)
- **Listing Creation**: Owners can create room listings, specifying the type (e.g., Whole Unit, Single Room), price, location, and detailed facilities (e.g., WiFi, air conditioning, fully furnished).
- **Listing Maintenance**: Owners can view and manage their listed rooms via the `My Rooms` dashboard.
- **Status Tracking**: Rooms have statuses indicating availability (e.g., Available, Rented).

### 4.3 Room Browsing & Wishlist (Tenant Module)
- **Dashboard & Search**: Tenants can view a dashboard of available rooms.
- **Detailed View**: Tenants can click into a room to see full details, facilities, and the owner's public profile.
- **Wishlist**: Users can save interesting rooms to their personal wishlist for later review.

### 4.4 Viewing Appointments Module
This is the core interaction feature connecting Tenants and Owners.
- **Booking**: A Tenant can request a viewing appointment for an available room by selecting a date and time and leaving notes.
- **Status Workflow**: 
  - **Pending**: Initial state when the tenant makes the request.
  - **Confirmed**: Owner accepts the viewing schedule.
  - **Rejected**: Owner declines the request, providing a `rejectReason` and optionally suggesting alternative times (`suggestedTimeFrom`, `suggestedTimeTo`).
- **Notifications**: Triggered via `EmailService` when appointment statuses change.

### Appointment Workflow Diagram
```mermaid
stateDiagram-v2
    [*] --> Pending : Tenant Requests Viewing
    
    Pending --> Confirmed : Owner Accepts
    Pending --> Rejected : Owner Rejects
    
    Rejected --> Pending : Tenant Proposes New Time
    
    Confirmed --> [*]
    Rejected --> [*]
```
