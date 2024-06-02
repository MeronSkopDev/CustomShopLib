package com.example.customshop.UIModules.WebBottomSheet;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.customshop.Models.PurchaseDetails;
import com.example.customshop.Models.PurchaseResult;
import com.example.customshop.ShopOpener;
import com.example.customshop.UIModules.Factories.BundleArgs;

public class ShopWebViewBottomSheetViewModel extends ViewModel {
    public final WebViewClient webViewClient;
    public final String shopUrl = "file:///android_asset/WebShop.html";
    private String titleInjection;
    private final PurchaseDetails purchaseDetails;
    private final ShopOpener.ShopResult result;
    private MutableLiveData<Void> purchaseEndMutableLiveData = new MutableLiveData<>();
    public LiveData<Void> purchaseEndLiveData() {return purchaseEndMutableLiveData; }


    public ShopWebViewBottomSheetViewModel(SavedStateHandle savedStateHandle) {
        this.result = savedStateHandle.get(BundleArgs.BottomSheetViewModel.SHOP_RESULT_KEY);
        this.purchaseDetails = savedStateHandle.get(BundleArgs.BottomSheetViewModel.PURCHASE_DETAILS_KEY);

        if (this.result == null || this.purchaseDetails == null) {
            throw new IllegalStateException("Result and PurchaseDetails must be provided in order to instantiate: " + this.getClass());
        }

        defineJavaScriptInjections();
        webViewClient = new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                view.loadUrl(titleInjection);
            }
        };
    }

    @JavascriptInterface
    public void purchaseFinish(String name) {
        purchaseEndMutableLiveData.postValue(null);
        result.onPurchaseSuccessful(new PurchaseResult("Purchase succeeded"));
    }

    private void defineJavaScriptInjections() {
        this.titleInjection = "javascript:setTitle('" + purchaseDetails.title + "')";
    }
}
