# CustomShopLib
In order to use the app you import `import com.example.customshop.ShopOpener;` for the `ShopOpener` object and use it to open the desiered bottom sheet like so:
```Java
PurchaseDetails details = new PurchaseDetails("Coins", 20.0, "Coins for the game");

ShopOpener.openShop(getParentFragmentManager(), details, new ShopOpener.ShopResult() {
  @Override
  public void onPurchaseSuccessful(PurchaseResult result) {
      ShopOpener.closeShop();
  }
  
  @Override
  public void onPurchaseFailed(Throwable error) {
      ShopOpener.closeShop();
  }
});
```
This will open the shop's bottom sheet over the current activity.
- `PurchaseDetails`: This object represents the details you need to pass to the `ShopOpener` in order for it to know what the user is purchasing.
- `ShopOpener.ShopResult`: This interface handles reporting back the purchase state.
  -  `onPurchaseFailed`: Means that the purchase failed and it passes the relevant error
  -  `onPurchaseSuccessful`: Means that the purchase succeded and passes the relavant data (Since this is a simple example its just a String).
- `ShopOpener.closeShop();`: This function closes the currently open shop bottom sheet. I leave this as an option for the developer in case they want to close the window if the purchase failed, or they would rather leave it open to retry.

## UI Modules
The app contains two UI modules `ShopBottomSheet` and `ShopWebViewBottomSheet`. Both are using the MVVM design pattern. Both ui modules use dependency injection and handle proccess death.

## The use of WebView
The `ShopWebViewBottomSheet` opens a locally sourced HTML file from the assets library. Using the `WebViewClient` I inject a title into the HTML that follows the `PurchaseDetails`, and using a bridge we let the WebView run a function from our app in order to recieve a callback that tells us the purchase is done.
