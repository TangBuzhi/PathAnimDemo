package com.tj.pathanimdemo;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Don on 2016/11/29 14:00.
 * Describe：${TODO}
 * Modified：${TODO}
 */
public class FoldActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView camera, music, place, sleep, thought, person, plus;
    private FrameLayout anim;
    private boolean toggle = false;//用来标记动画执行结果
    private static final long animStartTime = 100;//动画基础执行时间
    private static final long animPerTime = 20;//每个动画执行间隔
    private long animDuration;//动画执行时间
    private List<ImageView> views = new ArrayList<>();
    private int margin = 5;
    private int viewHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold);
        initView();
        initListener();
    }

    private void initView() {
        camera = (ImageView) findViewById(R.id.camera);
        music = (ImageView) findViewById(R.id.music);
        place = (ImageView) findViewById(R.id.place);
        sleep = (ImageView) findViewById(R.id.sleep);
        thought = (ImageView) findViewById(R.id.thought);
        person = (ImageView) findViewById(R.id.person);
        plus = (ImageView) findViewById(R.id.plus);
        anim = (FrameLayout) findViewById(R.id.anim);

        camera.post(new Runnable() {
            @Override
            public void run() {
                viewHeight = camera.getHeight();
                System.out.println("viewHeight=" + viewHeight);
            }
        });

        views.add(camera);
        views.add(music);
        views.add(place);
        views.add(sleep);
        views.add(thought);
        views.add(person);
    }

    private void initListener() {
        camera.setOnClickListener(this);
        music.setOnClickListener(this);
        place.setOnClickListener(this);
        sleep.setOnClickListener(this);
        thought.setOnClickListener(this);
        person.setOnClickListener(this);
        anim.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera:
                ToastUtil.showToast(this, "camera");
                break;
            case R.id.music:
                ToastUtil.showToast(this, "music");
                break;
            case R.id.place:
                ToastUtil.showToast(this, "place");
                break;
            case R.id.sleep:
                ToastUtil.showToast(this, "sleep");
                break;
            case R.id.thought:
                ToastUtil.showToast(this, "thought");
                break;
            case R.id.person:
                ToastUtil.showToast(this, "person");
                break;
            case R.id.anim:
            default:
                break;
        }
        showAnim();
    }

    private void showAnim() {
        int size = views.size();
        if (!toggle) {
            toggle = true;
            for (int i = 0; i < size; i++) {
                animDuration = animStartTime * 2 - i * animPerTime;
                showExpandAnim(views.get(i), i, animDuration);
            }
        } else {
            toggle = false;
            for (int i = 0; i < size; i++) {
                showShrinkAnim(views.get(i), i);
            }
        }
        showRotateAnimation(toggle);
    }

    private void showExpandAnim(ImageView view, int position, long animDuration) {
        ObjectAnimator tanslateY = ObjectAnimator.ofFloat(view, "translationY", 0, -(position + 1) * (margin + viewHeight));
        tanslateY.setInterpolator(new OvershootInterpolator());
        tanslateY.setDuration(animDuration);
        tanslateY.setStartDelay(position * animPerTime);
        tanslateY.start();
    }

    private void showShrinkAnim(ImageView view, int position) {
        ObjectAnimator tanslateY = ObjectAnimator.ofFloat(view, "translationY", -(position + 1) * (margin + viewHeight), 0);
        tanslateY.setDuration(animStartTime);
        tanslateY.setStartDelay(position * animPerTime);
        tanslateY.start();
    }

    public void showRotateAnimation(boolean toggle) {
        float centerX = plus.getWidth() / 2.0f;
        float centerY = plus.getHeight() / 2.0f;
        ObjectAnimator rotation;
        if (!toggle) {
            rotation = ObjectAnimator.ofFloat(plus, "rotation", 135, 0);
        } else {
            rotation = ObjectAnimator.ofFloat(plus, "rotation", 0, 135);
        }
        plus.setPivotX(centerX);
        plus.setPivotY(centerY);
        rotation.setDuration(animStartTime);
        rotation.start();
    }
}
