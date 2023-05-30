package com.example.myamazon;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

class mySql extends SQLiteOpenHelper {
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_CUSTOMER_ID = "customer_id";
    public static final String Admin_TABLE_NAME = "Admin";
    public static final String Admin_TB_NAME = "Admin";
    public static final String DB_CLN_ID = "id";
    public static final String DB_CLN_PASSWORD = "password";
    public static final String DB_CLN_EMILE = "emile";
    public static final String DB_CLN_NAME = "name";
    public static final String Product_TABLE_NAME = "productDB";
    public static final String Product_CLN_ID = "id";
    public static final String Product_CLN_name = "name";
    public static final String Product_CLN_description = "description";
    public static final String Product_CLN_image = "image";
    public static final String Product_CLN_price = "price";
    public static final String Product_CLN_quantity = "quantity";
    public static final String Customer_TB_NAME = "Customer";
    public static final String Customer_CLN_ID = "id";
    public static final String Customer_CLN_PASSWORD = "password";
    public static final String Customer_CLN_EMILE = "emile";
    public static final String Customer_CLN_Name = "name";
    public static final String Customer_CLN_Image = "image";
    private static final String TABLE_PURCHASES = "purchases";
    private static final String COLUMN_PURCHASE_CUSTOMER_ID = "customer_id";
    private static final String COLUMN_PURCHASE_PRODUCT_ID = "product_id";
    public static final int DB_VERSION = 4;

