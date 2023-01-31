package com.ms.dob.directchat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.hbb20.CountryCodePicker;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView iv_clear_text;
    private CountryCodePicker code_picker;
    private EditText edit_msg;
    private EditText edit_mobile_number;
    private Button btn_send_msg;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(
                this,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(
                            @NonNull InitializationStatus initializationStatus) {
                    }
                });
        new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("DA0479AEE77C97740BBEAF1644E8EEED"));
        LinearLayout ly_adcontainer_main = findViewById(R.id.ly_adcontainer_main);
        LinearLayout ly_adcontainer_main_bottom = findViewById(R.id.ly_adcontainer_main_bottom);
        View ad_default_layout_top = findViewById(R.id.ad_default_layout_top);
        View ad_default_layout_bottom = findViewById(R.id.ad_default_layout_bottom);
        this.code_picker = (CountryCodePicker) findViewById(R.id.code_picker);
        this.edit_mobile_number = (EditText) findViewById(R.id.edit_mobile_number);
        this.edit_msg = (EditText) findViewById(R.id.edit_msg);
        this.btn_send_msg = (Button) findViewById(R.id.btn_send_msg);
        this.iv_clear_text = (ImageView) findViewById(R.id.iv_clear_text);
        this.code_picker.registerCarrierNumberEditText(this.edit_mobile_number);
        this.code_picker.showFlag(true);
        this.btn_send_msg.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.sendMessage();
            }
        });
        this.iv_clear_text.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.edit_msg.getText().clear();
            }
        });
//        ((Button) findViewById(R.id.sendMedia)).setOnClickListener(new OnClickListener() {
//            public void onClick(View view) {
//                new AlertDialog.Builder(MainActivity.this, R.style.MyDialogTheme).setTitle("Great..").setMessage("Now attach media from WhatsApp app's attach button.").setNegativeButton("Okay", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        MainActivity.this.sendMessage();
//                    }
//                }).create().show();
//            }
//        });

        loadBanner(ly_adcontainer_main, ad_default_layout_top,ly_adcontainer_main_bottom,ad_default_layout_bottom);

    }

    private void loadBanner(LinearLayout adcontainer, View ad_default_layout,LinearLayout ly_adcontainer_main_bottom,View ad_default_layout_bottom) {
        AdView adView = new AdView(MainActivity.this);
        adView.setAdSize(AdSize.LARGE_BANNER);
        adView.setAdUnitId(getResources().getString(R.string.banner_id_1));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                adcontainer.removeAllViews();
                adcontainer.addView(adView);
                ad_default_layout.setVisibility(View.INVISIBLE);
                loadBannerSmall(ly_adcontainer_main_bottom, ad_default_layout_bottom);

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                ad_default_layout.setVisibility(View.INVISIBLE);
                Log.e(TAG, "onAdFailedToLoad:1 "+adError.getMessage() );
                loadBannerSmall(ly_adcontainer_main_bottom, ad_default_layout_bottom);


                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


    }
    private void loadBannerSmall(LinearLayout adcontainer, View ad_default_layout) {
        AdView adView = new AdView(MainActivity.this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getResources().getString(R.string.banner_id_2));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                adcontainer.removeAllViews();
                adcontainer.addView(adView);
                ad_default_layout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                ad_default_layout.setVisibility(View.INVISIBLE);
                Log.e(TAG, "onAdFailedToLoad:2 "+adError.getMessage() );



                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


    }

    public void sendMessage() {
        try {
            if (TextUtils.isEmpty(this.edit_mobile_number.getText())) {
                this.edit_mobile_number.setError("Please enter number");
                return;
            }
            String fullNumber = this.code_picker.getFullNumber();
            String obj = this.edit_msg.getText().toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("whatsapp://send/?text=");
            stringBuilder.append(URLEncoder.encode(obj, "UTF-8"));
            stringBuilder.append("&phone=");
            stringBuilder.append(fullNumber);
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
            intent.setFlags(268435456);
            startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(this, "Please install latest update for WhatsApp to begin.", 1).show();
        } catch (UnsupportedEncodingException unused2) {
            Toast.makeText(this, "Error while encoding your text message", 1).show();
        }
    }

