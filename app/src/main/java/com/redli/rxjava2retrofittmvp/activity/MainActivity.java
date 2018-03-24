package com.redli.rxjava2retrofittmvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.redli.rxjava2retrofittmvp.R;
import com.redli.rxjava2retrofittmvp.bean.HttpResult;
import com.redli.rxjava2retrofittmvp.bean.Subject;
import com.redli.rxjava2retrofittmvp.http.ApiClient;
import com.redli.rxjava2retrofittmvp.http.SubscribeHandler;
import com.redli.rxjava2retrofittmvp.http.rxjava.ProgressSubscriber;
import com.redli.rxjava2retrofittmvp.http.rxjava.SubscriberOnNextListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.text)
    TextView text;
    private SubscriberOnNextListener<List<Subject>> subscriberOnNextListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        startActivity(new Intent(this, TopMoviesActivity.class));
    }
}
