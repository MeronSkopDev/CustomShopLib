package com.example.customshop.UIModules.Factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.customshop.Models.PurchaseDetails;
import com.example.customshop.ShopOpener;
import com.example.customshop.UIModules.NativeBottomSheet.ShopBottomSheetViewModel;
import com.example.customshop.UIModules.WebBottomSheet.ShopWebViewBottomSheetViewModel;

public class ShopBottomSheetViewModelFactory implements ViewModelProvider.Factory {
    private final PurchaseDetails purchaseDetails;
    private final ShopOpener.ShopResult result;

    public ShopBottomSheetViewModelFactory(PurchaseDetails purchaseDetails, ShopOpener.ShopResult result) {
        this.purchaseDetails = purchaseDetails;
        this.result = result;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        SavedStateHandle state = new SavedStateHandle();
        state.set(BundleArgs.BottomSheetViewModel.SHOP_RESULT_KEY, result);
        state.set(BundleArgs.BottomSheetViewModel.PURCHASE_DETAILS_KEY, purchaseDetails);

        if (modelClass.isAssignableFrom(ShopWebViewBottomSheetViewModel.class)) {
            return modelClass.cast(new ShopWebViewBottomSheetViewModel(state));
        } else if (modelClass.isAssignableFrom(ShopBottomSheetViewModel.class)) {
            return modelClass.cast(new ShopBottomSheetViewModel(state));
        }
        throw new IllegalArgumentException("Unknown ViewModel class in: " + this.getClass());
    }
}
