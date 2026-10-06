# AI Prompt for Generating the Complete Project Report Word Document

Copy and paste the prompt below into any AI assistant (like ChatGPT, Claude, etc.) to generate your complete, beautifully formatted project report:

---

```text
Act as an expert technical writer and senior Android developer. I need you to write a comprehensive, professional academic and technical project report (suitable for a university course like IS229) for an Android e-commerce mobile application called "Theodist Super Store".

Here is the exact technical specification and code overview of the application:

1. PROJECT OVERVIEW & PURPOSE:
- "Theodist Super Store" is a native Android e-commerce mobile application built in Java using Android Studio and XML layouts. It simulates a fully functioning retail and office supply store with secure user authentication, database persistence, category-based product browsing, real-time search filtering, dynamic shopping cart management, and a streamlined prototype checkout system.

2. DETAILED CODEBASE DIRECTORY & COMPONENT WALKTHROUGH (What each part of the code does):
- Database & Security:
  * `UserDatabase.java`: An SQLiteOpenHelper class that manages the local SQLite database (`theodist_users.db`) and `users` table. It implements cryptographic password security using SHA-256 combined with a 16-byte random salt generated via SecureRandom to protect user credentials against rainbow table attacks.
  * `Customer.java`: The data model representing a registered user, holding their unique ID, full name, username, email, phone number, and delivery location.
  * `CustomerStore.java`: Manages the application's active runtime session (`getCurrent()`, `logout()`) and interfaces directly with `UserDatabase` for user registration and authentication.
- Activities (UI Controllers):
  * `LoginActivity.java`: Handles user sign-in. Supports authentication via username or email, includes a show/hide password toggle, input validation, progress indicators, "Remember Me" session persistence (using SharedPreferences to auto-login returning users), and links to the registration page.
  * `SignUpActivity.java`: Handles new user registration. Validates all inputs (unique username/email checks, password complexity with a live strength meter) and securely registers the user into the SQLite database.
  * `MainActivity.java`: The primary home screen (Dashboard). Greets the user by their registered full name, displays a promotion banner backed by a custom image (`images.png`), manages horizontal category filtering (Stationary, Technology, Toys, Office Furniture), drives real-time search filtering across 20 products (5 per category), manages the bottom navigation bar (Explorer, Cart, Profile), and displays an interactive User Profile & Logout dialog.
  * `ProductListActivity.java`: Displays filtered product lists by category with add-to-cart functionality.
  * `ProductDetailActivity.java`: Displays detailed product information, categories, pricing, and add-to-cart actions.
  * `CartActivity.java`: Manages the shopping cart, allowing users to increase/decrease item quantities (+/- operators), remove items, view live subtotals, and proceed to checkout.
  * `CheckoutActivity.java`: Collects customer delivery information (pre-filled from the logged-in user profile) and displays a prototype order confirmation dialog.
- Adapters & UI Helpers:
  * `PopularAdapter.java`: RecyclerView adapter that binds popular product cards (handling images, titles, prices, ratings, and add-to-cart actions).
  * `PopularDomain.java`: Data model for popular products displayed on the home screen grid.
  * `WrapContentRecyclerView.java`: Custom RecyclerView override that measures all grid items correctly inside ScrollViews to prevent grid clipping.

3. TECH STACK & DESIGN SYSTEM:
- Language: Java, Min SDK 24.
- UI Theme: "Frosted Purple Glass" design system utilizing custom XML shape drawables (`bg_gradient_screen.xml`, `bg_glass_card.xml`, `bg_input.xml`, `bg_button_primary.xml`) with gradients (#605FB4 to #6A67C0), translucent fills, and high-contrast text typography (white, mint, lavender).

Please generate a well-structured academic project report with the following sections, written in formal, professional technical English:
1. Title Page / Document Header
2. Introduction & Project Objectives
3. System Architecture & Technical Stack
4. Detailed Codebase Component Breakdown (explaining what each class and file does)
5. Database Design & Security Implementation (explaining SHA-256 + salt hashing)
6. UI/UX Design System & Implementation ("Frosted Purple Glass" theme)
7. Testing, Verification & Edge-Case Handling
8. Conclusion & Future Scope

Format the output clearly using Markdown headings, bullet points, and code snippet placeholders where appropriate so it can be easily exported into a Word document (.docx).
```
---
