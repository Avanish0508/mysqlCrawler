# Database Schema Crawler & Model Generator (Java + Spring Boot)

This project is a **Spring Boot-based schema crawler** that connects to a MySQL database, extracts schema metadata (tables, columns, keys, and relationships), and automatically generates Java model classes from the live database structure.

---

## ✅ Features

- Connects to MySQL using configuration from `dbconfig.json`
- Crawls metadata: tables, columns, primary keys, foreign keys
- Detects many-to-many join tables
- Exposes metadata via REST API (`/api/crawler/run`)
- Dynamically generates `.java` model files based on tables

---

## ⚙️ Technologies Used

- Java 17
- Spring Boot 3
- Gradle
- MySQL
- JDBC
- Jackson (for JSON)
- IntelliJ IDEA (Community Edition)

---

## 🚀 How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/crawlerr.git
   cd crawlerr
   
2. Create a MySQL database and add sample schema:
   CREATE TABLE Students (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE Courses (
    course_id INT PRIMARY KEY,
    course_name VARCHAR(100)
);

CREATE TABLE Student_Courses (
    student_id INT,
    course_id INT,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES Students(student_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

3. Configure your dbconfig.json in src/main/resources/:
{
  "url": "jdbc:mysql://localhost:3306/your_database_name",
  "username": "root",
  "password": "your_password"
}

4. Run the application:

In IntelliJ, run CrawlerrApplication.java

Or use:
./gradlew bootRun

5. Open browser and visit:
   http://localhost:8080/api/crawler/run

6. Check Generated models -
Located in:
src/main/java/com/example/crawlerr/generatedmodels/

Files: Students.java, Courses.java, StudentCourses.java (auto-generated)

📄 Sample Output (JSON Response)
[
  {
    "tableName": "Student_Courses",
    "joinTable": true,
    "columns": [
      {"name": "student_id", "type": "INT", "size": 11, "nullable": false},
      {"name": "course_id", "type": "INT", "size": 11, "nullable": false}
    ],
    "primaryKeys": ["student_id", "course_id"],
    "foreignKeys": [
      {"columnName": "student_id", "referencedTable": "Students", "referencedColumn": "student_id"},
      {"columnName": "course_id", "referencedTable": "Courses", "referencedColumn": "course_id"}
    ]
  }
]

📁 Folder Structure

crawlerr/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/crawlerr/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── model/
│   │   │       └── generatedmodels/ ← auto-created
│   └── resources/
│       ├── application.properties
│       └── dbconfig.json


🙌 Avanish
