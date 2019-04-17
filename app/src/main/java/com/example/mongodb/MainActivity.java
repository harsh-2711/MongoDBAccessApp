package com.example.mongodb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView listView;
    SearchView searchView;

    ArrayList<String> propertyName, propertyLocation, propertyType, propertyAmount, contractType, floor, carpetArea, state, city, description;
    ArrayAdapter<String> adapter;

    Button button;
    EditText editText;
    Spinner typeProperty, buySell;

    String selected_place;
    String contract = "None";
    String rentSell = "None";
    String none = "none";

    TextView result_text;

    ListView list;

    ArrayList<Result> data;

    CustomAdapter customAdapter;

    Boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        listView = (ListView) findViewById(R.id.list_view);
        searchView = (SearchView) findViewById(R.id.search_view);

        arrayList = new ArrayList<>();

        MongoDB mongoDB = new MongoDB();
        mongoDB.execute();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(arrayList.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    //adapter.getFilter().filter(newText);
                return false;
            }
        });

        */

        button = (Button) findViewById(R.id.search_button);
        editText = (EditText) findViewById(R.id.edit_text);
        list = (ListView) findViewById(R.id.list);
        result_text = (TextView) findViewById(R.id.result_text);
        typeProperty = (Spinner) findViewById(R.id.property_type);
        buySell = (Spinner) findViewById(R.id.buy_sell);

        propertyName = new ArrayList<>();
        propertyLocation = new ArrayList<>();
        propertyType = new ArrayList<>();
        propertyAmount = new ArrayList<>();
        contractType = new ArrayList<>();
        floor = new ArrayList<>();
        carpetArea = new ArrayList<>();
        state = new ArrayList<>();
        city = new ArrayList<>();
        description = new ArrayList<>();

        data = new ArrayList<>();
        customAdapter = new CustomAdapter(data, getApplicationContext());

        list.setAdapter(customAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Result result = data.get(position);

                int adap_pos = result.getPosition();

                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("Place", result.getPlaceName());
                intent.putExtra("Description", result.getDescription());
                intent.putExtra("PropertyLocation", propertyLocation.get(adap_pos));
                intent.putExtra("PropertyType", propertyType.get(adap_pos));
                intent.putExtra("ProeprtyAmount", propertyAmount.get(adap_pos));
                intent.putExtra("ContractType", contractType.get(adap_pos));
                intent.putExtra("Floor", floor.get(adap_pos));
                intent.putExtra("CarpetArea", carpetArea.get(adap_pos));
                intent.putExtra("City", city.get(adap_pos));
                intent.putExtra("State", state.get(adap_pos));

                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText("");
                propertyName.clear();
                propertyLocation.clear();
                propertyType.clear();
                propertyAmount.clear();
                contractType.clear();
                floor.clear();
                carpetArea.clear();
                state.clear();
                city.clear();
                description.clear();
                data.clear();
                customAdapter.notifyDataSetChanged();
                selected_place = editText.getText().toString();
                MongoDB mongoDB = new MongoDB();
                mongoDB.execute();
            }
        });


        typeProperty.setOnItemSelectedListener(this);
        buySell.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> typeOfProperty = new ArrayList<String>();
        typeOfProperty.add("None");
        typeOfProperty.add("Land");
        typeOfProperty.add("Apartment");
        typeOfProperty.add("Shop");

        List<String> buyOrSell = new ArrayList<String>();
        buyOrSell.add("None");
        buyOrSell.add("Buy");
        buyOrSell.add("Rent");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeOfProperty);
        typeProperty.setAdapter(dataAdapter);

        ArrayAdapter<String> newDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, buyOrSell);
        buySell.setAdapter(newDataAdapter);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        if(item.equals("None")){
            none = "None";
        } else {
            if (item.equals("Land") || item.equals("Apartment") || item.equals("Shop")) {
                contract = item;
                none = "No";
                Toast.makeText(MainActivity.this, contract, Toast.LENGTH_SHORT).show();
            } else {
                none = "No";
                if (item.equals("Buy")) {
                    rentSell = "forsell";
                } else {
                    rentSell = "rent";
                }
                Toast.makeText(MainActivity.this, rentSell, Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    private class MongoDB extends AsyncTask<Void, Void, Void> {

        String server_output = null;
        String temp_output = null;
        Boolean temp = false;

        @Override
        protected Void doInBackground(Void... voids) {

            String url_b = "https://api.mlab.com/api/1/databases/estate_locator/collections/properties?apiKey=6cbhJbwsGZSwc7PCcMgj8NNoa1mHHTab";

            URL url = null;
            try {
                url = new URL(url_b);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url
                        .openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setRequestProperty("Accept", "application/json");

            try {
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                while ((temp_output = br.readLine()) != null) {
                    server_output = temp_output;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            String mongoarray = "{ DB_output: " + server_output + "}";
            Object o = com.mongodb.util.JSON.parse(mongoarray);

            DBObject dbObj = (DBObject) o;
            BasicDBList contacts = (BasicDBList) dbObj.get("DB_output");

            int counter = 0;
            for (Object obj : contacts) {
                DBObject userObj = (DBObject) obj;
                Log.d("Name", userObj.get("propertyName").toString());

                if ((userObj.get("propertyName").toString().toLowerCase()).contains(selected_place.toLowerCase()) || (selected_place.toLowerCase()).contains((userObj.get("propertyName").toString().toLowerCase()))) {
                    if(none.equals("None")){
                        propertyName.add(userObj.get("propertyName").toString());
                        propertyLocation.add(userObj.get("propertyLocation").toString());
                        propertyType.add(userObj.get("property_type").toString());
                        propertyAmount.add(userObj.get("property_amount").toString());
                        contractType.add(userObj.get("contract_type").toString());
                        floor.add(userObj.get("floor").toString());
                        carpetArea.add(userObj.get("carpet_area").toString());
                        state.add(userObj.get("state").toString());
                        city.add(userObj.get("city").toString());
                        description.add(userObj.get("description").toString());

                        data.add(new Result(userObj.get("description").toString(), userObj.get("propertyName").toString(), userObj.get("contract_type").toString(), userObj.get("property_amount").toString(), counter));
                        counter++;

                        temp = true;
                    } else {
                        if (userObj.get("property_type").toString().toLowerCase().contains(contract.toLowerCase()) && (rentSell.toLowerCase().equals(userObj.get("contract_type").toString().toLowerCase()) || (userObj.get("contract_type").toString().toLowerCase().equals("buy")))) {
                            propertyName.add(userObj.get("propertyName").toString());
                            propertyLocation.add(userObj.get("propertyLocation").toString());
                            propertyType.add(userObj.get("property_type").toString());
                            propertyAmount.add(userObj.get("property_amount").toString());
                            contractType.add(userObj.get("contract_type").toString());
                            floor.add(userObj.get("floor").toString());
                            carpetArea.add(userObj.get("carpet_area").toString());
                            state.add(userObj.get("state").toString());
                            city.add(userObj.get("city").toString());
                            description.add(userObj.get("description").toString());

                            data.add(new Result(userObj.get("description").toString(), userObj.get("propertyName").toString(), userObj.get("contract_type").toString(), userObj.get("property_amount").toString(), counter));
                            counter++;

                            temp = true;
                        }
                    }
                }

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (propertyName.size() > 0) {
                result_text.setText("Result");

                customAdapter.notifyDataSetChanged();

                /*
                result.setText("Property Location: " + propertyLocation +
                        "\nProperty Type: " + propertyType +
                        "\nProperty Amount: " + propertyAmount +
                        "\nContract Type: " + contractType +
                        "\nFloor: " + floor +
                        "\nCarpet Area: " + carpetArea +
                        "\nCity: " + city +
                        "\nState: " + state +
                        "\n\nDescription: " + description);
                */
            } else {
                result_text.setText("Not Found");
            }

            if (temp)
                list.setVisibility(View.VISIBLE);
            else {
                list.setVisibility(View.GONE);
            }

        }
    }
}
