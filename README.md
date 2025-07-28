# Firefly Platform - Distributor Management Service

A microservice for managing distributors who resell financial products such as lending services within the Firefly Platform ecosystem.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Data Model](#data-model)
- [Setup and Installation](#setup-and-installation)
- [Quick Start Guide](#quick-start-guide)
- [API Documentation](#api-documentation)
  - [Usage Examples](#usage-examples)
- [Development Guidelines](#development-guidelines)
- [License](#license)

## Overview

The Distributor Management Service is a core component of the Firefly Platform, responsible for managing distributors who resell financial products. This service provides comprehensive functionality for creating, updating, and managing distributors, their branding configurations, and audit logs.

Key features include:
- Distributor lifecycle management (creation, updates, termination)
- Customizable branding configurations for distributors
- Comprehensive audit logging for all distributor-related actions
- Product management for distributor offerings
- Product categorization and organization
- Lending configuration management with various lending types
- Leasing contract creation and management
- Shipment tracking for leased products
- RESTful API for integration with other services

## Architecture

The service follows a modular architecture with clear separation of concerns:

```
common-platform-distributor-mgmt/
├── common-platform-distributor-mgmt-core/       # Business logic and service implementations
├── common-platform-distributor-mgmt-interfaces/ # DTOs and public interfaces
├── common-platform-distributor-mgmt-models/     # Data entities and repositories
├── common-platform-distributor-mgmt-sdk/        # Client SDK for other services
└── common-platform-distributor-mgmt-web/        # REST API controllers and web configuration
```

The service is built using:
- Spring Boot for application framework
- Spring WebFlux for reactive programming
- Spring Data R2DBC for reactive database access
- PostgreSQL for data storage
- Flyway for database migrations

## Data Model

The service manages the following core entities:

```mermaid
erDiagram
    Distributor ||--o{ DistributorBranding : "has"
    Distributor ||--o{ DistributorAuditLog : "has"
    Distributor ||--o{ Product : "offers"
    Distributor ||--o{ LeasingContract : "manages"
    
    Product ||--o{ LendingConfiguration : "has"
    Product }|--|| ProductCategory : "belongs to"
    Product ||--o{ LeasingContract : "included in"
    Product ||--o{ Shipment : "shipped as"
    
    LendingConfiguration }|--|| LendingType : "has type"
    LendingConfiguration ||--o{ LeasingContract : "used in"
    
    LeasingContract ||--o{ Shipment : "has"

    Distributor {
        Long id PK
        String externalCode
        String name
        String displayName
        String taxId
        String registrationNumber
        String websiteUrl
        String phoneNumber
        String email
        String supportEmail
        String addressLine
        String postalCode
        String city
        String state
        String countryCode
        Boolean isActive
        Boolean isTestDistributor
        String timeZone
        String defaultLocale
        LocalDateTime onboardedAt
        LocalDateTime terminatedAt
        LocalDateTime createdAt
        Long createdBy
        LocalDateTime updatedAt
        Long updatedBy
    }

    DistributorBranding {
        Long id PK
        Long distributorId FK
        String logoUrl
        String faviconUrl
        String primaryColor
        String secondaryColor
        String backgroundColor
        String fontFamily
        ThemeEnum theme
        Boolean isDefault
        LocalDateTime createdAt
        Long createdBy
        LocalDateTime updatedAt
        Long updatedBy
    }

    DistributorAuditLog {
        Long id PK
        Long distributorId FK
        DistributorActionEnum action
        String entity
        String entityId
        JsonNode metadata
        String ipAddress
        Long userId
        LocalDateTime timestamp
    }
    
    Product {
        Long id PK
        Long distributorId FK
        String name
        String description
        String sku
        String modelNumber
        String manufacturer
        ProductCategory category FK
        String imageUrl
        String specifications
        Boolean isActive
        LocalDateTime createdAt
        Long createdBy
        LocalDateTime updatedAt
        Long updatedBy
    }
    
    ProductCategory {
        Long id PK
        String name
        String code
        String description
        Boolean isActive
        LocalDateTime createdAt
        Long createdBy
        LocalDateTime updatedAt
        Long updatedBy
    }
    
    LendingConfiguration {
        Long id PK
        Long productId FK
        String name
        String description
        LendingType lendingType FK
        Integer minTermMonths
        Integer maxTermMonths
        Integer defaultTermMonths
        BigDecimal minDownPaymentPercentage
        BigDecimal defaultDownPaymentPercentage
        BigDecimal interestRate
        BigDecimal processingFeePercentage
        BigDecimal earlyTerminationFeePercentage
        BigDecimal latePaymentFeeAmount
        Integer gracePeriodDays
        Boolean isDefault
        Boolean isActive
        String termsConditions
        LocalDateTime createdAt
        Long createdBy
        LocalDateTime updatedAt
        Long updatedBy
    }
    
    LendingType {
        Long id PK
        String name
        String code
        String description
        Boolean isActive
        LocalDateTime createdAt
        Long createdBy
        LocalDateTime updatedAt
        Long updatedBy
    }
    
    LeasingContract {
        Long id PK
        Long contractId
        Long partyId
        Long distributorId FK
        Long productId FK
        Long lendingConfigurationId FK
        LocalDate startDate
        LocalDate endDate
        BigDecimal monthlyPayment
        BigDecimal downPayment
        BigDecimal totalAmount
        String status
        LocalDateTime approvalDate
        Long approvedBy
        String termsConditions
        String notes
        Boolean isActive
        LocalDateTime createdAt
        Long createdBy
        LocalDateTime updatedAt
        Long updatedBy
    }
    
    Shipment {
        Long id PK
        Long leasingContractId FK
        Long productId FK
        String trackingNumber
        String carrier
        String shippingAddress
        LocalDateTime shippingDate
        LocalDateTime estimatedDeliveryDate
        LocalDateTime actualDeliveryDate
        String status
        String notes
        LocalDateTime createdAt
        Long createdBy
        LocalDateTime updatedAt
        Long updatedBy
    }
```

### Enumerations

- **ThemeEnum**: Defines the theme options for distributor branding
  - `LIGHT`
  - `DARK`
  - `CUSTOM`

- **DistributorActionEnum**: Defines the types of actions recorded in audit logs
  - `CREATED`
  - `UPDATED`
  - `TERMINATED`

- **ProductCategoryEnum**: Defines the categories of products (later migrated to a separate table)
  - `MEDICAL_EQUIPMENT`: Equipment used in medical and healthcare settings
  - `VEHICLE`: Automobiles, trucks, and other vehicles
  - `CONSTRUCTION_EQUIPMENT`: Equipment used in construction and building
  - `TECHNOLOGY`: Computers, servers, and other technology products
  - `OFFICE_EQUIPMENT`: Equipment used in office settings
  - `INDUSTRIAL_MACHINERY`: Machinery used in industrial settings
  - `OTHER`: Other types of products not fitting into specific categories

- **LendingTypeEnum**: Defines the types of lending options (later migrated to a separate table)
  - `LEASE`: Traditional lease with fixed term
  - `RENT`: Short-term rental
  - `LEASE_TO_OWN`: Lease with option to purchase at end of term
  - `FINANCE`: Direct financing option
  - `SUBSCRIPTION`: Recurring payment model
  - `INSTALLMENT`: Payment in installments

## Setup and Installation

### Prerequisites

- JDK 17 or higher
- Maven 3.8 or higher
- PostgreSQL 13 or higher

### Building the Service

```bash
# Clone the repository
git clone https://github.com/firefly-platform/common-platform-distributor-mgmt.git
cd common-platform-distributor-mgmt

# Build the project
mvn clean install
```

### Configuration

The service can be configured through the `application.yaml` file located in the `common-platform-distributor-mgmt-web/src/main/resources` directory.

Key configuration properties:

```yaml
spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/distributor_mgmt
    username: postgres
    password: postgres
  flyway:
    url: jdbc:postgresql://localhost:5432/distributor_mgmt
    user: postgres
    password: postgres
```

### Running the Service

```bash
# Run the service
cd common-platform-distributor-mgmt-web
mvn spring-boot:run
```

## Quick Start Guide

This guide will help you get started with the Distributor Management Service quickly, covering the most common operations.

### Setting Up Your First Distributor

1. **Start the service** as described in the [Setup and Installation](#setup-and-installation) section.

2. **Create a new distributor** by sending a POST request to `/api/v1/distributors`:

```bash
curl -X POST http://localhost:8080/api/v1/distributors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Acme Financial Services",
    "displayName": "Acme Finance",
    "taxId": "123456789",
    "registrationNumber": "REG123456",
    "websiteUrl": "https://acmefinance.example.com",
    "phoneNumber": "+1-555-123-4567",
    "email": "contact@acmefinance.example.com",
    "supportEmail": "support@acmefinance.example.com",
    "addressLine": "123 Finance Street",
    "postalCode": "10001",
    "city": "New York",
    "state": "NY",
    "countryCode": "US",
    "isActive": true,
    "isTestDistributor": false,
    "timeZone": "America/New_York",
    "defaultLocale": "en-US"
  }'
```

3. **Set up branding** for your distributor by sending a POST request to `/api/v1/distributors/{distributorId}/brandings`:

```bash
curl -X POST http://localhost:8080/api/v1/distributors/1/brandings \
  -H "Content-Type: application/json" \
  -d '{
    "logoUrl": "https://acmefinance.example.com/logo.png",
    "faviconUrl": "https://acmefinance.example.com/favicon.ico",
    "primaryColor": "#4285F4",
    "secondaryColor": "#34A853",
    "backgroundColor": "#FFFFFF",
    "fontFamily": "Roboto, sans-serif",
    "theme": "LIGHT",
    "isDefault": true
  }'
```

### Common Workflows

#### Distributor Lifecycle Management

1. **Create a distributor** (as shown above)
2. **Update distributor details** when information changes:

```bash
curl -X PUT http://localhost:8080/api/v1/distributors/1 \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "name": "Acme Financial Services",
    "displayName": "Acme Finance",
    "phoneNumber": "+1-555-123-9999",  # Updated phone number
    "email": "new-contact@acmefinance.example.com",  # Updated email
    "isActive": true
    # Include other fields as needed
  }'
```

3. **Terminate a distributor** by updating its status and setting a termination date:

```bash
curl -X PUT http://localhost:8080/api/v1/distributors/1 \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "isActive": false,
    "terminatedAt": "2023-12-31T23:59:59"
    # Include other required fields
  }'
```

#### Branding Management

1. **Create branding** (as shown above)
2. **Update branding** when the distributor's visual identity changes:

```bash
curl -X PUT http://localhost:8080/api/v1/distributors/1/brandings/1 \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "distributorId": 1,
    "primaryColor": "#1976D2",  # Updated primary color
    "secondaryColor": "#388E3C",  # Updated secondary color
    "theme": "CUSTOM"  # Changed theme
    # Include other fields as needed
  }'
```

#### Audit Logging

1. **Create an audit log entry** when significant actions occur:

```bash
curl -X POST http://localhost:8080/api/v1/distributors/1/audit-logs \
  -H "Content-Type: application/json" \
  -d '{
    "distributorId": 1,
    "action": "UPDATED",
    "entity": "DISTRIBUTOR",
    "entityId": "1",
    "metadata": {
      "changedFields": ["phoneNumber", "email"],
      "reason": "Contact information update"
    },
    "userId": 1001
  }'
```

2. **Query audit logs** to review the history of changes:

```bash
curl -X POST http://localhost:8080/api/v1/distributors/1/audit-logs/filter \
  -H "Content-Type: application/json" \
  -d '{
    "filters": {
      "entity": "DISTRIBUTOR",
      "entityId": "1"
    },
    "pagination": {
      "pageNumber": 0,
      "pageSize": 10,
      "sortBy": "timestamp",
      "sortDirection": "DESC"
    }
  }'
```

#### Product Management

1. **Create a new product** for a distributor:

```bash
curl -X POST http://localhost:8080/api/v1/distributors/1/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Medical Scanner X2000",
    "description": "Advanced medical imaging scanner for diagnostic use",
    "sku": "MS-X2000",
    "modelNumber": "X2000-MED",
    "manufacturer": "MedTech Innovations",
    "category": "MEDICAL_EQUIPMENT",
    "imageUrl": "https://example.com/images/scanner-x2000.jpg",
    "specifications": {
      "dimensions": "60cm x 80cm x 150cm",
      "weight": "120kg",
      "powerRequirements": "220V, 10A",
      "certifications": ["FDA", "CE", "ISO13485"]
    },
    "isActive": true
  }'
```

2. **Update product details** when information changes:

```bash
curl -X PUT http://localhost:8080/api/v1/distributors/1/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "distributorId": 1,
    "name": "Medical Scanner X2000 Pro",
    "description": "Advanced medical imaging scanner for diagnostic use with enhanced resolution",
    "sku": "MS-X2000-PRO",
    "modelNumber": "X2000-MED-PRO",
    "manufacturer": "MedTech Innovations",
    "category": "MEDICAL_EQUIPMENT",
    "imageUrl": "https://example.com/images/scanner-x2000-pro.jpg",
    "specifications": {
      "dimensions": "60cm x 80cm x 150cm",
      "weight": "115kg",
      "powerRequirements": "220V, 8A",
      "certifications": ["FDA", "CE", "ISO13485", "AAMI"]
    },
    "isActive": true
  }'
```

3. **Get all products** for a distributor:

```bash
curl -X GET http://localhost:8080/api/v1/distributors/1/products
```

4. **Get products by category**:

```bash
curl -X GET http://localhost:8080/api/v1/distributors/1/products/category/MEDICAL_EQUIPMENT
```

5. **Filter products** based on criteria:

```bash
curl -X POST http://localhost:8080/api/v1/distributors/1/products/filter \
  -H "Content-Type: application/json" \
  -d '{
    "filters": {
      "category": "MEDICAL_EQUIPMENT",
      "isActive": true
    },
    "pagination": {
      "pageNumber": 0,
      "pageSize": 10,
      "sortBy": "name",
      "sortDirection": "ASC"
    }
  }'
```

#### Lending Configuration Management

1. **Create a new lending configuration** for a product:

```bash
curl -X POST http://localhost:8080/api/v1/distributors/1/products/1/lending-configurations \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Standard Lease",
    "description": "Standard leasing terms for medical equipment",
    "lendingType": "LEASE",
    "minTermMonths": 12,
    "maxTermMonths": 60,
    "defaultTermMonths": 36,
    "minDownPaymentPercentage": 10.00,
    "defaultDownPaymentPercentage": 15.00,
    "interestRate": 5.75,
    "processingFeePercentage": 1.50,
    "earlyTerminationFeePercentage": 3.00,
    "latePaymentFeeAmount": 50.00,
    "gracePeriodDays": 10,
    "isDefault": true,
    "isActive": true,
    "termsConditions": "Standard terms and conditions apply. See contract for details."
  }'
```

2. **Update a lending configuration**:

```bash
curl -X PUT http://localhost:8080/api/v1/distributors/1/products/1/lending-configurations/1 \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "productId": 1,
    "name": "Premium Lease",
    "description": "Premium leasing terms with lower interest rate",
    "lendingType": "LEASE",
    "minTermMonths": 12,
    "maxTermMonths": 60,
    "defaultTermMonths": 36,
    "minDownPaymentPercentage": 15.00,
    "defaultDownPaymentPercentage": 20.00,
    "interestRate": 4.75,
    "processingFeePercentage": 1.00,
    "earlyTerminationFeePercentage": 2.50,
    "latePaymentFeeAmount": 45.00,
    "gracePeriodDays": 15,
    "isDefault": true,
    "isActive": true,
    "termsConditions": "Premium terms and conditions apply. See contract for details."
  }'
```

3. **Get all lending configurations** for a product:

```bash
curl -X GET http://localhost:8080/api/v1/distributors/1/products/1/lending-configurations
```

4. **Get the default lending configuration** for a product:

```bash
curl -X GET http://localhost:8080/api/v1/distributors/1/products/1/lending-configurations/default
```

5. **Get lending configurations by type**:

```bash
curl -X GET http://localhost:8080/api/v1/distributors/1/products/1/lending-configurations/type/LEASE
```

6. **Get all lending configurations** for a distributor:

```bash
curl -X GET http://localhost:8080/api/v1/distributors/1/lending-configurations
```

```bash
curl -X POST http://localhost:8080/api/v1/distributors/1/audit-logs/filter \
  -H "Content-Type: application/json" \
  -d '{
    "filters": {
      "action": "UPDATED"
    },
    "page": 0,
    "size": 10,
    "sort": [
      {
        "field": "timestamp",
        "direction": "DESC"
      }
    ]
  }'
```

## API Documentation

The service provides a RESTful API with the following main endpoints:

### Distributor Endpoints

- `POST /api/v1/distributors` - Create a new distributor
- `GET /api/v1/distributors/{distributorId}` - Get distributor by ID
- `PUT /api/v1/distributors/{distributorId}` - Update distributor
- `DELETE /api/v1/distributors/{distributorId}` - Delete distributor
- `POST /api/v1/distributors/filter` - Filter distributors

### Distributor Branding Endpoints

- `POST /api/v1/distributors/{distributorId}/brandings` - Create a new branding configuration
- `GET /api/v1/distributors/{distributorId}/brandings/{brandingId}` - Get branding by ID
- `PUT /api/v1/distributors/{distributorId}/brandings/{brandingId}` - Update branding
- `DELETE /api/v1/distributors/{distributorId}/brandings/{brandingId}` - Delete branding
- `POST /api/v1/distributors/{distributorId}/brandings/filter` - Filter branding configurations

### Distributor Audit Log Endpoints

- `POST /api/v1/distributors/{distributorId}/audit-logs` - Create a new audit log entry
- `GET /api/v1/distributors/{distributorId}/audit-logs/{auditLogId}` - Get audit log by ID
- `PUT /api/v1/distributors/{distributorId}/audit-logs/{auditLogId}` - Update audit log
- `DELETE /api/v1/distributors/{distributorId}/audit-logs/{auditLogId}` - Delete audit log
- `POST /api/v1/distributors/{distributorId}/audit-logs/filter` - Filter audit logs

### Product Endpoints

- `POST /api/v1/distributors/{distributorId}/products` - Create a new product
- `GET /api/v1/distributors/{distributorId}/products/{productId}` - Get product by ID
- `PUT /api/v1/distributors/{distributorId}/products/{productId}` - Update product
- `DELETE /api/v1/distributors/{distributorId}/products/{productId}` - Delete product
- `GET /api/v1/distributors/{distributorId}/products` - Get all products for a distributor
- `GET /api/v1/distributors/{distributorId}/products/active` - Get all active products for a distributor
- `GET /api/v1/distributors/{distributorId}/products/category/{categoryId}` - Get products by category
- `POST /api/v1/distributors/{distributorId}/products/filter` - Filter products

### Lending Configuration Endpoints

- `POST /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations` - Create a new lending configuration
- `GET /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/{configId}` - Get lending configuration by ID
- `PUT /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/{configId}` - Update lending configuration
- `DELETE /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/{configId}` - Delete lending configuration
- `GET /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations` - Get all lending configurations for a product
- `GET /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/active` - Get active lending configurations
- `GET /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/type/{lendingTypeId}` - Get configurations by type
- `GET /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/default` - Get default configuration
- `POST /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/{configId}/create-contract` - Create contract from configuration
- `POST /api/v1/distributors/{distributorId}/products/{productId}/lending-configurations/filter` - Filter lending configurations
- `GET /api/v1/distributors/{distributorId}/lending-configurations` - Get all lending configurations for a distributor

### Leasing Contract Endpoints

- `POST /api/v1/leasing-contracts` - Create a new leasing contract
- `GET /api/v1/leasing-contracts/{id}` - Get leasing contract by ID
- `PUT /api/v1/leasing-contracts/{id}` - Update leasing contract
- `DELETE /api/v1/leasing-contracts/{id}` - Delete leasing contract
- `GET /api/v1/leasing-contracts/contract/{contractId}` - Get leasing contract by contract ID
- `GET /api/v1/leasing-contracts/distributor/{distributorId}` - Get contracts for a distributor
- `GET /api/v1/leasing-contracts/product/{productId}` - Get contracts for a product
- `GET /api/v1/leasing-contracts/party/{partyId}` - Get contracts for a party
- `GET /api/v1/leasing-contracts/status/{status}` - Get contracts by status
- `POST /api/v1/leasing-contracts/{id}/approve` - Approve a leasing contract
- `POST /api/v1/leasing-contracts/filter` - Filter leasing contracts

### Shipment Endpoints

- `POST /api/v1/shipments` - Create a new shipment
- `GET /api/v1/shipments/{id}` - Get shipment by ID
- `PUT /api/v1/shipments/{id}` - Update shipment
- `DELETE /api/v1/shipments/{id}` - Delete shipment
- `GET /api/v1/shipments/tracking/{trackingNumber}` - Get shipment by tracking number
- `GET /api/v1/shipments/leasing-contract/{leasingContractId}` - Get shipments for a leasing contract
- `GET /api/v1/shipments/product/{productId}` - Get shipments for a product
- `GET /api/v1/shipments/status/{status}` - Get shipments by status
- `PUT /api/v1/shipments/{id}/status` - Update shipment status
- `POST /api/v1/shipments/filter` - Filter shipments

Detailed API documentation is available through Swagger UI at `/swagger-ui.html` when the service is running.

### Usage Examples

Below are detailed examples of how to use the API for common scenarios, including request and response payloads.

#### Creating and Managing Distributors

**Example 1: Creating a new distributor**

Request:
```http
POST /api/v1/distributors
Content-Type: application/json

{
  "name": "Global Finance Partners",
  "displayName": "GFP",
  "taxId": "987654321",
  "registrationNumber": "GFP2023-456",
  "websiteUrl": "https://globalfinancepartners.example.com",
  "phoneNumber": "+1-555-987-6543",
  "email": "info@gfp.example.com",
  "supportEmail": "support@gfp.example.com",
  "addressLine": "789 Global Avenue",
  "postalCode": "94105",
  "city": "San Francisco",
  "state": "CA",
  "countryCode": "US",
  "isActive": true,
  "isTestDistributor": false,
  "timeZone": "America/Los_Angeles",
  "defaultLocale": "en-US"
}
```

Response:
```json
{
  "id": 2,
  "name": "Global Finance Partners",
  "displayName": "GFP",
  "taxId": "987654321",
  "registrationNumber": "GFP2023-456",
  "websiteUrl": "https://globalfinancepartners.example.com",
  "phoneNumber": "+1-555-987-6543",
  "email": "info@gfp.example.com",
  "supportEmail": "support@gfp.example.com",
  "addressLine": "789 Global Avenue",
  "postalCode": "94105",
  "city": "San Francisco",
  "state": "CA",
  "countryCode": "US",
  "isActive": true,
  "isTestDistributor": false,
  "timeZone": "America/Los_Angeles",
  "defaultLocale": "en-US",
  "createdAt": "2023-09-15T14:30:45.123Z",
  "createdBy": 1001,
  "updatedAt": null,
  "updatedBy": null,
  "onboardedAt": "2023-09-15T14:30:45.123Z",
  "terminatedAt": null
}
```

**Example 2: Filtering distributors by criteria**

Request:
```http
POST /api/v1/distributors/filter
Content-Type: application/json

{
  "filters": {
    "isActive": true,
    "countryCode": "US"
  },
  "page": 0,
  "size": 10,
  "sort": [
    {
      "field": "name",
      "direction": "ASC"
    }
  ]
}
```

Response:
```json
{
  "content": [
    {
      "id": 1,
      "name": "Acme Financial Services",
      "displayName": "Acme Finance",
      "countryCode": "US",
      "isActive": true,
      "createdAt": "2023-09-10T09:15:30.456Z",
      "...": "other fields omitted for brevity"
    },
    {
      "id": 2,
      "name": "Global Finance Partners",
      "displayName": "GFP",
      "countryCode": "US",
      "isActive": true,
      "createdAt": "2023-09-15T14:30:45.123Z",
      "...": "other fields omitted for brevity"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": [
      {
        "property": "name",
        "direction": "ASC"
      }
    ]
  },
  "totalElements": 2,
  "totalPages": 1,
  "last": true,
  "first": true,
  "size": 10,
  "number": 0,
  "numberOfElements": 2,
  "empty": false
}
```

#### Branding Configuration

**Example 1: Creating a dark theme branding**

Request:
```http
POST /api/v1/distributors/2/brandings
Content-Type: application/json

{
  "logoUrl": "https://globalfinancepartners.example.com/logo-white.png",
  "faviconUrl": "https://globalfinancepartners.example.com/favicon.ico",
  "primaryColor": "#2196F3",
  "secondaryColor": "#FFC107",
  "backgroundColor": "#121212",
  "fontFamily": "Montserrat, sans-serif",
  "theme": "DARK",
  "isDefault": true
}
```

Response:
```json
{
  "id": 2,
  "distributorId": 2,
  "logoUrl": "https://globalfinancepartners.example.com/logo-white.png",
  "faviconUrl": "https://globalfinancepartners.example.com/favicon.ico",
  "primaryColor": "#2196F3",
  "secondaryColor": "#FFC107",
  "backgroundColor": "#121212",
  "fontFamily": "Montserrat, sans-serif",
  "theme": "DARK",
  "isDefault": true,
  "createdAt": "2023-09-15T15:10:22.789Z",
  "createdBy": 1001,
  "updatedAt": null,
  "updatedBy": null
}
```

**Example 2: Getting all branding configurations for a distributor**

Request:
```http
POST /api/v1/distributors/2/brandings/filter
Content-Type: application/json

{
  "filters": {},
  "page": 0,
  "size": 10,
  "sort": [
    {
      "field": "createdAt",
      "direction": "DESC"
    }
  ]
}
```

Response:
```json
{
  "content": [
    {
      "id": 2,
      "distributorId": 2,
      "logoUrl": "https://globalfinancepartners.example.com/logo-white.png",
      "theme": "DARK",
      "isDefault": true,
      "createdAt": "2023-09-15T15:10:22.789Z",
      "...": "other fields omitted for brevity"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": [
      {
        "property": "createdAt",
        "direction": "DESC"
      }
    ]
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true,
  "size": 10,
  "number": 0,
  "numberOfElements": 1,
  "empty": false
}
```

#### Audit Logging

**Example 1: Recording a branding update**

Request:
```http
POST /api/v1/distributors/2/audit-logs
Content-Type: application/json

{
  "distributorId": 2,
  "action": "UPDATED",
  "entity": "DISTRIBUTOR_BRANDING",
  "entityId": "2",
  "metadata": {
    "changedFields": ["primaryColor", "secondaryColor"],
    "previousValues": {
      "primaryColor": "#2196F3",
      "secondaryColor": "#FFC107"
    },
    "newValues": {
      "primaryColor": "#1565C0",
      "secondaryColor": "#FFB300"
    },
    "reason": "Brand refresh"
  },
  "ipAddress": "192.168.1.100",
  "userId": 1001
}
```

Response:
```json
{
  "id": 3,
  "distributorId": 2,
  "action": "UPDATED",
  "entity": "DISTRIBUTOR_BRANDING",
  "entityId": "2",
  "metadata": {
    "changedFields": ["primaryColor", "secondaryColor"],
    "previousValues": {
      "primaryColor": "#2196F3",
      "secondaryColor": "#FFC107"
    },
    "newValues": {
      "primaryColor": "#1565C0",
      "secondaryColor": "#FFB300"
    },
    "reason": "Brand refresh"
  },
  "ipAddress": "192.168.1.100",
  "userId": 1001,
  "timestamp": "2023-09-15T16:45:33.456Z"
}
```

**Example 2: Querying audit logs for a specific action type**

Request:
```http
POST /api/v1/distributors/2/audit-logs/filter
Content-Type: application/json

{
  "filters": {
    "action": "UPDATED",
    "entity": "DISTRIBUTOR_BRANDING"
  },
  "page": 0,
  "size": 10,
  "sort": [
    {
      "field": "timestamp",
      "direction": "DESC"
    }
  ]
}
```

Response:
```json
{
  "content": [
    {
      "id": 3,
      "distributorId": 2,
      "action": "UPDATED",
      "entity": "DISTRIBUTOR_BRANDING",
      "entityId": "2",
      "metadata": {
        "changedFields": ["primaryColor", "secondaryColor"],
        "reason": "Brand refresh",
        "...": "other metadata omitted for brevity"
      },
      "userId": 1001,
      "timestamp": "2023-09-15T16:45:33.456Z",
      "...": "other fields omitted for brevity"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": [
      {
        "property": "timestamp",
        "direction": "DESC"
      }
    ]
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true,
  "size": 10,
  "number": 0,
  "numberOfElements": 1,
  "empty": false
}
```

## Development Guidelines

### Code Style

This project follows the Google Java Style Guide. Please ensure your code adheres to these guidelines before submitting pull requests.

### Testing

All new features and bug fixes should include appropriate unit and integration tests. Run tests using:

```bash
mvn test
```

### Branching Strategy

- `main` - Production-ready code
- `develop` - Integration branch for features
- `feature/*` - Feature branches
- `bugfix/*` - Bug fix branches
- `release/*` - Release preparation branches

### Commit Messages

Follow the conventional commits specification for commit messages:

```
<type>(<scope>): <description>

[optional body]

[optional footer(s)]
```

Types: feat, fix, docs, style, refactor, test, chore

## License

This project is licensed under the [Apache License 2.0](LICENSE).
