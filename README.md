# Theodist Super Store

A Java-based Android shopping application built for browsing a product catalog, creating an account, searching and filtering products, managing a cart, and completing a checkout flow.

## Overview

This project is a mobile storefront prototype for an online shopping experience. It includes user authentication, product discovery, category filtering, cart management, profile access, and a prototype checkout flow. The app stores local customer data using SQLite and uses Android UI components, Java model classes, and Gradle-based project configuration.

## Features

- User registration and login
- "Remember me" session support
- Product browsing with a grid-based storefront
- Search by product name
- Category filtering for Stationary, Technology, Toys, and Office Furniture
- Product detail view with pricing and item actions
- Cart management with quantity adjustments and subtotal updates
- Checkout form that pre-fills user information
- Profile dialog showing logged-in customer details
- Local SQLite-based customer database
- Demo product catalog with product images stored in `res/drawable`
- Responsive Android Material UI styling

## Tech Stack

- Android Studio
- Java
- XML layouts
- SQLite (local user database)
- Gradle Kotlin DSL
- AndroidX / Material Components
- Android SDK 24+

## App Flow

1. User opens the app and lands on the Login screen.
2. New users can sign up with validation and database checks.
3. After login, the main storefront loads with category chips and search.
4. Users can browse products, view details, and add items to the cart.
5. Cart items can be increased, decreased, or removed.
6. The checkout screen confirms the order and clears the cart after confirmation.
7. User profile details are displayed from the current logged-in customer.

## Core Java Classes

### Authentication and User Data
- `LoginActivity.java` - login form, remember-me logic, password visibility toggle
- `SignUpActivity.java` - registration validation, password strength, uniqueness checks
- `Customer.java` - customer model
- `CustomerStore.java` - session handling and current user management
- `UserDatabase.java` - SQLite database for customer records and login checks

### Storefront and Catalog
- `MainActivity.java` - home screen, category filtering, product search, cart badge
- `ProductListActivity.java` - product list screen by category
- `ProductDetailActivity.java` - selected product details and cart actions
- `ProductCatalog.java` - product catalog setup and category lookup
- `Product.java` - base product model
- `Stationary.java` - stationary product subtype
- `Technology.java` - technology product subtype
- `Toys.java` - toy product subtype
- `OfficeFurniture.java` - office furniture subtype
- `PopularDomain.java` - home-screen product data model
- `PopularAdapter.java` - storefront recycler adapter
- `WrapContentRecyclerView.java` - recycler sizing helper

### Cart and Checkout
- `Cart.java` - cart data container
- `CartItem.java` - item quantity and subtotal logic
- `CartManager.java` - shared cart instance for app-wide access
- `CartActivity.java` - cart screen and item controls
- `CheckoutActivity.java` - order form and prototype confirmation flow
- `Order.java` - order summary model

## Project Structure

```text
IS223-online-shopping-app-project/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/theodistonline_shoppingapp/
│   │   │   │   ├── Cart.java
│   │   │   │   ├── CartActivity.java
│   │   │   │   ├── CartItem.java
│   │   │   │   ├── CartManager.java
│   │   │   │   ├── CheckoutActivity.java
│   │   │   │   ├── Customer.java
│   │   │   │   ├── CustomerStore.java
│   │   │   │   ├── LoginActivity.java
│   │   │   │   ├── MainActivity.java
│   │   │   │   ├── OfficeFurniture.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── PopularAdapter.java
│   │   │   │   ├── PopularDomain.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── ProductCatalog.java
│   │   │   │   ├── ProductDetailActivity.java
│   │   │   │   ├── ProductList.java
│   │   │   │   ├── ProductListActivity.java
│   │   │   │   ├── SignUpActivity.java
│   │   │   │   ├── Stationary.java
│   │   │   │   ├── Technology.java
│   │   │   │   ├── Toys.java
│   │   │   │   ├── UserDatabase.java
│   │   │   │   └── WrapContentRecyclerView.java
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   ├── layout/
│   │   │   │   ├── menu/
│   │   │   │   ├── mipmap*/
│   │   │   │   ├── values/
│   │   │   │   └── xml/
│   │   │   └── AndroidManifest.xml
│   │   └── androidTest/
│   ├── build.gradle.kts
│   └── .gitignore
├── .gitignore
├── README.md
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
└── .idea/
```

## Product Categories Included

The catalog contains products in these categories:

- Stationary
- Technology
- Toys
- Office Furniture

Each category contains demo products with real local image resources for the storefront.

## Requirements

Before running the project, make sure you have:

- Android Studio
- JDK 11 or newer
- Android SDK with API 24+
- An Android emulator or physical Android device

## Setup Instructions

1. Clone the repository:

```bash
git clone https://github.com/Tommiieblvck/IS223-online-shopping-app-project.git
```

2. Open the project in Android Studio.

3. Allow Gradle to sync the project.

4. Connect an emulator or Android device.

5. Run the app from the Android Studio toolbar or using the terminal.

## Run the App

```bash
./gradlew assembleDebug
```

Then install the APK on a connected device or emulator.

## Notes

- This app is a prototype e-commerce application for learning Android UI, Java OOP patterns, and local persistence.
- Customer registration and login are local-only and stored in SQLite.
- Checkout is a prototype flow and does not process real payments.
- Product data is demo data included in the app and mapped to files in `app/src/main/res/drawable`.

## Future Improvements

Possible improvements include:

- Firebase or backend authentication
- Real database integration
- Secure payment processing
- Search and sorting enhancements
- Product admin dashboard
- Wishlist and favorites management
- Cloud image loading and product APIs

## License

This project is provided for educational and student project use.

## Repository

https://github.com/Tommiieblvck/IS223-online-shopping-app-project
