# MVC Architecture of the Application

Here is the detailed flowchart diagram of the MVC architecture for the Room Rental Management System, organized by layers (View, Controller, Service, Repository, and Model Entities).

```mermaid
flowchart LR
    %% Defining Styles
    classDef view fill:#f9d0c4,stroke:#333,stroke-width:2px;
    classDef ctrl fill:#d4e157,stroke:#333,stroke-width:2px;
    classDef srvc fill:#81d4fa,stroke:#333,stroke-width:2px;
    classDef repo fill:#ce93d8,stroke:#333,stroke-width:2px;
    classDef model fill:#ffcc80,stroke:#333,stroke-width:2px;
    classDef db fill:#b0bec5,stroke:#333,stroke-width:2px;

    Client((Client Browser))

    subgraph ViewLayer ["1. View Layer (Thymeleaf Templates)"]
        direction TB
        V_Auth["auth/ (login, register)"]:::view
        V_Home["dashboard.html"]:::view
        V_Room["add-room.html, room-detail.html, my-rooms.html"]:::view
        V_Appt["my-appointments.html, reject-appointment.html"]:::view
        V_Prof["profile.html, public-profile.html"]:::view
        V_Wish["wishlist.html"]:::view
        V_Frag["fragments/ (header, footer)"]:::view
    end

    subgraph ControllerLayer ["2. Controller Layer"]
        direction TB
        C_Auth["AuthController"]:::ctrl
        C_Home["HomeController"]:::ctrl
        C_Room["RoomController"]:::ctrl
        C_Appt["AppointmentController"]:::ctrl
        C_Prof["ProfileController"]:::ctrl
        C_Wish["WishlistController"]:::ctrl
    end

    subgraph ServiceLayer ["3. Service Layer (Business Logic)"]
        direction TB
        S_User["UserService"]:::srvc
        S_Room["RoomService"]:::srvc
        S_Appt["AppointmentService"]:::srvc
        S_Email["EmailService"]:::srvc
        S_Wish["WishlistService"]:::srvc
    end

    subgraph RepositoryLayer ["4. Repository Layer (Data Access)"]
        direction TB
        R_User["UserRepository"]:::repo
        R_Room["RoomRepository"]:::repo
        R_Appt["AppointmentRepository"]:::repo
        R_Wish["WishlistRepository"]:::repo
    end

    subgraph ModelLayer ["5. Domain Model Layer (Entities)"]
        direction TB
        M_User["User"]:::model
        M_Room["Room"]:::model
        M_Appt["Appointment"]:::model
        M_Wish["Wishlist"]:::model
    end

    DB[(Database)]:::db

    %% Connections
    Client <-->|HTTP Requests/Responses| ViewLayer
    
    %% View to Controller
    V_Auth <--> C_Auth
    V_Home <--> C_Home
    V_Room <--> C_Room
    V_Appt <--> C_Appt
    V_Prof <--> C_Prof
    V_Wish <--> C_Wish
    
    %% Controller to Service
    C_Auth --> S_User
    C_Home --> S_Room
    C_Room --> S_Room
    C_Room --> S_User
    C_Appt --> S_Appt
    C_Appt --> S_Email
    C_Appt --> S_User
    C_Appt --> S_Room
    C_Prof --> S_User
    C_Wish --> S_Wish

    %% Service to Repository
    S_User --> R_User
    S_Room --> R_Room
    S_Appt --> R_Appt
    S_Wish --> R_Wish

    %% Repository to Database
    R_User --> DB
    R_Room --> DB
    R_Appt --> DB
    R_Wish --> DB

    %% Cross-cutting Entity usage
    ServiceLayer -. uses .-> ModelLayer
    RepositoryLayer -. maps to .-> ModelLayer
    ControllerLayer -. binds .-> ModelLayer
```

## Layers Overview
- **View Layer**: Handles the presentation. Built using Thymeleaf templates (HTML/CSS) to render data sent from the Controllers.
- **Controller Layer**: Handles incoming HTTP requests, processes user input, orchestrates tasks with the Service layer, and returns the appropriate View or redirects.
- **Service Layer**: Contains the core business logic. It isolates the Controllers from data access details and coordinates between multiple repositories or external services (e.g., `EmailService`).
- **Repository Layer**: Interfaces for database operations using Spring Data JPA.
- **Model Layer**: JPA Entities that represent the database schema (users, rooms, appointments, wishlists) and are passed across layers.
