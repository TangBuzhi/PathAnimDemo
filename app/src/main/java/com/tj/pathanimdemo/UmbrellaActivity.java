package com.tj.pathanimdemo;

import android.animation.AnimatorSet;
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
public class UmbrellaActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView camera, music, place, sleep, thought, person, plus;
    private FrameLayout anim;
    private boolean toggle = false;//用来标记动画执行结果
    private float overShotLength = 300f;//view将要移动的距离
    private static float angle /*= 360 / (6 - 1) */ = 36f;//每个view之间的夹角
    private static final long animStartTime = 100;//动画基础执行时间
    private static final long animPerTime = 20;//每个动画执行间隔
    private long animDuration;//动画执行时间
    private List<ImageView> views = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umbrella);
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
        ObjectAnimator tanslateX = ObjectAnimator.ofFloat(view, "translationX", 0, (float) (overShotLength * Math.cos(angle * Math.PI / 180 * position)));
        ObjectAnimator tanslateY = ObjectAnimator.ofFloat(view, "translationY", 0, (float) (-overShotLength * Math.sin(angle * Math.PI / 180 * position)));
        AnimatorSet set = new AnimatorSet();
        set.playTogether(tanslateX, tanslateY);
        set.setInterpolator(new OvershootInterpolator());
        set.setDuration(animDuration);
        set.setStartDelay(position * animPerTime);
        set.start();
    }

    private void showShrinkAnim(ImageView view, int position) {
        ObjectAnimator tanslateX = ObjectAnimator.ofFloat(view, "translationX", (float) (overShotLength * Math.cos(angle * Math.PI / 180 * position)), 0);
        ObjectAnimator tanslateY = ObjectAnimator.ofFloat(view, "translationY", (float) (-overShotLength * Math.sin(angle * Math.PI / 180 * position)), 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(tanslateX, tanslateY);
        set.setDuration(animStartTime);
        set.setStartDelay(position * animPerTime);
        set.start();
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
