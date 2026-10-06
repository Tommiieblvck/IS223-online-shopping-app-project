# Theodist Super Store — Android Application Project Report

---

## 1. Executive Summary
**Theodist Super Store** is a fully functional, modern native Android e-commerce application developed in **Java** using **Android Studio** and **XML layouts**. Designed around a sleek **"Frosted Purple Glass"** aesthetic, the application provides an intuitive shopping experience featuring user authentication, encrypted local database storage, categorized product catalogs, real-time search filtering, dynamic shopping cart management, and a streamlined prototype checkout workflow.

---

## 2. Technical Stack & Architecture
* **Programming Language:** Java
* **Platform:** Android (Min SDK 24, Target SDK 34+)
* **UI Framework:** Android XML Layouts, Material Design 3 Components, CoordinatorLayout, RecyclerView (Grid & Custom WrapContent layouts)
* **Local Database:** SQLite via `SQLiteOpenHelper` (`UserDatabase.java`)
* **Session & State Persistence:** `SharedPreferences` for "Remember Me" session handling and `CartManager` for in-memory cart persistence.

---

## 3. Database Design & Security (`UserDatabase.java`)
To ensure enterprise-grade security and data persistence across app restarts, the application implements a local SQLite database with robust cryptographic protection:
* **Table Schema (`users`):** `id` (Primary Key), `full_name`, `username` (Unique), `email` (Unique), `phone`, `location`, `password_hash`, `salt`, `created_at`.
* **Password Hashing (SHA-256 + Random Salt):**
  * *Random Salt:* A cryptographically secure random 16-byte salt is generated via `SecureRandom` for every user upon registration. This ensures identical passwords yield completely unique hashes, neutralizing rainbow table attacks.
  * *Hashing:* Passwords combined with their unique salt are hashed using **SHA-256** prior to database insertion. Plain-text passwords are never stored or logged.

---

## 4. UI/UX Design System ("Frosted Purple Glass")
The application follows a cohesive, professional design system:
* **Color Palette:** Primary Purple (`#605FB4`), Gradient End (`#6A67C0`), Soft Lavender (`#B9B7F0`), Mint (`#7EE0B5`), Coral (`#FF6B81`), and Near-White (`#F4F3FF`).
* **Glass Morphism:** Cards and containers utilize translucent white fills (`#26FFFFFF`) paired with 1.5dp white borders (`#66FFFFFF`) over dynamic gradient backgrounds.
* **Banner & Imaging:** Custom product and promotion images (such as the featured promotion banner powered by `images.png`) feature curved edges and dark scrim overlays to guarantee 100% text readability.

---

## 5. Core Functional Modules

### A. Authentication & Session Management
* **Sign Up (`SignUpActivity`):** Validates full name, unique username, email format, phone digits, and password complexity (with a live strength meter).
* **Login (`LoginActivity`):** Supports authentication via **either username or email**, features a password visibility toggle (show/hide), and includes a **"Remember Me"** option that securely bypasses the login screen on subsequent launches.
* **Profile & Logout (`MainActivity`):** The profile bottom navigation tab presents user account details in an AlertDialog with an integrated Logout option.

### B. Product Catalog & Discovery (`MainActivity` & `ProductListActivity`)
* **Categories:** Four primary departments—**Stationary**, **Technology**, **Toys**, and **Office Furniture**—each stocked with 5 items (20 total products).
* **Real-Time Search:** Instant filtering of popular products based on search queries and selected categories.
* **Custom Grid Layout:** Powered by `WrapContentRecyclerView` to eliminate grid clipping inside ScrollViews.

### C. Shopping Cart & Checkout (`CartActivity` & `CheckoutActivity`)
* **Cart Management:** Live quantity adjustments (`+` and `-` operators), item removal, and subtotal calculations.
* **Checkout Integration:** Pre-fills customer details (name, phone, location) from the active user session to streamline the prototype checkout flow.

---

## 6. Testing & Quality Assurance
* **Build Verification:** Compiles successfully with zero build errors or unresolved symbol references.
* **Unit Testing:** Passes local JVM unit tests (`app:testDebugUnitTest`).
* **Edge Case Handling:** Handles empty database states, screen orientation changes, search queries with no results (*"No products found"*), and duplicate registration attempts.

---

## 7. Conclusion & Future Enhancements
The Theodist Super Store application successfully demonstrates mastery of native Android development concepts, database persistence, secure authentication, and modern UI design. Future enhancements may include backend REST API integration, online payment gateway integration, and wishlist synchronization.
