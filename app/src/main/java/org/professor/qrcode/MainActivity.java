package org.professor.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startScan;
    private TextView contentInfo;
    private EditText etBuildCode;
    private Button buildCode;
    private ImageView ivShowCode;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startScan = (Button) findViewById(R.id.btn_start_scan);
        contentInfo = (TextView) findViewById(R.id.content_info);
        etBuildCode = (EditText) findViewById(R.id.et_input_build);
        buildCode = (Button) findViewById(R.id.btn_build_code);
        ivShowCode = (ImageView) findViewById(R.id.iv_qr_code);
        checkBox = (CheckBox) findViewById(R.id.cb_logo);
        startScan.setOnClickListener(this);
        buildCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btn_start_scan:
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
                break;
            case R.id.btn_build_code:
                String input = etBuildCode.getText().toString().trim();

                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }


                Bitmap qrCode = EncodingUtils.createQRCode(input, 500, 500, checkBox.isChecked() ? BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher) : null);

                ivShowCode.setImageBitmap(qrCode);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();

            String result = bundle.getString("result");

            contentInfo.setText(result);


        }
    }
}
