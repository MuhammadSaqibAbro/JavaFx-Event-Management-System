# 🎟️ Event Management System - JavaFX

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)  
![JavaFX](https://img.shields.io/badge/JavaFX-1C1C1C?style=for-the-badge&logo=java&logoColor=white)  
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

---

## 🚀 Project Overview

The **Event Management System** is a comprehensive desktop application built using **JavaFX** that allows users to **sign up, log in**, and manage **event schedules, tickets, attendees, and participants**. It features **role-based dashboards** for **Managers**, **Event Coordinators**, and **Support Operators** with full **CRUD functionalities**. All data is persistently stored using `.txt` files, eliminating the need for external databases.

> 📌 **Main Class to Run**: `EventManagementDashboard.java`

---

## 🎯 Features

✅ **User Authentication** with role-based access  
✅ **Sign Up / Login** functionality  
✅ Separate **dashboards** for **Manager**, **Event Coordinator**, **Support Operator**  
✅ **Ticket Confirmation System**  
✅ **Event Scheduling** & time management  
✅ **Attendee** & **Participant Tracking**  
✅ Full **CRUD** (Create, Read, Update, Delete) operations for events and attendees  
✅ **Analytics & Insights Dashboard**  
✅ **Modern UI** with styled components  
✅ Data stored securely in `.txt` files  

---

## 🧑‍💻 Roles and Permissions

| Role               | Description                                               |
|--------------------|-----------------------------------------------------------|
| 🧑‍💼 **Manager**          | Full access to events, users, attendees, and analytics     |
| 🎯 **Event Coordinator** | Can create/edit events and manage scheduling              |
| 🧰 **Support Operator**  | View attendees and assist with support operations         |

---

## 🛠️ Technologies Used

- **[JavaFX](https://openjfx.io/)** – Modern UI and components
- **[Java](https://www.java.com/)** – Core application logic
- **TXT Files** – Used as a lightweight database
- **OOP Concepts** – Classes for Event, Attendee, Ticket, Schedule, etc.

---
## Screenshots
![fx1](https://github.com/user-attachments/assets/4a26abe1-fdb5-422c-8996-e43bfdcae877)
![fx2](https://github.com/user-attachments/assets/97d500fe-800e-44e2-bf15-669a39f41159)
![fx3](https://github.com/user-attachments/assets/8ed02c3d-dbc6-48d8-875e-f8cc6e47a8ca)
![fx4](https://github.com/user-attachments/assets/79f26824-5750-4c61-bc67-bc5d5ad988c5)
![fx5](https://github.com/user-attachments/assets/16f86eed-c3e6-4b25-82b4-3dbd425eb957)

## 📂 Project Structure

```plaintext
├── src/
│   ├── com.example.javafxapplication/
│   │   ├── LoginView.java
│   │   ├── EventManagementDashboard.java
│   │   ├── AddEventDashboard.java
│   │   ├── AnalyticsPage.java
│   │   ├── Attendee.java
│   │   ├── ManagerDashboard.java
│   │   ├── EventCoordinatorDashboard.java
│   │   ├── SupportOperatorDashboard.java
│   ├── resources/
│   │   ├── users.txt
│   │   ├── events.txt
│   │   ├── tickets.txt
│   │   ├── schedule.txt


