package android.anthor.beertimer.util;

import android.anthor.beertimer.StartActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.anthor.beertimer.R;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class IntroActivity extends ActionBarActivity {

    ImageView button;
    ImageView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        button = (ImageView) findViewById(R.id.intro_buton);
        timer = (ImageView) findViewById(R.id.intro_timer);
        ImageView base = (ImageView) findViewById(R.id.intro_base);

        Animation animation_rotate = AnimationUtils.loadAnimation(this, R.anim.intro_button_rotate);
        final Animation animation_push = AnimationUtils.loadAnimation(this, R.anim.intro_button_push);
        final Animation animation_show = AnimationUtils.loadAnimation(this, R.anim.intro_timer_show);

        animation_rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                button.startAnimation(animation_push);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation_push.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                timer.setAlpha(1.0f);
                timer.startAnimation(animation_show);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation_show.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent i = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        button.startAnimation(animation_rotate);

    }
}