    public mySql(@Nullable Context context) {
        super ( context, Admin_TABLE_NAME, null, DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL ( "CREATE TABLE " + Admin_TB_NAME + " (" + DB_CLN_ID + " INTEGER PRIMARY  KEY AUTOINCREMENT ," +
                "" + DB_CLN_NAME + " Text , " + DB_CLN_PASSWORD + " Text, " + DB_CLN_EMILE + " TEXT )" );


        db.execSQL ( "CREATE TABLE " + Customer_TB_NAME + " (" + Customer_CLN_ID + " INTEGER PRIMARY  KEY AUTOINCREMENT ," +
                "" + Customer_CLN_Name + " Text unique , " + Customer_CLN_PASSWORD + " Text, " + Customer_CLN_EMILE + " TEXT , " + Customer_CLN_Image + " Text)" );


        db.execSQL ( "CREATE TABLE " + Product_TABLE_NAME + " (" + Product_CLN_ID + " INTEGER PRIMARY  KEY AUTOINCREMENT," +
                "" + Product_CLN_name + " Text , " + Product_CLN_description + " Text,  " + Product_CLN_image + " Text," + Product_CLN_price + " REAL, "
                + Product_CLN_quantity + " INteger)" );


        db.execSQL ( "CREATE TABLE " + TABLE_PURCHASES + " (" +
                COLUMN_PRODUCT_ID + " INTEGER," +
                COLUMN_CUSTOMER_ID + " INTEGER," +
                "FOREIGN KEY(" + COLUMN_PRODUCT_ID + ") REFERENCES Products(id)," +
                "FOREIGN KEY(" + COLUMN_CUSTOMER_ID + ") REFERENCES Customers(id)" +
                ");" );

    }

    public void associateProductWithCustomer(int productId, int customerId) {
        SQLiteDatabase db = getWritableDatabase ( );
        ContentValues values = new ContentValues ( );
        values.put ( COLUMN_PRODUCT_ID, productId );
        values.put ( COLUMN_CUSTOMER_ID, customerId );
        db.insert ( TABLE_PURCHASES, null, values );
        db.close ( );
    }
    @SuppressLint("Range")
    public List<Product> getProductsByCustomer2(int customerId) {
        List<Product> products = new ArrayList<> ( );

        SQLiteDatabase db = getReadableDatabase ( );

        String query = "SELECT p.* FROM " + Product_TABLE_NAME + " p " +
                "JOIN " + TABLE_PURCHASES + " pc ON p." + Product_CLN_ID + " = pc." + COLUMN_PRODUCT_ID + " " +
                "WHERE pc." + COLUMN_CUSTOMER_ID + " = ?";

        String[] selectionArgs = {String.valueOf ( customerId )};
        Cursor cursor = db.rawQuery ( query, selectionArgs );
        if (cursor != null && cursor.moveToFirst ( )) {
            do {
                int id = cursor.getInt ( cursor.getColumnIndex ( Product_CLN_ID ) );
                String name = cursor.getString ( cursor.getColumnIndex ( Product_CLN_name ) );
                String img = cursor.getString ( cursor.getColumnIndex ( Product_CLN_image ) );
                String description = cursor.getString ( cursor.getColumnIndex ( Product_CLN_description ) );
                double prise = cursor.getDouble ( cursor.getColumnIndex ( Product_CLN_price ) );
                int quantity = cursor.getInt ( cursor.getColumnIndex ( Product_CLN_quantity ) );
                Product product = new Product ( name,img, description, prise, quantity );
                products.add ( product );
            } while (cursor.moveToNext ( ));

            cursor.close ( );
        }

        db.close ( );

        return products;
    }

    // ...


    public void addProductsToCustomer(int customerId, ArrayList<Integer> productIds) {
        SQLiteDatabase db = getWritableDatabase ( );
        db.beginTransaction ( );

        try {
            for (int productId : productIds) {
                ContentValues values = new ContentValues ( );
                values.put ( COLUMN_PURCHASE_CUSTOMER_ID, customerId );
                values.put ( COLUMN_PURCHASE_PRODUCT_ID, productId );
                db.insert ( TABLE_PURCHASES, null, values );
            }
            db.setTransactionSuccessful ( );
        } finally {
            db.endTransaction ( );
        }
    }


    @SuppressLint("Range")
    public ArrayList<Product> getAllProductCustomer(int customId) {
        ArrayList<Product> products = new ArrayList<> ( );
        SQLiteDatabase db = getReadableDatabase ( );
        try (Cursor cursor = db.rawQuery ( "SELECT * FROM " + TABLE_PURCHASES + " WHERE id = ?  ", new String[]{String.valueOf ( customId )} )) {
            if (cursor.moveToFirst ( )) {
                do {
                    int id = cursor.getInt ( cursor.getColumnIndex ( Product_CLN_ID ) );
                    String name = cursor.getString ( cursor.getColumnIndex ( Product_CLN_name ) );
                    String img = cursor.getString ( cursor.getColumnIndex ( Product_CLN_image ) );
                    String description = cursor.getString ( cursor.getColumnIndex ( Product_CLN_description ) );
                    double prise = cursor.getDouble ( cursor.getColumnIndex ( Product_CLN_price ) );
                    int quantity = cursor.getInt ( cursor.getColumnIndex ( Product_CLN_quantity ) );
                    Product p = new Product ( name, img, description, prise, quantity );
                    p.setId ( id );
                    products.add ( p );
                } while (cursor.moveToNext ( ));
                cursor.close ( );
            }
        }


        return products;
    }

    public boolean deleteItemFromBasket(int customerId, int productId) {
        SQLiteDatabase db = getWritableDatabase ( );
        String whereClause = COLUMN_CUSTOMER_ID + " = ? AND " + COLUMN_PRODUCT_ID + " = ?";
        String[] whereArgs = {String.valueOf ( customerId ), String.valueOf ( productId )};
        int rowsDeleted = db.delete ( TABLE_PURCHASES, whereClause, whereArgs );
        db.close ( );
        return rowsDeleted > 0;
    }


    @SuppressLint("Range")
    public int getCustomerIdByName(String customerName) {
        SQLiteDatabase db = getReadableDatabase ( );
        int customerId = -1;
        String[] columns = {Customer_CLN_ID};
        String selection = Customer_CLN_Name + " = ?";
        String[] selectionArgs = {customerName};
        Cursor cursor = db.query ( Customer_TB_NAME, columns, selection, selectionArgs, null, null, null );
        if (cursor.moveToFirst ( )) {
            customerId = cursor.getInt ( cursor.getColumnIndex ( Customer_CLN_ID ) );
        }
        cursor.close ( );
        return customerId;
    }

    @SuppressLint("Range")
    public int getCustomerIdByUsernameAndPassword(String username, String password) {
        SQLiteDatabase db = getReadableDatabase ( );
        int customerId = -1;

        String[] columns = {Customer_CLN_ID};
        String selection = Customer_CLN_Name + " = ? AND " + Customer_CLN_Name + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query ( Customer_TB_NAME, columns, selection, selectionArgs, null, null, null );

        if (cursor.moveToFirst ( )) {
            customerId = cursor.getInt ( cursor.getColumnIndex ( Customer_CLN_ID ) );
        }

        cursor.close ( );
        return customerId;
    }

    @SuppressLint("Range")
    public Customer getUserBYName(String Name) {
        SQLiteDatabase db = getReadableDatabase ( );
        Cursor cursor = db.rawQuery ( "SELECT * FROM " + Customer_TB_NAME + " WHERE " + Customer_CLN_Name + " =?", new String[]{String.valueOf ( Name )} );
        if (cursor.moveToFirst ( )) {
            int id = cursor.getInt ( cursor.getColumnIndex ( Customer_CLN_ID ) );
            String name = cursor.getString ( cursor.getColumnIndex ( Customer_CLN_Name ) );
            String emile = cursor.getString ( cursor.getColumnIndex ( Customer_CLN_EMILE ) );
            String password = cursor.getString ( cursor.getColumnIndex ( Customer_CLN_PASSWORD ) );
            Customer User = new Customer ( name, password, emile );
            cursor.close ( );
            return User;
        }

        return null;
    }

    public boolean checkUserAdmin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase ( );
        Cursor cursor = db.rawQuery ( String.format ( "SELECT * FROM %s WHERE %s = ? AND %s = ?", Admin_TB_NAME, DB_CLN_NAME, DB_CLN_PASSWORD ), new String[]{username, password} );
        int count = cursor.getCount ( );
        cursor.close ( );
        db.close ( );
        return count > 0;
    }

