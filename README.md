# 🏦 DivClarity UI Data Service

[![Java](https://img.shields.io/badge/Java-13-blue.svg)](https://www.oracle.com/java/technologies/javase-jdk13-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.2.2-green.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6+-orange.svg)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE.md)

## 📊 Project Tagline

The main purpose of this code was to make it significanlty easier to display Stock Information pullled from the services provided b
the DCDataSvc repository. The data needs to be in specific formats display Tabular and Graph data when using specific a JavaScript UI
components. I decided to do the data formatting in the this code rather than on the client side in JavaScript.

## 📑 Table of Contents

- [💡 About The Project](#-about-the-project)
- [✨ Key Features](#-key-features)
- [💻 Technologies Used](#-technologies-used)
- [🛠️ Installation & Setup](#-installation--setup)
- [▶️ Running Locally](#-running-locally)
- [🧪 Testing](#-testing)
- [📌 Project Status](#-project-status)
- [🗺️ Future Roadmap](#-future-roadmap)
- [🤝 Contributing](#-contributing)
- [👤 Contact & Credit](#-contact--credit)
- [⚖️ License](#-license)

## 💡 About The Project

### Motivation
DivClarity UI Data Service was built to solve the critical need for reliable, scalable financial data aggregation and transformation. I have always
been very interested in dividend stocks and wanted to work on a platform that can display data that would later be used for analysis.

### Problem Solved
This microservice addresses the complexity of financial data integration by providing a unified REST API that aggregates stock information, dividend data, financial statements (balance sheets, income statements, cash flow), and sector analysis into standardized, consumable formats for frontend applications.

### Key Learnings
- **Enterprise Architecture**: Implemented microservices architecture with Spring Boot, demonstrating scalable backend development
- **RESTful API Design**: Built comprehensive REST endpoints following industry best practices for financial data services
- **Data Transformation**: Mastered complex JSON data mapping and financial data normalization techniques
- **Docker Containerization**: Achieved production-ready deployment with Docker containerization for cloud scalability
- **Error Handling**: Implemented robust error handling and validation for financial data processing

## ✨ Key Features

- **📈 Stock Data APIs**: Real-time stock price series and dividend series endpoints
- **💰 Financial Statement Analysis**: Comprehensive balance sheet, income statement, and cash flow data
- **📊 Interactive Graph Data**: Structured financial graph data for visualization components
- **🏭 Sector Analysis**: Sector-based stock filtering and analysis capabilities
- **🔍 Stock Suggestions**: Intelligent stock search and suggestion endpoints
- **📅 Dividend Tracking**: Historical dividend data with customizable date ranges
- **🛡️ CORS Support**: Cross-origin resource sharing for frontend integration
- **🔐 Security Headers**: Correlation ID tracking for request tracing and debugging
- **⚡ High Performance**: Optimized data processing with connection pooling and timeout management

## 💻 Technologies Used

### Backend & Framework
- **Java 13** - Modern Java features for enterprise development
- **Spring Boot 2.2.2** - Rapid application development framework
- **Spring Web** - RESTful web service development
- **Maven** - Dependency management and build automation

### Data Processing
- **Jackson** - JSON processing and data binding
- **Apache Commons IO** - Utility libraries for I/O operations
- **BigDecimal** - Precision financial calculations

### Development & DevOps
- **Docker** - Containerization for deployment
- **JUnit 5** - Unit testing framework
- **Spring Boot Test** - Integration testing support
- **Git** - Version control system

### Architecture Patterns
- **Microservices Architecture** - Scalable service-oriented design
- **RESTful API Design** - Industry-standard web service patterns
- **Template Method Pattern** - Flexible financial data templates
- **Builder Pattern** - Structured entity construction

## 🛠️ Installation & Setup

### Prerequisites
- **Java 13** or higher
- **Maven 3.6+**
- **Docker** (optional, for containerized deployment)
- **Git**

### Step-by-Step Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/dcuisvc.git
cd dcuisvc
```

2. **Set up environment variables**
```bash
export DCUI_DATAHOST="your-data-host"
export DCUI_DATAPORT="your-data-port"
export DCUI_READTIMEOUT="5000"
export DCUI_CONNECTTIMEOUT="5000"
```

3. **Build the project**
```bash
mvn clean install
```

4. **Run tests**
```bash
mvn test
```

## ▶️ Running Locally

### Option 1: Direct Java Execution
```bash
java -jar target/dcuisvc-1.0.jar
```
The service will start on **port 8000** by default.

### Option 2: Maven Spring Boot Plugin
```bash
mvn spring-boot:run
```

### Option 3: Docker Deployment
```bash
# Build Docker image
docker build -f docker/Dockerfile -t dc/dcuisvc .

# Run container
docker run --name dcuisvc -d -p 8000:8000 dc/dcuisvc
```

### API Endpoints
Once running, access the following endpoints:
- **Stock Suggestions**: `GET http://localhost:8000/divclarity/v1/uidata/stock/suggest`
- **Dividend Data**: `GET http://localhost:8000/divclarity/v1/uidata/{symbol}/divtable/range/{range}`
- **Financial Statements**: `GET http://localhost:8000/divclarity/v1/uidata/{symbol}/balancesheet/{type}`
- **Sector Analysis**: `GET http://localhost:8000/divclarity/v1/uidata/sectable/{sector}`

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

### Test Coverage
The project includes comprehensive unit tests covering:
- Application context loading
- REST endpoint functionality
- Data transformation logic
- Error handling scenarios

### Integration Testing
```bash
mvn verify
```

## 📌 Project Status

**🟢 Active Development** - Version 1.0 Complete

The project is currently in production-ready status with ongoing feature enhancements and performance optimizations.

## 👤 Contact & Credit

**Duane Grey** - Software Engineer & Financial Technology Specialist