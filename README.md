# 🔐 Spring Security - Custom Token-Based Authentication Demo

This is a simple Spring Boot project demonstrating how to secure REST APIs using **Spring Security** with a **custom filter**, instead of the default form-based login.

It includes:
- Manual token-based authentication using request headers
- Custom authorization logic using roles
- Role-based endpoint access using `@PreAuthorize`

---

## 📦 Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- Maven

---

## 🚀 Features

✅ Custom authentication filter (no login page)  
✅ Token & role-based header validation  
✅ Role-based authorization (`@PreAuthorize`)  
✅ Public vs protected endpoints  
✅ Configurable token via `application.properties`

---

## 🧠 How It Works

- All requests (except `/api/v1/public/**`) go through a **custom filter**.
- The filter checks two headers:
  - `Authorization`: must match a configured token
  - `X-ROLE`: must be `"ADMIN"` (for this example)
- If the token and role are valid:
  - A Spring `UsernamePasswordAuthenticationToken` is created manually.
  - It is stored in the `SecurityContextHolder`.
  - The request is allowed to continue.
- Otherwise, the request is rejected with a `401 Unauthorized`.

---

## 📁 API Endpoints

### 🔓 Public (No Auth Required)
GET /api/v1/public/hello

### 🔐 Secured (Token & Role Required)
POST /api/v1/secured
Headers:
Authorization: token
X-ROLE: ADMIN

## ⚙️ Configuration

Set your token in `src/main/resources/application.properties`:

```properties
spring.security.token=token
This token will be required in the Authorization header of all secured requests.

📥 Sample Request (Postman)
POST http://localhost:8080/api/v1/secured

Headers:

Authorization: token
X-ROLE: ADMIN
Response:

secured details accessed!
🔐 Custom Filter Logic

if (authToken != null && authToken.equals(token) && role.equalsIgnoreCase("ADMIN")) {
    // Authenticated
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        "customUser", null,
        List.of(new SimpleGrantedAuthority("ROLE_" + role))
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
} else {
    // Unauthorized
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write("role mismatched or unauthorized User");
}
📚 Learning Goals
Understand how Spring Security filters work

Implement OncePerRequestFilter to handle custom auth

Set authentication in SecurityContextHolder

Use role-based access control with @PreAuthorize

🛠️ Future Improvements
Replace hardcoded token with JWT

Add user role support (e.g., USER, ADMIN)

Externalize user details (DB, LDAP, etc.)

Global exception handling

📄 License
MIT — feel free to use and adapt!

💬 Author
Developed for learning and demo purposes. Reach out if you’d like a version with JWT or OAuth2 support!
