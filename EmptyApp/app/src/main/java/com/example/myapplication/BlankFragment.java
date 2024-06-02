package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.customshop.Models.PurchaseDetails;
import com.example.customshop.Models.PurchaseResult;
import com.example.customshop.ShopOpener;

public class BlankFragment extends Fragment {
    private Button openShopButton;
    private Button openWebShopButton;
    private PurchaseDetails details = new PurchaseDetails("Coins", 20.0, "Coins for the game");

    public BlankFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        openShopButton = view.findViewById(R.id.open_shop_button);
        openWebShopButton = view.findViewById(R.id.open_web_shop_button);
        listenToButton();
        return view;
    }

    private void listenToButton() {
        openShopButton.setOnClickListener(v -> {
            openShop();
        });

        openWebShopButton.setOnClickListener(v -> {
            openWebViewShop();
        });
    }

    private void openShop() {
        ShopOpener.openShop(getParentFragmentManager(), details, new ShopOpener.ShopResult() {
            @Override
            public void onPurchaseSuccessful(PurchaseResult result) {
                System.out.println(result.resultString);
                ShopOpener.closeShop();
            }

            @Override
            public void onPurchaseFailed(Throwable error) {
                System.out.println(error);
                ShopOpener.closeShop();
            }
        });
    }

    private void openWebViewShop() {
        ShopOpener.openWebViewShop(getParentFragmentManager(), details, new ShopOpener.ShopResult() {
            @Override
            public void onPurchaseSuccessful(PurchaseResult result) {
                System.out.println(result.resultString);
                ShopOpener.closeShop();
            }

            @Override
            public void onPurchaseFailed(Throwable error) {
                System.out.println(error);
                ShopOpener.closeShop();
            }
        });
    }
}