//    public void checkTime() {
//        if (!this.pref.getBoolean("activity_executed", false)) {
//            AlertDialog create = new AlertDialog.Builder(this, R.style.MyDialogTheme).setTitle("Terms & Conditions").setMessage("This App or developer is not associate with WhatsApp by anyway.\nThis app is made for personal use only.\nBy pressing Continue you have accepted our terms & privacy policy.").setNegativeButton("Continue", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    Editor edit = MainActivity.this.pref.edit();
//                    edit.putBoolean("activity_executed", true);
//                    edit.apply();
//                    edit.commit();
//                    dialogInterface.dismiss();
//                    MainActivity.this.startActivity(new Intent(MainActivity.this, HelpActivity.class));
//                }
//            }).setNeutralButton("Terms & Privacy Policy", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://syscron.in/directchat_privacy_policy.html")).setFlags(268435456));
//                }
//            }).create();
//            create.setCancelable(false);
//            create.show();
//        }
//    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

   /* public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        Intent intent;
        if (itemId != R.id.Share_App) {
            switch (itemId) {
                case R.id.menu_about *//*2131230896*//*:
                    startActivity(new Intent(this, AboutActivity.class));
                    break;
                case R.id.menu_fblike *//*2131230897*//*:
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/directchat")).setFlags(268435456));
                    break;
                case R.id.menu_help *//*2131230898*//*:
                    startActivity(new Intent(this, HelpActivity.class));
                    break;
                case R.id.menu_rate *//*2131230899*//*:
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=cycloneworld.directchat")));
                    break;
                case R.id.menu_settings *//*2131230900*//*:
                    intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.addFlags(268435456);
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(intent);
                    break;
                case R.id.menu_terms *//*2131230901*//*:
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://syscron.in/directchat_privacy_policy.html")));
                    break;
            }
        }
        intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", "Hey check out this small app to send WhatsApp without saving contacts. \nSend WhatsApp messages and media to any number without saving contact. \nDownload it from playstore :\n https://goo.gl/PK5XyK");
        intent.setType("text/plain");
        startActivity(intent);
        return true;
    }*/

    /* Access modifiers changed, original: protected */
    public void onPause() {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.edit_mobile_number.getWindowToken(), 0);
        super.onPause();
    }

    /* Access modifiers changed, original: protected */
//    public void onRestart() {
//        super.onRestart();
//        if (!this.sentAction) {
//            return;
//        }
//        AlertDialog create;
//        if (!this.pref.getBoolean("rating_executed", false)) {
//            create = new AlertDialog.Builder(this, R.style.MyDialogTheme).setTitle("Rate us").setMessage("Like the experience?\nPlease rate us on playstore.\n'Your ratings & reviews encourages us to add new features'.").setPositiveButton("Rate Now", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=cycloneworld.directchat")));
//                    Editor edit = MainActivity.this.pref.edit();
//                    edit.putBoolean("rating_executed", true);
//                    edit.apply();
//                    edit.commit();
//                    dialogInterface.dismiss();
//                }
//            }).setNegativeButton("Not now", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                }
//            }).setNeutralButton("Never ask", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    Editor edit = MainActivity.this.pref.edit();
//                    edit.putBoolean("rating_executed", true);
//                    edit.apply();
//                    edit.commit();
//                    dialogInterface.dismiss();
//                }
//            }).create();
//            create.setCancelable(false);
//            create.show();
//        } else if (!this.pref.getBoolean("liking_executed", false)) {
//            create = new AlertDialog.Builder(this, R.style.MyDialogTheme).setTitle("Connect with us").setMessage("Please like our facebook page to get all updated and to connect with us for any further help").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/directchat")).setFlags(268435456));
//                    Editor edit = MainActivity.this.pref.edit();
//                    edit.putBoolean("liking_executed", true);
//                    edit.apply();
//                    edit.commit();
//                    dialogInterface.dismiss();
//                }
//            }).setNegativeButton("Not now", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                }
//            }).setNeutralButton("Never", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    Editor edit = MainActivity.this.pref.edit();
//                    edit.putBoolean("liking_executed", true);
//                    edit.apply();
//                    edit.commit();
//                    dialogInterface.dismiss();
//                }
//            }).create();
//            create.setCancelable(false);
//            create.show();
//        }
//    }
}
