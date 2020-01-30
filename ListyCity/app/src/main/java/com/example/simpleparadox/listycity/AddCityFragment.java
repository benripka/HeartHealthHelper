package com.example.simpleparadox.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.simpleparadox.listycity.City;
import com.example.simpleparadox.listycity.R;

public class AddCityFragment extends DialogFragment {
    private EditText cityName;
    private EditText provinceName;
    private OnFragmentInteractionListener listener;
    private City currentCity;

    public interface OnFragmentInteractionListener {
        void onAddCityPressed(City newCity);
        void onEditCityPresser(City oldCity, City newCity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_city_fragment_layout, null);
        cityName = view.findViewById(R.id.city_field);
        provinceName = view.findViewById(R.id.province_field);

        Bundle bundle = this.getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if(bundle.containsKey("current_city")) {
            currentCity = (City) bundle.getSerializable("current_city");
            cityName.setText(currentCity.getCityName());
            provinceName.setText(currentCity.getProvinceName());
            return builder
                    .setView(view)
                    .setTitle("Add/Edit City")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String city = cityName.getText().toString().trim();
                            String province = provinceName.getText().toString().trim();
                            listener.onEditCityPresser(currentCity, new City(city, province));
                        }
                    }).create();

        } else {
            currentCity = new City("", "");
            return builder
                    .setView(view)
                    .setTitle("Add/Edit City")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String city = cityName.getText().toString().trim();
                            String province = provinceName.getText().toString().trim();
                            listener.onAddCityPressed(new City(city, province));
                        }
                    }).create();
        }
    }
}
