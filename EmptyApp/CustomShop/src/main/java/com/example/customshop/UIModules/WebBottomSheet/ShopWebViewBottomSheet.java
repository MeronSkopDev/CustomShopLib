package com.example.customshop.UIModules.WebBottomSheet;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.customshop.Models.PurchaseDetails;
import com.example.customshop.R;
import com.example.customshop.ShopOpener;
import com.example.customshop.UIModules.Factories.BundleArgs;
import com.example.customshop.UIModules.Factories.ShopBottomSheetViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ShopWebViewBottomSheet extends BottomSheetDialogFragment {
    private ShopWebViewBottomSheetViewModel viewModel;
    private WebView shopWebView;

    public ShopWebViewBottomSheet() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        View view = inflater.inflate(R.layout.fragment_shop_web_view_bottom_sheet, container, false);
        assignViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWebView();
    }

    private void assignViews(View view) {
        shopWebView = view.findViewById(R.id.shop_web_view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        shopWebView.setWebViewClient(viewModel.webViewClient);
        shopWebView.loadUrl(viewModel.shopUrl);
        shopWebView.addJavascriptInterface(viewModel,"PurchaseInterface");
        shopWebView.getSettings().setJavaScriptEnabled(true);
    }

    private void observeLiveData() {
       viewModel.purchaseEndLiveData().observe(this, unused -> {
            //If we needed to react to actions from the WebView we could do it here.
       });
    }

    private void handleOnCreate() {
        PurchaseDetails details = (PurchaseDetails) getArguments().getSerializable(BundleArgs.BottomSheetViewModel.PURCHASE_DETAILS_KEY);
        ShopOpener.ShopResult result = (ShopOpener.ShopResult) getArguments().getSerializable(BundleArgs.BottomSheetViewModel.SHOP_RESULT_KEY);
        ShopBottomSheetViewModelFactory viewModelFactory = new ShopBottomSheetViewModelFactory(details, result);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ShopWebViewBottomSheetViewModel.class);
    }
}