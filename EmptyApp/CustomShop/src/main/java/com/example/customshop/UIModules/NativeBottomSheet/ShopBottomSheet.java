package com.example.customshop.UIModules.NativeBottomSheet;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.customshop.Models.PurchaseDetails;
import com.example.customshop.R;
import com.example.customshop.ShopOpener;
import com.example.customshop.UIModules.Factories.BundleArgs;
import com.example.customshop.UIModules.Factories.ShopBottomSheetViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ShopBottomSheet extends BottomSheetDialogFragment {
    private ShopBottomSheetViewModel viewModel;
    private TextView titleText;
    private TextView priceText;
    private TextView descriptionText;
    private EditText nameEditText;
    private EditText cardNumberEditText;
    private Button payButton;
    private ProgressBar loader;

    public ShopBottomSheet() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            handleOnCreate();
        } else {
            throw new RuntimeException("Did not get needed parameters to create ViewModel for: " + this.getClass());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        assignViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        defineOnClickListeners();
        observeLiveData();
        setUi();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        viewModel.handleDismiss();
    }

    private void assignViews(View view) {
        titleText = view.findViewById(R.id.title_text);
        priceText = view.findViewById(R.id.price_text);
        descriptionText = view.findViewById(R.id.description_text);
        nameEditText = view.findViewById(R.id.name_edit_text);
        cardNumberEditText = view.findViewById(R.id.card_number_edit_text);
        payButton = view.findViewById(R.id.pay_button);
        loader = view.findViewById(R.id.loader);
    }

    private void defineOnClickListeners() {
        payButton.setOnClickListener(v -> {
            startLoading();
            viewModel.enactPurchase();
        });
    }

    private void setUi() {
        titleText.setText(viewModel.purchaseDetails.title);
        priceText.setText(String.valueOf(viewModel.purchaseDetails.price));
        descriptionText.setText(viewModel.purchaseDetails.description);
    }

    private void observeLiveData() {
        viewModel.purchaseSuccessLiveData().observe(this, unused -> {
            finishLoading();
        });

        viewModel.purchaseErrorLiveData().observe(this, throwable -> {
            finishLoading();
        });
    }

    private void startLoading() {
        loader.setVisibility(View.VISIBLE);
        payButton.setEnabled(false);
        nameEditText.setEnabled(false);
        cardNumberEditText.setEnabled(false);
    }

    private void finishLoading() {
        loader.setVisibility(View.GONE);
        payButton.setEnabled(true);
        nameEditText.setEnabled(true);
        cardNumberEditText.setEnabled(true);
    }

    private void handleOnCreate() {
        PurchaseDetails details = (PurchaseDetails) getArguments().getSerializable(BundleArgs.BottomSheetViewModel.PURCHASE_DETAILS_KEY);
        ShopOpener.ShopResult result = (ShopOpener.ShopResult) getArguments().getSerializable(BundleArgs.BottomSheetViewModel.SHOP_RESULT_KEY);
        ShopBottomSheetViewModelFactory viewModelFactory = new ShopBottomSheetViewModelFactory(details, result);
        viewModel =new ViewModelProvider(this, viewModelFactory).get(ShopBottomSheetViewModel.class);
    }
}