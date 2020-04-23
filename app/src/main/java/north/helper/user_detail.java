package north.helper;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import north.other.MessageBox;

import static north.helper.main_activity.con_list;
import static north.helper.main_activity.friends_list;
import static north.helper.main_activity.in_mb;
import static north.helper.main_activity.myques_list;


public class user_detail extends Fragment {

    private LayoutInflater inflater = null;
    private ViewGroup container = null;
    private View view = null;

    private ArrayList<String> title;
    private ArrayList<View> zones = new ArrayList<>();

    public static conversation_view_adapter con_adapter;
    public friends_view_adapter fri_adapter;
    public myques_adapter mq_adapter;

    private Handler handler;
    private Handler mh;

    public user_detail() {
        this.set_title();
        for (int i = 0; i < 30; i++) {
            myques_list.add(String.valueOf(i));
        }


    }


    private void ini_h() {

        this.handler = new Handler() {

            @Override
            public void handleMessage(Message m) {
                super.handleMessage(m);

                MediaPlayer mMediaPlayer = MediaPlayer.create(getContext(), R.raw.new_msg_sound);
                mMediaPlayer.start();

                switch (m.what) {
                    case 1:
                        MessageBox msg = (MessageBox) m.obj;
                        if (!con_list.contains(msg.sender)) {
                            con_list.add(msg.sender);
                            con_adapter.notifyDataSetChanged();


                        } else {
                            int p = con_list.indexOf(msg.sender);
                            LinearLayout l = (LinearLayout) zones.get(0);
                            RecyclerView rv = l.findViewById(R.id.u_d_r);
                            LinearLayout ll = (LinearLayout) rv.getChildAt(p);
                            ImageView iv = ll.findViewById(R.id.ic_new_msg);
                            iv.setVisibility(View.VISIBLE);

                        }

                }
            }
        };

        new receive_msg(handler).start();

    }


    private void set_title() {
        title = new ArrayList<>();
        title.add("聊天");
        title.add("好友");
        title.add("我的求助");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.ini(inflater, container);

        this.set_view_pager();
        this.get_conversation();
        this.get_friends();
        this.get_my_ques();

        ini_h();

        return this.view;

    }

    private void ini(LayoutInflater inflater, ViewGroup container) {
        this.inflater = inflater;
        this.container = container;
        this.view = inflater.inflate(R.layout.user_detail, container, false);

    }


    private void set_view_pager() {
        ViewPager view_pager = this.view.findViewById(R.id.user_view_pager);
        view_pager.setAdapter(new PagerAdapter() {
            @Override
            public CharSequence getPageTitle(int position) {
                return title.get(position);
            }

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

    }

    private void get_conversation() {
        LinearLayout con = (LinearLayout) inflater.inflate(R.layout.user_detail_frag, null);
        RecyclerView con_rv = con.findViewById(R.id.u_d_r);
        con_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        con_adapter = new conversation_view_adapter();
        con_rv.setAdapter(con_adapter);

        zones.add(con);

    }

    private void get_friends() {
        LinearLayout f = (LinearLayout) inflater.inflate(R.layout.user_detail_frag, null);
        RecyclerView f_rv = f.findViewById(R.id.u_d_r);
        f_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        fri_adapter = new friends_view_adapter();
        f_rv.setAdapter(fri_adapter);

        zones.add(f);
    }

    private void get_my_ques() {
        LinearLayout q = (LinearLayout) inflater.inflate(R.layout.user_detail_frag, null);
        RecyclerView q_rv = q.findViewById(R.id.u_d_r);
        q_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mq_adapter = new myques_adapter();
        q_rv.setAdapter(mq_adapter);

        zones.add(q);
    }


    class conversation_view_adapter extends RecyclerView.Adapter<conversation_item> {

        public conversation_view_adapter() {
            super();
        }

        @Override
        public conversation_item onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new conversation_item(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(conversation_item item, int i) {
            item.bind(con_list.get(i));
        }

        @Override
        public int getItemCount() {
            return con_list.size();
        }
    }


    class conversation_item extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView con;

        public conversation_item(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.conversation_item, viewGroup, false));
            itemView.setOnClickListener(this);
            con = itemView.findViewById(R.id.con_id);

        }


        public void bind(String con_id) {
            con.setText(con_id);
        }


        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), talk_activity.class);
            i.putExtra("id", con.getText().toString());
            startActivity(i);

            LinearLayout temp = (LinearLayout) v;
            ImageView iv = temp.findViewById(R.id.ic_new_msg);
            iv.setVisibility(View.INVISIBLE);
        }
    }
    //conversation


    class friends_view_adapter extends RecyclerView.Adapter<firend_item> {

        public friends_view_adapter() {
            super();
        }

        @Override
        public firend_item onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new firend_item(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(firend_item item, int i) {
            item.bind(friends_list.get(i));
        }

        @Override
        public int getItemCount() {
            return friends_list.size();
        }
    }


    class firend_item extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView fri_id;

        public firend_item(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.friends_item, viewGroup, false));
            itemView.setOnClickListener(this);
            fri_id = itemView.findViewById(R.id.fri_id);

        }

        public void bind(String id) {
            fri_id.setText(id);
        }


        @Override
        public void onClick(View v) {
            String temp = fri_id.getText().toString();
            if (!con_list.contains(temp)) {
                con_list.add(temp);
                con_adapter.notifyDataSetChanged();
            } else {
                //start talk_activity
            }
        }

    }
    //friends

    class myques_adapter extends RecyclerView.Adapter<myques_item> {

        public myques_adapter() {
            super();
        }

        @Override
        public myques_item onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new myques_item(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(myques_item item, int i) {
            //item.bind(list.get(i));
        }

        @Override
        public int getItemCount() {
            return myques_list.size();
        }
    }


    class myques_item extends RecyclerView.ViewHolder implements View.OnClickListener {

        public myques_item(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.myques_item, viewGroup, false));
            itemView.setOnClickListener(this);

        }

        public void bind() {

        }


        @Override
        public void onClick(View v) {

        }

    }


}
