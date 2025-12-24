# Global Talent Mobility AI 🌍 ✈️

An innovative hybrid-cloud solution designed to help tech professionals navigate international relocation using AI-powered job scraping and visa sponsorship analysis.

## 🚀 Overview
Relocating to a new country is hard. This project automates the search for jobs that specifically offer **visa sponsorship** and relocation support.

### Key Features:
- **AI-Powered Analysis**: Uses local LLMs (via Ollama) to analyze job descriptions for sponsorship nuances.
- **Microservices Architecture**: A high-performance Java backend communicating with a specialized Python AI engine via gRPC.
- **Real-time Scraping**: Asynchronous Python scrapers that gather data without blocking.

## 🛠️ Technology Stack
- **Backend (Java)**: Spring Boot 3.4, gRPC Java, JUnit 5.
- **AI Engine (Python)**: FastAPI, gRPC Python, BeautifulSoup.
- **Communication**: gRPC / Protocol Buffers (Binary serialization).
- **AI Model**: Ollama (Llama 3).
- **Database**: H2 (In-memory).

## 🏗️ Architecture
The project follows a "Contract-First" design using Protobuf to ensure strict typing between the Java and Python services.

## 📝 Learning Journey
This project was built to demonstrate:
1. Senior-level Java architecture and testing.
2. Cross-language communication with gRPC.
3. Integration of local AI models into standard workflows.
4. Asynchronous programming in both Java and Python.

---
Built with ❤️ for Global Talent Mobility.
