Here is the corrected text:

# CustomShopLib
To use the app, import `com.example.customshop.ShopOpener` for the `ShopOpener` object and use it to open the desired bottom sheet like so:
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
- `PurchaseDetails`: This object represents the details you need to pass to the `ShopOpener` so it knows what the user is purchasing.
- `ShopOpener.ShopResult`: This interface handles reporting back the purchase state.
  -  `onPurchaseFailed`: Indicates that the purchase failed and passes the relevant error.
  -  `onPurchaseSuccessful`: Indicates that the purchase succeeded and passes the relevant data (Since this is a simple example, it's just a String).
- `ShopOpener.closeShop()`: This function closes the currently open shop bottom sheet. This is left as an option for the developer in case they want to close the window if the purchase failed, or they would rather leave it open to retry.

## UI Modules
The app contains two UI modules: `ShopBottomSheet` and `ShopWebViewBottomSheet`. Both use the MVVM design pattern. Both UI modules use dependency injection and handle process death.

## The Use of WebView
The `ShopWebViewBottomSheet` opens a locally sourced HTML file from the assets library. Using the `WebViewClient`, I inject a title into the HTML that follows the `PurchaseDetails`, and using a bridge, we let the WebView run a function from our app to receive a callback that tells us the purchase is done.
