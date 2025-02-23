# Restaurant Management System

### Overview
DishFlow is designed to help restaurants manage orders and inventory efficiently, especially during peak hours. By automating key processes, the system simplifies staff tasks, improves order accuracy, and enhances the overall customer experience.

---
### **Routes**

**Endpoint:**  
`POST /api/login`

**Request Headers:**
```http
Content-Type: application/json
```

**Request Body (JSON):**
```json
{
  "email": "test@gmail.com",
  "password": "12345"
}
```

**Response (Success - 200 Ok):**

```json
{
  "accessToken": "something",
  "refreshToken": null
}
```

**Response (Error - 400 Bad Request):**
```json
{
  "error": "<error message>"
}
```

