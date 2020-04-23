package north.helper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class user_fragment extends Fragment implements login_callback {

    private LayoutInflater inflater = null;
    private ViewGroup container = null;
    private View view = null;

    private EditText id;
    private EditText pwd;

    private login_UI_change ui_change;
    private ArrayList<View> zones = new ArrayList<>();

    private ImageView game;

    public user_fragment(login_UI_change ui_change) {
        super();
        this.ui_change = ui_change;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;

        this.ini(inflater, container);
        this.set_view_pager();
        return this.view;

    }

    private void set_view_pager() {

        ImageView iv1 = (ImageView) inflater.inflate(R.layout.ad, null);
        iv1.setImageResource(R.mipmap.c);
        ImageView iv2 = (ImageView) inflater.inflate(R.layout.ad, null);
        iv2.setImageResource(R.mipmap.t);
        ImageView iv3 = (ImageView) inflater.inflate(R.layout.ad, null);
        iv3.setImageResource(R.mipmap.b);
        zones.add(iv1);
        zones.add(iv2);
        zones.add(iv3);

        ViewPager view_pager = this.view.findViewById(R.id.ads);
        view_pager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                if (zones.get(position) != null) {
                    ViewGroup parentViewGroup = (ViewGroup) zones.get(position).getParent();
                    if (parentViewGroup != null) {
                        parentViewGroup.removeView(zones.get(position));
                    }
                }
                container.addView(zones.get(position));
                return zones.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(zones.get(position));

            }

            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

        });

    }// set_view_pager

    private void ini(LayoutInflater inflater, ViewGroup container) {
        this.inflater = inflater;
        this.container = container;
        view = inflater.inflate(R.layout.user_fragment, container, false);

        id = view.findViewById(R.id.et_id);
        pwd = view.findViewById(R.id.et_pwd);
        game = view.findViewById(R.id.game_launcher);

        game.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(getActivity(), game_activity.class));
                return false;
            }
        });

        view.findViewById(R.id.new_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), register.class));
            }
        });

        view.findViewById(R.id.forgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), forgot.class));
            }
        });

        view.findViewById(R.id.b_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.length()!=0&&pwd.length()!=0){
                    String[] account = new String[2];
                    account[0] = id.getText().toString().trim();
                    account[1] = pwd.getText().toString().trim();
                    new verify_account(account, user_fragment.this).start();
                }
                else {
                    Toast.makeText(getActivity(), "请填写完整信息！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void on_no_user() {
        Looper.prepare();
        Toast.makeText(getActivity(), "没有此账号！", Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    @Override
    public void on_wrong_pwd() {
        Looper.prepare();
        Toast.makeText(getActivity(), "密码错误！", Toast.LENGTH_SHORT).show();
        Looper.loop();

    }

    @Override
    public void on_success(ArrayList<String> fri, String login_id) {
        this.ui_change.change(fri, login_id);
    }
}