    @SuppressLint("Range")
    public int getCustomerIdByName2(String name) {
        SQLiteDatabase db = getReadableDatabase ( );

        int customerId = -1;

        if (name == null) {
            throw new IllegalArgumentException ( "Name cannot be null" );
        }

        // Perform the query
        String[] columns = {Customer_CLN_ID};
        String selection = "name = ?";
        String[] selectionArgs = {name};
        Cursor cursor = db.query ( Customer_TB_NAME, columns, selection, selectionArgs, null, null, null );

        // Retrieve the customer ID from the cursor
        if (cursor.moveToFirst ( )) {
            customerId = cursor.getInt ( cursor.getColumnIndex ( Customer_CLN_ID ) );
        }

        // Close the cursor and database connection
        cursor.close ( );
        db.close ( );

        return customerId;
    }

    public boolean checkUserNameAdmin(String username) {
        SQLiteDatabase db = this.getReadableDatabase ( );
        Cursor cursor = db.rawQuery ( String.format ( "SELECT * FROM %s WHERE %s = ?  ", Admin_TB_NAME, DB_CLN_NAME ), new String[]{username} );
        int count = cursor.getCount ( );
        cursor.close ( );
        db.close ( );
        return count > 0;
    }

    public boolean checkPasswordAdmin(String password) {
        SQLiteDatabase db = this.getReadableDatabase ( );
        int count;
        try (Cursor cursor = db.rawQuery ( "SELECT * FROM Admin WHERE password = ?", new String[]{password} )) {
            count = cursor.getCount ( );
        }
        db.close ( );
        return count > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL ( " DROP TABLE IF EXISTS "+Admin_TABLE_NAME );
        db.execSQL ( " DROP TABLE IF EXISTS "+Product_TABLE_NAME );
        db.execSQL ( " DROP TABLE IF EXISTS "+Customer_CLN_ID );
        db.execSQL ( " DROP TABLE IF EXISTS "+TABLE_PURCHASES );
        onCreate ( db );

    }

