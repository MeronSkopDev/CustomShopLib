package com.example.customshop.UIModules.NativeBottomSheet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.customshop.Models.PurchaseDetails;
import com.example.customshop.Models.PurchaseResult;
import com.example.customshop.Models.RickAndMortyCharacter;
import com.example.customshop.PaymentsRemoteAPI;
import com.example.customshop.ShopOpener;
import com.example.customshop.UIModules.Factories.BundleArgs;

public class ShopBottomSheetViewModel extends ViewModel {
    private final PaymentsRemoteAPI networkService = new PaymentsRemoteAPI();
    private final ShopOpener.ShopResult shopResult;
    public final PurchaseDetails purchaseDetails;
    private final MutableLiveData<Void> purchaseSuccessMutableLiveData = new MutableLiveData<>();
    public LiveData<Void> purchaseSuccessLiveData() { return purchaseSuccessMutableLiveData; }
    private final MutableLiveData<Throwable> purchaseErrorMutableData = new MutableLiveData<>();
    public LiveData<Throwable> purchaseErrorLiveData() { return purchaseErrorMutableData; }

    public ShopBottomSheetViewModel(SavedStateHandle savedStateHandle) {
        this.shopResult = savedStateHandle.get(BundleArgs.BottomSheetViewModel.SHOP_RESULT_KEY);
        this.purchaseDetails = savedStateHandle.get(BundleArgs.BottomSheetViewModel.PURCHASE_DETAILS_KEY);

        if (this.shopResult == null || this.purchaseDetails == null) {
            throw new IllegalStateException("Result and PurchaseDetails must be provided in order to instantiate: " + this.getClass());
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        handleDismiss();
    }

    public void enactPurchase() {
        networkService.fetchCharacter(new PaymentsRemoteAPI.NetworkCallResult<RickAndMortyCharacter>() {
            @Override
            public void callSuccess(RickAndMortyCharacter rickAndMortyCharacter) {
                purchaseSuccessMutableLiveData.postValue(null);
                shopResult.onPurchaseSuccessful(new PurchaseResult("Purchase succeeded"));
            }

            @Override
            public void callFailure(Throwable error) {
                purchaseErrorMutableData.postValue(error);
                shopResult.onPurchaseFailed(error);
            }
        });
    }

    public void handleDismiss() {
        networkService.cancelActiveCharacterCall();
    }
}
