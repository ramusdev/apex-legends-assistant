package com.rb.apexlegendsassistant;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.List;

public class DonateFragment extends Fragment implements View.OnClickListener {

    private DonateViewModel mViewModel;
    View view;

    public BillingClient billingClient;
    public SkuDetails skuDetailsLvlOne;
    public SkuDetails skuDetailsLvlTwo;
    public SkuDetails skuDetailsLvlThree;
    public SkuDetails skuDetailsLvlFour;

    public static DonateFragment newInstance() {
        return new DonateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_donate));

        view = inflater.inflate(R.layout.donate_fragment, container, false);

        // Button set click listener
        Button buttonDonateLvlOne = (Button) view.findViewById(R.id.button_donate_lvl_one);
        buttonDonateLvlOne.setOnClickListener(this);
        Button buttonDonateLvlTwo = (Button) view.findViewById(R.id.button_donate_lvl_two);
        buttonDonateLvlTwo.setOnClickListener(this);
        Button buttonDonateLvlThree = (Button) view.findViewById(R.id.button_donate_lvl_three);
        buttonDonateLvlThree.setOnClickListener(this);
        Button buttonDonateLvlFour = (Button) view.findViewById(R.id.button_donate_lvl_four);
        buttonDonateLvlFour.setOnClickListener(this);

        // if (mainActivity.isNetworkAvailable()) {
            initBillingClient();
        // }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DonateViewModel.class);
        final TextView textView = view.findViewById(R.id.main_text);

        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Spanned>() {
            @Override
            public void onChanged(Spanned s) {
                textView.setText(s);
            }
        });
        // TODO: Use the ViewModel
    }

    public void initBillingClient() {
        PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                        && list != null) {
                    for (Purchase purchase : list) {
                        handlePurchase(purchase);
                    }
                }
            }
        };

        billingClient = BillingClient.newBuilder(MyApplicationContext.getAppContext())
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d("MyTag", "Donate: connect ok billing client");

                    List<String> skuList = new ArrayList<String>();
                    skuList.add("donate_lvl_1");
                    skuList.add("donate_lvl_2");
                    skuList.add("donate_lvl_3");
                    skuList.add("donate_lvl_4");

                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);

                    billingClient.querySkuDetailsAsync(params.build(), new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                            skuDetailsLvlOne = list.get(0);
                            skuDetailsLvlTwo = list.get(1);
                            skuDetailsLvlThree = list.get(2);
                            skuDetailsLvlFour = list.get(3);
                        }
                    });
                } else {
                    Log.d("MyTag", "Donate: error connect billing client");
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Log.d("MyTag", "Donate: disconnect billing client");
            }
        });
    }

    public void handlePurchase(Purchase purchase) {
        ConsumeParams consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();

        ConsumeResponseListener consumeResponseListener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String s) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d("MyTag", "Donate: handle consume response");
                }
            }
        };

        billingClient.consumeAsync(consumeParams, consumeResponseListener);
    }

    @Override
    public void onClick(View v) {
        Log.d("MyTag", "Donate: onclick");
        v.getId();

        int responseCode;

        // if (! mainActivity.isNetworkAvailable()) {
            // Toolbar toolbar = (Toolbar) mainActivity.findViewById(R.id.toolbar);
            // FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
            // transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
            // toolbar.setTitle(getResources().getString(R.string.error_connection));
            // return;
        // }

        BillingFlowParams billingFlowParams = null;
        BillingFlowParams.Builder billingFlowParamsBuilder = BillingFlowParams.newBuilder();

        switch (view.getId()) {
            case R.id.button_donate_lvl_one:
                try {
                    billingFlowParams = billingFlowParamsBuilder.setSkuDetails(skuDetailsLvlOne).build();
                } catch (IllegalArgumentException e) {
                    // Log.e("CustomLogTag", "SKU not loaded");
                }
                break;
            case R.id.button_donate_lvl_two:
                try {
                    billingFlowParams = billingFlowParamsBuilder.setSkuDetails(skuDetailsLvlTwo).build();
                } catch (IllegalArgumentException e) {
                    // Log.e("CustomLogTag", "SKU not loaded");
                }
                break;
            case R.id.button_donate_lvl_three:
                try {
                    billingFlowParams = billingFlowParamsBuilder.setSkuDetails(skuDetailsLvlThree).build();
                } catch (IllegalArgumentException e) {
                    // Log.e("CustomLogTag", "SKU not loaded");
                }
                break;
            case R.id.button_donate_lvl_four:
                try {
                    billingFlowParams = billingFlowParamsBuilder.setSkuDetails(skuDetailsLvlFour).build();
                } catch (IllegalArgumentException e) {
                    // Log.e("CustomLogTag", "SKU not loaded");
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        try {
            responseCode = billingClient.launchBillingFlow(getActivity(), billingFlowParams).getResponseCode();
        } catch (NullPointerException e) {
            // Log.e("CustomLogTag", "SKU null pointer");
        }
    }
}
