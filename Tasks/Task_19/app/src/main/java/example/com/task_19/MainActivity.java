package example.com.task_19;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

import static android.graphics.Typeface.BOLD_ITALIC;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_error_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.error(MainActivity.this, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
            }
        });
        findViewById(R.id.button_success_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.success(MainActivity.this, "Success!", Toast.LENGTH_SHORT, true).show();
            }
        });
        findViewById(R.id.button_info_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(MainActivity.this, "Here is some info for you.", Toast.LENGTH_SHORT, true).show();
            }
        });
        findViewById(R.id.button_warning_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.warning(MainActivity.this, "Beware of the dog.", Toast.LENGTH_SHORT, true).show();
            }
        });
        findViewById(R.id.button_normal_toast_wo_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.normal(MainActivity.this, "Normal toast w/o icon").show();
            }
        });
        findViewById(R.id.button_normal_toast_w_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable icon = getResources().getDrawable(R.drawable.ic_bug_report_white_48dp);
                Toasty.normal(MainActivity.this, "Normal toast w/ icon", icon).show();
            }
        });
        findViewById(R.id.button_info_toast_with_formatting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(MainActivity.this, getFormattedMessage()).show();
            }
        });
        findViewById(R.id.button_custom_config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.Config.getInstance()
                        .setTextColor(Color.GREEN)
                        .apply();
                Toasty.custom(MainActivity.this, "sudo kill -9 everyone", getResources().getDrawable(R.drawable.ic_desktop_mac_white_48dp),
                        Color.BLACK, Toast.LENGTH_SHORT, true, true).show();
                Toasty.Config.reset(); // Use this if you want to use the configuration above only once
            }
        });
    }
    private CharSequence getFormattedMessage() {
        final String prefix = "Formatted ";
        final String highlight = "bold italic";
        final String suffix = " text";
        SpannableStringBuilder ssb = new SpannableStringBuilder(prefix).append(highlight).append(suffix);
        int prefixLen = prefix.length();
        ssb.setSpan(new StyleSpan(BOLD_ITALIC),
                prefixLen, prefixLen + highlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }
}
