# Hospital Management System: OOP & GUI Integration

### Project Overview
This project presents a fully interactive Hospital Management System featuring role-based access control. The objective of this project was to implement robust Object-Oriented Programming (OOP) principles from scratch, allowing for scalable management of medical staff, patients, and hospital appointments.

The project encompasses core business logic, a custom data persistence mechanism using File I/O, and a seamless Graphical User Interface (GUI) for an engaging administrative experience.

---

### The Interactive Hospital Environment (GUI)
We developed a user-friendly graphical interface using **Java Swing** to visualize user dashboards, handle administrative inputs smoothly, and display real-time feedback for data entry and appointment scheduling.

<img width="977" height="614" alt="image" src="https://github.com/user-attachments/assets/9f11f6fa-26d4-4ae7-a659-1a20b6d45103" />


---

### Technical Methodology & Architecture

**1. Core Architecture: Object-Oriented Design (OOP)** The foundation of the system's decision-making and access control relies on abstraction and inheritance. A central abstract `User` class is extended by `Admin`, `Doctor`, and `Patient` entities. This ensures code reusability, polymorphism in data handling, and strict logical separation of privileges.

**2. Data Persistence & State Management** To make the system viable across multiple sessions without a relational database, a custom File I/O manager was integrated.
* **Performance:** During runtime, the system utilizes `HashMap` data structures, ensuring $O(1)$ time complexity for data retrieval and updates.
* **Key Insight:** Allowed the system to seamlessly serialize object states into CSV-formatted `.txt` files (`users.txt`, `appointments.txt`, etc.), ensuring zero data loss upon application termination while maintaining high speed.

**3. Input Validation & Fault Tolerance** Since the system handles critical hospital data, custom evaluation methods (e.g., `try-catch` blocks and `Scanner` buffer clearing) were designed to catch `NumberFormatExceptions` and prevent system crashes during unexpected user inputs.

---

### System Capabilities & Workflow

<img width="912" height="599" alt="image" src="https://github.com/user-attachments/assets/eda04067-59c9-4367-95ae-c32971512bd2" />

<br>

1. **Dynamic Scheduling Logic:** The appointment system features built-in algorithmic checks to prevent double-booking conflicts, ensuring schedule integrity across all doctors.
2. **Role-Based Workflows:** The system isolates environments based on credentials. Admins control the ecosystem, Doctors manage their queues and update statuses, and Patients self-service their bookings.

---

### Tech Stack

* **Language:** Java
* **GUI Framework:** Java Swing (`JFrame`, `JOptionPane`, `LayoutManagers`)
* **Data Structures:** `HashMap`, `ArrayList`
* **Core Concepts:** File I/O, Exception Handling, Polymorphism, Encapsulation

---

### How to Run Locally
Run the following commands in your terminal to set up and start the system:

1. Clone the repository:
   
   ```
   git clone https://github.com/Hamza-Qabbari/Hospital-Management-System-Java.git
   ```
2. Navigate to the project directory:
   
   ```
   cd Hospital-Management-System-Java
   ```
3. Compile the Java files:
   
   ```
   javac *.java
   ```
4. Run the application:
   
   ```
   java LoginFrame
   ```
## Demo Credentials

To quickly test the application without looking into the database files, you can use the following default credentials:

| Role  | Username | Password |
| :---  | :---     | :---     |
| **Admin** | `admin`    | `admin123` |
| **Doctor** | `dr_ahmed` | `pass123`  |
| **Patient**| `hamza_h`  | `pass123`  |

---
