package com.example.theodistonline_shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // --- UI Components ---
    private TextView tvUserName, tvCartBadge, tvEmpty;
    private EditText etSearch;
    private FrameLayout layoutCartIcon;
    private WrapContentRecyclerView recyclerViewPopular;
    private BottomNavigationView bottomNavigationView;

    // --- Category Click Views ---
    private View catStationary, catTechnology, catToys, catFurniture, catViewAll;

    // --- Data & Adapter ---
    private List<PopularDomain> allProductsList;
    private List<PopularDomain> filteredList;
    private PopularAdapter popularAdapter;

    // --- User Variable ---
    private final String userName = "John Rambo";
    private String selectedCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize UI Views
        initViews();

        // 2. Setup User Header & Cart Badge
        setupHeader();

        // 3. Setup Sample Data for Popular Products (5 items per category across 4 categories = 20 items with real images)
        setupSampleData();

        // 4. Setup RecyclerView & Adapter
        setupRecyclerView();

        // 5. Setup Search Filtering
        setupSearch();

        // 6. Setup Category Filtering
        setupCategories();

        // 7. Setup Bottom Navigation Bar
        setupBottomNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh cart badge count and adapter whenever activity resumes
        updateCartBadgeCount();
        if (popularAdapter != null) {
            popularAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Initialize all view references from activity_main.xml
     */
    private void initViews() {
        tvUserName = findViewById(R.id.tvUserName);
        tvCartBadge = findViewById(R.id.tvCartBadge);
        tvEmpty = findViewById(R.id.tvEmpty);
        etSearch = findViewById(R.id.etSearch);
        layoutCartIcon = findViewById(R.id.layoutCartIcon);
        recyclerViewPopular = findViewById(R.id.recyclerViewPopular);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        catStationary = findViewById(R.id.catStationary);
        catTechnology = findViewById(R.id.catTechnology);
        catToys = findViewById(R.id.catToys);
        catFurniture = findViewById(R.id.catFurniture);
        catViewAll = findViewById(R.id.catViewAll);
    }

    /**
     * Setup user name variable and cart icon click listener
     */
    private void setupHeader() {
        // Set user name from CustomerStore current user or fallback
        Customer current = CustomerStore.getCurrent();
        if (current != null && current.getFullName() != null) {
            tvUserName.setText(current.getFullName());
        } else {
            tvUserName.setText(userName);
        }

        // Tapping cart icon opens CartActivity
        layoutCartIcon.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CartActivity.class)));
    }

    /**
     * Update the red badge count on the cart icon
     */
    private void updateCartBadgeCount() {
        int totalItems = CartManager.getCart().getTotalItems();
        if (totalItems > 0) {
            tvCartBadge.setText(String.valueOf(totalItems));
            tvCartBadge.setVisibility(View.VISIBLE);
        } else {
            tvCartBadge.setVisibility(View.GONE);
        }
    }

    /**
     * Initialize 5 products per category across Stationary, Technology, Toys, Office Furniture (Total 20 items)
     * mapped to the user's pasted real image files in res/drawable.
     */
    private void setupSampleData() {
        allProductsList = new ArrayList<>();

        // 1. Stationary (5 items)
        allProductsList.add(new PopularDomain("Highlighters Set", 25.00, 4.7, "Stationary", R.drawable.highlighters));
        allProductsList.add(new PopularDomain("Calculator Pro", 45.00, 4.4, "Stationary", R.drawable.calculator));
        allProductsList.add(new PopularDomain("Ballpoint Pens Box", 10.00, 4.2, "Stationary", R.drawable.bollpoint_pens));
        allProductsList.add(new PopularDomain("Correction Tape", 12.50, 4.6, "Stationary", R.drawable.correction_tape));
        allProductsList.add(new PopularDomain("Staplers & Punchers", 35.00, 4.8, "Stationary", R.drawable.staplers_and_hole_punchers));

        // 2. Technology (5 items)
        allProductsList.add(new PopularDomain("Apple MacBook Air 13", 4500.00, 4.9, "Technology", R.drawable.apple_macbook_air_13_laptop_m5_10ccpu_8c_gpu_16gb_512gb_silver_mdh74xa));
        allProductsList.add(new PopularDomain("DJI Avata Drone Combo", 1800.00, 4.8, "Technology", R.drawable.dji_avata_360_motion_fly_more_combo_drone_dji_googles_n3_6937224137639));
        allProductsList.add(new PopularDomain("Torq Plus Blast Speaker", 150.00, 4.5, "Technology", R.drawable.torq_plus_blast_wireless_speaker_60w_with_rgb_lights));
        allProductsList.add(new PopularDomain("HPE AP25 Access Point", 220.00, 4.6, "Technology", R.drawable.hpe_ap25_networking_instant_on_access_points));
        allProductsList.add(new PopularDomain("Nintendo Switch Console", 1200.00, 4.9, "Technology", R.drawable.nintendo_switch_console));

        // 3. Toys (5 items)
        allProductsList.add(new PopularDomain("Pace HBB Mini Ball", 18.00, 4.6, "Toys", R.drawable.pace_hbb_mini_high_bounce_ball_6cm_assorted_colour));
        allProductsList.add(new PopularDomain("World Dinosaur FL2204", 65.00, 4.7, "Toys", R.drawable.world_dinosaur_fl2204_2in1_modes_ages_6_building_blocks_collect_all_8_sets));
        allProductsList.add(new PopularDomain("Mini Pocket Blocks", 45.00, 4.5, "Toys", R.drawable.mini_pocket_blocks_home_appliances_ages_6_collect_all_6_sets));
        allProductsList.add(new PopularDomain("UBTECH Trackbot Kit", 210.00, 4.8, "Toys", R.drawable.ubtech_jimu_series_trackbot_kit));
        allProductsList.add(new PopularDomain("UBTECH WarriorBot", 240.00, 4.9, "Toys", R.drawable.ubtech_jimu_series_warriorbot_kit_jra0602));

        // 4. Office Furniture (5 items)
        allProductsList.add(new PopularDomain("Office Desk Executive", 850.00, 4.9, "Office Furniture", R.drawable.office_desk));
        allProductsList.add(new PopularDomain("Ergonomic Office Chair", 420.00, 4.8, "Office Furniture", R.drawable.office_chair));
        allProductsList.add(new PopularDomain("Meeting Pod with Table", 2500.00, 5.0, "Office Furniture", R.drawable.meeting_pod_with_coffee_table_1250lx632wx1260h));
        allProductsList.add(new PopularDomain("Workstations Cluster", 1620.00, 4.6, "Office Furniture", R.drawable.workstations));
        allProductsList.add(new PopularDomain("Steel Filing Cabinet", 310.00, 4.7, "Office Furniture", R.drawable.filing_cabinet_3));

        filteredList = new ArrayList<>(allProductsList);
    }

    /**
     * Setup RecyclerView with GridLayoutManager (2 columns)
     */
    private void setupRecyclerView() {
        recyclerViewPopular.setLayoutManager(new GridLayoutManager(this, 2));
        popularAdapter = new PopularAdapter(filteredList, this, this::updateCartBadgeCount);
        recyclerViewPopular.setAdapter(popularAdapter);
    }

    /**
     * Setup search bar filtering logic
     */
    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString(), selectedCategory);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    /**
     * Setup category click listeners to filter products
     */
    private void setupCategories() {
        catStationary.setOnClickListener(v -> handleCategorySelect("Stationary"));
        catTechnology.setOnClickListener(v -> handleCategorySelect("Technology"));
        catToys.setOnClickListener(v -> handleCategorySelect("Toys"));
        catFurniture.setOnClickListener(v -> handleCategorySelect("Office Furniture"));
        catViewAll.setOnClickListener(v -> handleCategorySelect("All"));

        // Also wire "See all" text to show all products
        TextView tvSeeAll = findViewById(R.id.tvSeeAllCategories);
        if (tvSeeAll != null) {
            tvSeeAll.setOnClickListener(v -> handleCategorySelect("All"));
        }

        // Wire promotion banner "Buy Now" button to Stationary category
        View btnBannerBuy = findViewById(R.id.btnBannerBuy);
        if (btnBannerBuy != null) {
            btnBannerBuy.setOnClickListener(v -> handleCategorySelect("Stationary"));
        }
    }

    private void handleCategorySelect(String category) {
        selectedCategory = category;
        String query = etSearch.getText().toString();
        filterProducts(query, selectedCategory);
        Toast.makeText(this, "Category: " + category, Toast.LENGTH_SHORT).show();
    }

    /**
     * Filter products by search query and category
     */
    private void filterProducts(String query, String category) {
        filteredList.clear();
        String lowerQuery = query.toLowerCase().trim();

        for (PopularDomain item : allProductsList) {
            boolean matchesCategory = category.equals("All") || item.getCategory().equalsIgnoreCase(category);
            boolean matchesSearch = item.getTitle().toLowerCase().contains(lowerQuery);

            if (matchesCategory && matchesSearch) {
                filteredList.add(item);
            }
        }

        popularAdapter.updateList(filteredList);

        // Handle empty search results state
        if (filteredList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerViewPopular.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerViewPopular.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Setup Bottom Navigation Bar actions ensuring smooth flow across pages
     */
    private void setupBottomNavigation() {
        // Set Explorer as selected by default
        bottomNavigationView.setSelectedItemId(R.id.nav_explorer);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_cart) {
                // Open CartActivity when Cart is tapped
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            } else if (id == R.id.nav_explorer) {
                // Already on Explorer (Home screen)
                return true;
            } else if (id == R.id.nav_profile) {
                Customer user = CustomerStore.getCurrent();
                String info = "Name: " + (user != null ? user.getFullName() : "N/A") +
                        "\nUsername: " + (user != null ? user.getUsername() : "N/A") +
                        "\nEmail: " + (user != null ? user.getEmail() : "N/A") +
                        "\nPhone: " + (user != null ? user.getPhone() : "N/A") +
                        "\nLocation: " + (user != null ? user.getLocation() : "N/A");

                new AlertDialog.Builder(this)
                        .setTitle("User Profile")
                        .setMessage(info)
                        .setPositiveButton("Close", null)
                        .setNeutralButton("Logout", (dialog, which) -> {
                            CustomerStore.logout(this);
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        })
                        .show();
                return true;
            }
            return false;
        });
    }
}
