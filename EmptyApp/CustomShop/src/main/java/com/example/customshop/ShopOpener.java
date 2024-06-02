package com.example.customshop;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import com.example.customshop.Models.PurchaseDetails;
import com.example.customshop.Models.PurchaseResult;
import com.example.customshop.UIModules.Factories.BundleArgs;
import com.example.customshop.UIModules.NativeBottomSheet.ShopBottomSheet;
import com.example.customshop.UIModules.WebBottomSheet.ShopWebViewBottomSheet;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.Serializable;

public class ShopOpener {
    public interface ShopResult extends Serializable {
        void onPurchaseSuccessful(PurchaseResult result);
        void onPurchaseFailed(Throwable error);
    }

    private static BottomSheetDialogFragment currentOpenBottomSheet;

    public static void closeShop() {
        if(currentOpenBottomSheet != null) {
            currentOpenBottomSheet.dismiss();
            currentOpenBottomSheet = null;
        }
    }

    static public void openShop(FragmentManager manager, PurchaseDetails details, ShopResult result) {
        ShopBottomSheet bottomSheet = new ShopBottomSheet();
        Bundle args = new Bundle();
        args.putSerializable(BundleArgs.BottomSheetViewModel.PURCHASE_DETAILS_KEY, details);
        args.putSerializable(BundleArgs.BottomSheetViewModel.SHOP_RESULT_KEY, result);
        bottomSheet.setArguments(args);
        bottomSheet.show(manager, null);
        currentOpenBottomSheet = bottomSheet;
    }

    static public void openWebViewShop(FragmentManager manager, PurchaseDetails details, ShopResult result) {
        ShopWebViewBottomSheet bottomSheet = new ShopWebViewBottomSheet();
        Bundle args = new Bundle();
        args.putSerializable(BundleArgs.BottomSheetViewModel.PURCHASE_DETAILS_KEY, details);
        args.putSerializable(BundleArgs.BottomSheetViewModel.SHOP_RESULT_KEY, result);
        bottomSheet.setArguments(args);
        bottomSheet.show(manager, null);
        currentOpenBottomSheet = bottomSheet;
    }
}
