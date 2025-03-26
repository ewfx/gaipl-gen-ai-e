# 🚀 Project Name

## 📌 Table of Contents
- [Introduction](#introduction)
- [Demo](#demo)
- [Inspiration](#inspiration)
- [What It Does](#what-it-does)
- [How We Built It](#how-we-built-it)
- [Challenges We Faced](#challenges-we-faced)
- [How to Run](#how-to-run)
- [Tech Stack](#tech-stack)
- [Team](#team)

---

## 🎯 Introduction
A brief overview of your project and its purpose. Mention which problem statement are your attempting to solve. Keep it concise and engaging.

## 🎥 Demo
🔗 [Live Demo](#) (if applicable)  
📹 [Video Demo](#) (if applicable)  
🖼️ Screenshots:

![Screenshot 1](link-to-image)

## 💡 Inspiration
An AI enabled Dashboard for managing platform related incidents, viewing past related incidents and getting resolution recommendations from historical data using AI.

## ⚙️ What It Does
Explained in the document present in the artifacts folder.

## 🛠️ How We Built It
Explained in the document present in the artifacts folder. Also written below.

## 🚧 Challenges We Faced
Difficulty in obtaining realistic test data. Also, the scope of the challenge was very huge, we tried our best.

## 🏃 How to Run
1. Clone the repository  
   ```sh
   git clone https://github.com/ewfx/gaipl-gen-ai-e.git
   ```
2. Go to vectordb folder and run the following command to deploy pgvector database to store vector embeddings of incidents and to run 'ollama' AI embedding model locally.
   ```sh
   docker-compose up -d (On Windows/Mac: First Install Docker Desktop, which includes Docker Compose)
   ```
3. Ensure JAVA 17 or later is installed in your system.
4. Build the Spring Boot Backend JAR. Navigate to the project directory:
   ```sh
   cd /gaipl-gen-ai-e/code/springboot-backend
   ```
5. Build the JAR:
   ```sh
   ./gradlew clean build
   ```
6. The JAR will be generated under build/libs/your-app-name-0.0.1-SNAPSHOT.jar
7. Run the JAR:
   ```sh
   java -jar build/libs/your-app-name-0.0.1-SNAPSHOT.jar
   ```
8. Install the following for front-end setup.
   ```sh
   Node.js (LTS version recommended, e.g., 20.x)
   npm
   ```
9. Then, navigate into the project directory:
   ```sh
   cd /gaipl-gen-ai-e/code/Incident_Dashboard_Final
   ```
10. Run the following command to install all required dependencies:
   ```sh
   npm install
   ```
11. To run the project in development mode:
   ```sh
   npm run dev
   ```
12. This will start the AI enabled app on http://localhost:5173

## 🏗️ Tech Stack
- 🔹 Frontend: React with TypeScript and Material-UI
- 🔹 Backend: Spring Boot with PostgreSQL and pgvector and Ollama
- 🔹 Database: PostgreSQL / pgvector

## 👥 Team
- **Kamalesh Kumar Barik**
- **Prarthana Maharana**