    public void insertAdmin(Admin Admin) {
        SQLiteDatabase database = getWritableDatabase ( );
        ContentValues values = new ContentValues ( );
        values.put ( DB_CLN_NAME, Admin.getUserName ( ) );
        values.put ( DB_CLN_PASSWORD, Admin.getPassword ( ) );
        values.put ( DB_CLN_EMILE, Admin.getEmail ( ) );
        long result = database.insert ( Admin_TB_NAME, null, values );

    }


    public boolean insertProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase ( );
        ContentValues values = new ContentValues ( );
        values.put ( Product_CLN_name, product.getName ( ) );
        values.put ( Product_CLN_image, String.valueOf ( product.getImage ( ) ) );
        values.put ( Product_CLN_description, product.getDescription ( ) );
        values.put ( Product_CLN_price, product.getPrice ( ) );
        values.put ( Product_CLN_quantity, product.getQuantity ( ) );
        long result = db.insert ( Product_TABLE_NAME, null, values );
        return -1 != result;
    }

    public void
    updateProduct(int productId, String newName, String image, String Description, double price, int q) {
        SQLiteDatabase db = this.getWritableDatabase ( );
        ContentValues values = new ContentValues ( );
        values.put ( Product_CLN_name, newName );
        values.put ( Product_CLN_description, Description );
        values.put ( Product_CLN_price, price );
        values.put ( Product_CLN_quantity, q );
        values.put ( Product_CLN_image, image );
        String whereClause = Product_CLN_ID + " = ?";
        String[] whereArgs = {String.valueOf ( productId )};
        Log.d ( "updateTest", "updateProduct: " + productId );
        db.update ( Product_TABLE_NAME, values, whereClause, whereArgs );
        db.close ( );
    }

    //    public void deleteProductCustomer(int customerId, int productId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String whereClause = COLUMN_CUSTOMER_ID + " = ? AND " + COLUMN_PRODUCT_ID + " = ?";
