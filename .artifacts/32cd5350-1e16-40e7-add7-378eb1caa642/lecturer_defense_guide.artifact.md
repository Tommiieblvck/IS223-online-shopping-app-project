# Theodist Super Store — Lecturer One-on-One Defense & Presentation Guide

This guide breaks down the three core parts of the application (**Login**, **Listing**, and **Adding to Cart**) in detail so you can explain what the code does, how it works, and why it was designed that way during your lecturer defense.

---

## Part 1: The Login Part
### What the Code Does
The login module authenticates users against a secure local SQLite database and manages session persistence using Android's `SharedPreferences` for the "Remember Me" feature.

### Key Files Involved
* `LoginActivity.java` & `activity_login.xml` (UI & Controller)
* `CustomerStore.java` (Session & Bridge)
* `UserDatabase.java` (SQLite Database & Hashing)
* `Customer.java` (Data Model)

### Step-by-Step Explanation for Your Lecturer
1. **User Input & Validation**:
   * When the user opens the app, `LoginActivity` checks `SharedPreferences` for a remembered username. If found, it auto-logs the user in and skips straight to `MainActivity`.
   * Otherwise, the user enters their username/email and password, then taps **Login**. `LoginActivity` validates that neither field is empty.
2. **Authentication Bridge (`CustomerStore`)**:
   * `LoginActivity` calls `CustomerStore.login(context, usernameOrEmail, password)`.
   * `CustomerStore` instantiates `UserDatabase` and calls `db.loginUser(...)`.
3. **Secure Database Lookup & Hashing (`UserDatabase`)**:
   * `UserDatabase` queries the `users` table where `username = ? OR email = ?`.
   * **Security (SHA-256 + Salt)**: Passwords are never stored in plain text. When the user registered, `UserDatabase` generated a cryptographically secure random 16-byte salt using `SecureRandom` and hashed the password with **SHA-256**.
   * During login, the database retrieves the user's stored salt, re-hashes the entered password with that salt, and compares the resulting hash against the stored `password_hash`. If they match, a `Customer` object is returned and stored in memory as `CustomerStore.current`.
4. **Session Persistence ("Remember Me")**:
   * If the "Remember Me" checkbox is ticked, the username is saved in `SharedPreferences` (`TheodistPrefs`), ensuring returning users bypass the login screen.

---

## Part 2: The Listing Part (Home Screen & Catalog)
### What the Code Does
The listing module renders the frosted purple glass home dashboard (`MainActivity`), manages 4 categories (*Stationary*, *Technology*, *Toys*, *Office Furniture*), drives real-time search filtering, and displays 20 product cards (5 per category) in a responsive 2-column grid.

### Key Files Involved
* `MainActivity.java` & `activity_main.xml` (Dashboard & UI)
* `PopularAdapter.java` & `viewholder_pup_list.xml` (RecyclerView Adapter & Card Layout)
* `PopularDomain.java` (Product Card Model)
* `WrapContentRecyclerView.java` (Custom RecyclerView for ScrollViews)

### Step-by-Step Explanation for Your Lecturer
1. **Initialization (`MainActivity`)**:
   * `onCreate()` initializes UI views and retrieves the logged-in user's full name from `CustomerStore.getCurrent()` to display in the header ("Welcome, [Name]").
   * `setupSampleData()` initializes `allProductsList` with 20 sample products (5 items per category) mapped to actual image resource IDs in `res/drawable`.
2. **RecyclerView & Grid Layout**:
   * `setupRecyclerView()` binds `PopularAdapter` to `recyclerViewPopular` using a `GridLayoutManager` with a span count of 2 (2 columns).
   * **Technical Detail**: Because the RecyclerView is placed inside a ScrollView, we created `WrapContentRecyclerView`, which overrides `onMeasure()` to allow the grid to expand fully and prevent vertical clipping of grid rows.
3. **Real-Time Search Filtering**:
   * `etSearch` has a `TextWatcher` attached. As the user types, `onTextChanged()` triggers `filterProducts()`, which iterates through the product list matching both the search query and the currently selected category in real-time, updating the adapter dynamically. If no products match, an empty state view ("No products found") appears.
4. **Category Selection**:
   * Tapping category icons (`catStationary`, `catTechnology`, `catToys`, `catFurniture`, or `catViewAll`) updates `selectedCategory`, re-filters the product list, and refreshes the grid. Tapping the promotion banner's "Buy Now" button automatically selects the Stationary category.

---

## Part 3: The Adding to Cart Part
### What the Code Does
The cart module allows users to add items to their shopping cart from the home screen grid, tracks quantities, calculates live totals, updates the header cart badge count, and manages cart item modification (`+`, `-`, `Remove`).

### Key Files Involved
* `CartManager.java` (Singleton Cart Provider)
* `Cart.java` (Cart Logic & Totals Calculation)
* `CartItem.java` (Cart Item Model)
* `PopularAdapter.java` (Triggers Add-to-Cart from Grid)
* `CartActivity.java` & `activity_cart.xml` (Cart View & Management)

### Step-by-Step Explanation for Your Lecturer
1. **Global Cart Singleton (`CartManager`)**:
   * `CartManager.getCart()` returns a single, globally shared `Cart` instance. This ensures the cart items persist across different activities during the user session.
2. **Adding an Item (`PopularAdapter`)**:
   * Every product card has a round purple `+` button (`btnAdd`).
   * When tapped, `PopularAdapter` converts the grid item (`PopularDomain`) into a `Product` object and calls `CartManager.getCart().addProduct(product)`.
3. **Cart Business Logic (`Cart`)**:
   * Inside `Cart.java`, `addProduct(Product product)` checks if the product already exists in `cartItems` (`ArrayList<CartItem>`).
   * If it already exists, its quantity is incremented by 1 (`item.increase()`). If it does not exist, a new `CartItem` object is created with quantity 1 and added to the list.
4. **Live Badge & Feedback**:
   * A Toast notification (*"Added to cart"*) confirms the action.
   * The callback listener `onCartChanged()` triggers `updateCartBadgeCount()`, which queries `CartManager.getCart().getTotalItems()` and updates the red notification badge on the cart icon in the header in real-time.
5. **Cart Management & Checkout (`CartActivity`)**:
   * Tapping the cart icon opens `CartActivity`, where users can adjust quantities (`+` and `-`), remove items, view the computed total price, and proceed to `CheckoutActivity`.
