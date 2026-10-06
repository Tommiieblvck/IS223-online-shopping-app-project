package com.example.theodistonline_shoppingapp;

import java.util.ArrayList;

public class ProductCatalog {
    private static ArrayList<Product> products = new ArrayList<>();

    static {
        // 1. Stationary (5 items)
        products.add(new Product("Highlighters Set", 25.00, "Stationary", R.drawable.highlighters));
        products.add(new Product("Calculator Pro", 45.00, "Stationary", R.drawable.calculator));
        products.add(new Product("Ballpoint Pens Box", 10.00, "Stationary", R.drawable.bollpoint_pens));
        products.add(new Product("Correction Tape", 12.50, "Stationary", R.drawable.correction_tape));
        products.add(new Product("Staplers & Punchers", 35.00, "Stationary", R.drawable.staplers_and_hole_punchers));

        // 2. Technology (5 items)
        products.add(new Product("Apple MacBook Air 13", 4500.00, "Technology", R.drawable.apple_macbook_air_13_laptop_m5_10ccpu_8c_gpu_16gb_512gb_silver_mdh74xa));
        products.add(new Product("DJI Avata Drone Combo", 1800.00, "Technology", R.drawable.dji_avata_360_motion_fly_more_combo_drone_dji_googles_n3_6937224137639));
        products.add(new Product("Torq Plus Blast Speaker", 150.00, "Technology", R.drawable.torq_plus_blast_wireless_speaker_60w_with_rgb_lights));
        products.add(new Product("HPE AP25 Access Point", 220.00, "Technology", R.drawable.hpe_ap25_networking_instant_on_access_points));
        products.add(new Product("Nintendo Switch Console", 1200.00, "Technology", R.drawable.nintendo_switch_console));

        // 3. Toys (5 items)
        products.add(new Product("Pace HBB Mini Ball", 18.00, "Toys", R.drawable.pace_hbb_mini_high_bounce_ball_6cm_assorted_colour));
        products.add(new Product("World Dinosaur FL2204", 65.00, "Toys", R.drawable.world_dinosaur_fl2204_2in1_modes_ages_6_building_blocks_collect_all_8_sets));
        products.add(new Product("Mini Pocket Blocks", 45.00, "Toys", R.drawable.mini_pocket_blocks_home_appliances_ages_6_collect_all_6_sets));
        products.add(new Product("UBTECH Trackbot Kit", 210.00, "Toys", R.drawable.ubtech_jimu_series_trackbot_kit));
        products.add(new Product("UBTECH WarriorBot", 240.00, "Toys", R.drawable.ubtech_jimu_series_warriorbot_kit_jra0602));

        // 4. Office Furniture (5 items)
        products.add(new Product("Office Desk Executive", 850.00, "Office Furniture", R.drawable.office_desk));
        products.add(new Product("Ergonomic Office Chair", 420.00, "Office Furniture", R.drawable.office_chair));
        products.add(new Product("Meeting Pod with Table", 2500.00, "Office Furniture", R.drawable.meeting_pod_with_coffee_table_1250lx632wx1260h));
        products.add(new Product("Workstations Cluster", 1620.00, "Office Furniture", R.drawable.workstations));
        products.add(new Product("Steel Filing Cabinet", 310.00, "Office Furniture", R.drawable.filing_cabinet_3));
    }

    public static ArrayList<Product> getAll() { return products; }

    public static ArrayList<Product> getByCategory(String category) {
        if (category == null || category.equals("All")) return products;
        ArrayList<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) result.add(p);
        }
        return result;
    }

    public static Product findByName(String name) {
        for (Product p : products) {
            if (p.getProductName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }
}
