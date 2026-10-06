# IS223 Online Shopping App Project

A Java-based Android e-commerce application for browsing products, managing a cart, saving wishlist items, creating an account, and completing checkout.

## Overview

This project is an online shopping app designed for a mobile storefront experience. It includes user authentication, product browsing, product details, a shopping cart, wishlist functionality, and checkout flow. The app is built with Android Studio using Java and XML layout resources.

## Features

- User registration and login
- Product catalog browsing
- Product detail view
- Wishlist management
- Cart management
- Checkout process
- Customer and order data handling
- Android Material UI styling
- Local app data management using Java model classes and database helpers

## Tech Stack

- Android Studio
- Java
- XML layouts
- Gradle Kotlin DSL
- AndroidX / Material Components
- Android SDK 24+

## Project Structure

```text
IS223-online-shopping-app-project/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/theodistonline_shoppingapp/
│   │   │   │   ├── MainActivity.java
│   │   │   │   ├── LoginActivity.java
│   │   │   │   ├── SignUpActivity.java
│   │   │   │   ├── ProductListActivity.java
│   │   │   │   ├── ProductDetailActivity.java
│   │   │   │   ├── CartActivity.java
│   │   │   │   ├── WishlistActivity.java
│   │   │   │   ├── CheckoutActivity.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── Customer.java
│   │   │   │   ├── ProductCatalog.java
│   │   │   │   ├── UserDatabase.java
│   │   │   │   ├── Cart.java
│   │   │   │   ├── CartItem.java
│   │   │   │   ├── WishlistManager.java
│   │   │   │   ├── CartManager.java
│   │   │   │   ├── PopularAdapter.java
│   │   │   │   └── WrapContentRecyclerView.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   ├── drawable/
│   │   │   │   ├── values/
│   │   │   │   ├── menu/
│   │   │   │   └── xml/
│   │   │   └── AndroidManifest.xml
│   │   └── androidTest/
│   ├── build.gradle.kts
│   └── .gitignore
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
├── gradle.properties
├── .gitignore
├── README.md
└── .idea/
```

## Main App Components

### Authentication
- `LoginActivity.java` handles account login
- `SignUpActivity.java` handles account creation
- `Customer.java` and `UserDatabase.java` manage customer identity and account data

### Product Experience
- `MainActivity.java` acts as the main storefront screen
- `ProductListActivity.java` displays product lists
- `ProductDetailActivity.java` shows details for a selected product
- `Product.java` and `ProductCatalog.java` manage product details and catalog information

### Shopping Features
- `CartActivity.java` manages the cart screen
- `Cart.java` and `CartItem.java` handle cart contents
- `WishlistActivity.java` and `WishlistManager.java` manage saved items
- `CheckoutActivity.java` handles checkout flow

### Data Model
- `Customer.java` stores customer information
- `Order.java` represents a customer order
- `Clothing.java`, `Electronics.java`, and `Stationary.java` model product types
- `PopularDomain.java` supports the UI product display structure

## Screens Included

The app includes the following user interface screens and views:

- Login screen
- Sign up screen
- Main storefront page
- Product list screen
- Product detail screen
- Cart screen
- Wishlist screen
- Checkout screen

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

3. Let Gradle sync the project.

4. Connect an emulator or Android device.

5. Run the app from Android Studio.

## Run the App

Use the Android Studio Run button or execute:

```bash
./gradlew assembleDebug
```

Then install the APK on a connected device or emulator.

## Notes

- This project is structured as a mobile shopping app prototype and is suitable for learning Android UI development, Java-based app architecture, and basic e-commerce flow design.
- The repository contains the app source code, Android resource files, and Gradle configuration needed to build and run the project.

## Future Improvements

Possible improvements for this project include:

- Firebase authentication integration
- Real database backend support
- Payment gateway integration
- Product images from remote sources
- Search and sorting filters
- Admin panel for product management
- Improved data persistence and security

## License

This project is currently provided as a student project and may be used for educational purposes.

## Repository

https://github.com/Tommiieblvck/IS223-online-shopping-app-project