//        String[] whereArgs = {String.valueOf(customerId), String.valueOf(productId)};
//        db.delete(TABLE_PURCHASES, whereClause, whereArgs);
//        db.close();
//    }
    public void deleteProductCustomer(int customerId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase ( );
        String whereClause = COLUMN_CUSTOMER_ID + " = ? AND " + COLUMN_PRODUCT_ID + " = ?";
        String[] whereArgs = {String.valueOf ( productId ), String.valueOf ( customerId )};
        db.delete ( TABLE_PURCHASES, whereClause, whereArgs );
        db.close ( );
    }

    public void
    updateQuantity(int productId, int q) {
        SQLiteDatabase db = this.getWritableDatabase ( );
        ContentValues values = new ContentValues ( );
        values.put ( Product_CLN_quantity, q );
        String whereClause = Product_CLN_ID + " = ?";
        String[] whereArgs = {String.valueOf ( productId )};
        Log.d ( "updateTest", "updateProduct: " + productId );
        db.update ( Product_TABLE_NAME, values, whereClause, whereArgs );
        db.close ( );
    }

    public void
    updateCustomer(String Name, String newName, String password, String emile) {
        SQLiteDatabase db = this.getWritableDatabase ( );
        ContentValues values = new ContentValues ( );
        values.put ( Customer_CLN_Name, newName );
        values.put ( Customer_CLN_EMILE, emile );
        values.put ( Customer_CLN_PASSWORD, password );
        String whereClause = Customer_CLN_Name + " = ?";
        String[] whereArgs = {String.valueOf ( Name )};
        db.update ( Customer_TB_NAME, values, whereClause, whereArgs );
        db.close ( );
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = getWritableDatabase ( );
        String[] args = {String.valueOf ( id )};
        int result = db.delete ( Product_TABLE_NAME, "id=?", args );
    }
    public void deleteProductC(int id) {
        SQLiteDatabase db = getWritableDatabase ( );
        String[] args = {String.valueOf ( id )};
        int result = db.delete ( Product_TABLE_NAME, "id=?", args );
    }
    @SuppressLint("Range")
    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> products = new ArrayList<> ( );
        SQLiteDatabase db = getReadableDatabase ( );
        try (Cursor cursor = db.rawQuery ( "SELECT * FROM productDB", null )) {
            if (cursor.moveToFirst ( )) {
                do {
                    int id = cursor.getInt ( cursor.getColumnIndex ( Product_CLN_ID ) );
                    String name = cursor.getString ( cursor.getColumnIndex ( Product_CLN_name ) );
                    String img = cursor.getString ( cursor.getColumnIndex ( Product_CLN_image ) );
                    String description = cursor.getString ( cursor.getColumnIndex ( Product_CLN_description ) );
                    double prise = cursor.getDouble ( cursor.getColumnIndex ( Product_CLN_price ) );
                    int quantity = cursor.getInt ( cursor.getColumnIndex ( Product_CLN_quantity ) );
                    Product p = new Product ( name, img, description, prise, quantity );
                    p.setId ( id );
                    products.add ( p );
                } while (cursor.moveToNext ( ));
                cursor.close ( );
            }
        }


        return products;
    }

    @SuppressLint("Range")
    public Product getProduct(int productId) {
        SQLiteDatabase db = getReadableDatabase ( );
        try (Cursor cursor = db.rawQuery ( "SELECT * FROM productDB WHERE id =?", new String[]{String.valueOf ( productId )} )) {
            if (cursor.moveToFirst ( )) {
                int id = cursor.getInt ( cursor.getColumnIndex ( Product_CLN_ID ) );
                String name = cursor.getString ( cursor.getColumnIndex ( Product_CLN_name ) );
                String img = cursor.getString ( cursor.getColumnIndex ( Product_CLN_image ) );
                String description = cursor.getString ( cursor.getColumnIndex ( Product_CLN_description ) );
                double prise = cursor.getDouble ( cursor.getColumnIndex ( Product_CLN_price ) );
                int quantity = cursor.getInt ( cursor.getColumnIndex ( Product_CLN_quantity ) );
                Product product = new Product ( id, name, img, description, prise, quantity );
                cursor.close ( );
                return product;
            }
        }

        return null;
    }

    @SuppressLint("Range")
    public ArrayList<Product> getProduct(String search) {
        ArrayList<Product> products = new ArrayList<> ( );
        SQLiteDatabase db = getReadableDatabase ( );
        try (Cursor cursor = db.rawQuery ( "SELECT * FROM productDB WHERE name LIKE ?", new String[]{search + "%"} )) {
            if (cursor.moveToFirst ( )) {
                do {
                    int id = cursor.getInt ( cursor.getColumnIndex ( Product_CLN_ID ) );
                    String name = cursor.getString ( cursor.getColumnIndex ( Product_CLN_name ) );
                    String img = cursor.getString ( cursor.getColumnIndex ( Product_CLN_image ) );
                    String description = cursor.getString ( cursor.getColumnIndex ( Product_CLN_description ) );
                    double prise = cursor.getDouble ( cursor.getColumnIndex ( Product_CLN_quantity ) );
                    int quantity = cursor.getInt ( cursor.getColumnIndex ( Product_CLN_quantity ) );
                    products.add ( new Product ( name, img, description, prise, quantity ) );
                } while (cursor.moveToNext ( ));
                cursor.close ( );
            }
        }
        return products;
    }

    public boolean checkUserCustomer(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase ( );
        Cursor cursor = db.rawQuery ( "SELECT * FROM Customer WHERE name = ? AND password = ?", new String[]{username, password} );
        int count = cursor.getCount ( );
        cursor.close ( );
        db.close ( );
        return 0 < count;
    }

    public boolean checkUserNameCustomer(String username) {
        SQLiteDatabase db = this.getReadableDatabase ( );
        Cursor cursor = db.rawQuery ( "SELECT * FROM " + Customer_TB_NAME + " WHERE " +
                Customer_CLN_Name + " = ?  ", new String[]{username} );
        int count = cursor.getCount ( );
        cursor.close ( );
        db.close ( );
        return count > 0;
    }

    public boolean checkPasswordCustomer(String password) {
        SQLiteDatabase db = this.getReadableDatabase ( );
        int count;
        try (Cursor cursor = db.rawQuery ( "SELECT * FROM Customer WHERE password = ?", new String[]{password} )) {
            count = cursor.getCount ( );
        }
        db.close ( );
        return count > 0;
    }

    public boolean insertCustomer(Customer customer) {
        SQLiteDatabase db = getWritableDatabase ( );
        ContentValues values = new ContentValues ( );
        values.put ( Customer_CLN_Name, customer.getUserName ( ) );
        values.put ( Customer_CLN_PASSWORD, customer.getPassword ( ) );
        values.put ( Customer_CLN_EMILE, customer.getEmail ( ) );

        long result = db.insert ( Customer_TB_NAME, null, values );
        return result != -1;

    }


    public boolean deleteCustomer(int id) {
        SQLiteDatabase db = getWritableDatabase ( );
        String[] args = {String.valueOf ( id )};
        int result = db.delete ( Customer_TB_NAME, "id=?", args );
        return result > 0;
    }


    @SuppressLint("Range")
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<> ( );
        SQLiteDatabase db = getReadableDatabase ( );
        try (Cursor cursor = db.rawQuery ( "SELECT * FROM " + Customer_TB_NAME, null )) {
            if (cursor.moveToFirst ( )) {
                do {
                    int id = cursor.getInt ( cursor.getColumnIndex ( Customer_CLN_ID ) );
                    String name = cursor.getString ( cursor.getColumnIndex ( Customer_CLN_Name ) );
                    String emile = cursor.getString ( cursor.getColumnIndex ( Customer_CLN_EMILE ) );
                    String password = cursor.getString ( cursor.getColumnIndex ( Customer_CLN_PASSWORD ) );
                    customers.add ( new Customer ( name, password, emile ) );
                } while (cursor.moveToNext ( ));
                cursor.close ( );
                db.close ( );
            }
        }
        return customers;
    }

    @SuppressLint("Range")
    public Customer getCustomer(int customerId) {
        SQLiteDatabase db = getReadableDatabase ( );
        try (Cursor cursor = db.rawQuery ( "SELECT * FROM Customer WHERE id =?", new String[]{String.valueOf ( customerId )} )) {
            if (cursor.moveToFirst ( )) {
                int id = cursor.getInt ( cursor.getColumnIndex ( Customer_CLN_ID ) );
                String name = cursor.getString ( cursor.getColumnIndex ( Customer_CLN_Name ) );
                String emile = cursor.getString ( cursor.getColumnIndex ( Customer_CLN_EMILE ) );
                String password = cursor.getString ( cursor.getColumnIndex ( Customer_CLN_PASSWORD ) );
                Customer customer = new Customer ( name, password, emile );
                cursor.close ( );
                return customer;
            }
        }

        return null;
    }

    @SuppressLint("Range")
    public ArrayList<Customer> getCustomer(String search) {
        ArrayList<Customer> Customer = new ArrayList<> ( );
        SQLiteDatabase db = getReadableDatabase ( );
        try (Cursor cursor = db.rawQuery ( "SELECT * FROM Customer WHERE name LIKE ?", new String[]{search + "%"} )) {
            if (cursor.moveToFirst ( )) {
                do {
                    String name = cursor.getString ( cursor.getColumnIndex ( Customer_CLN_Name ) );
                    String password = cursor.getString ( cursor.getColumnIndex ( Customer_CLN_PASSWORD ) );
                    String emile = cursor.getString ( cursor.getColumnIndex ( Customer_CLN_EMILE ) );
                    Customer customer = new Customer ( name, password, emile );
                } while (cursor.moveToNext ( ));
                cursor.close ( );
                db.close ( );
            }
        }
        return Customer;
    }

}