package north.helper;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class main_fragment extends Fragment {

    private final String IT_TABLE = "7554fdb37266672a4b6a87d077cc069e";
    private final String LIFE_TABLE = "2d63d7f2fa9abc1eff20b5881f5936e9";
    private final String STUDY_TABLE = "88127ee4c3b9337391461a92dba9f494";

    public static HashMap<String, ArrayList<String[]>> all_questions = new HashMap<>();

    private LayoutInflater inflater = null;
    private ViewGroup container = null;
    private View view = null;

    private ArrayList<View> zones = new ArrayList<>();
    private ArrayList<String> title;

    public static boolean it_swi = false;
    public static boolean life_swi = false;
    public static boolean study_swi = false;

    public main_fragment() {
        new get_tables().start();
        this.set_title();
    }


    private void set_title() {
        title = new ArrayList<>();
        title.add("铁道信号");
        title.add("机车车辆");
        title.add("铁道工程");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.ini(inflater, container);
        while (true) {
            if (!all_questions.isEmpty()) {
                this.set_view_pager();
                return this.view;
            }
        }
    }


    private void ini(LayoutInflater inflater, ViewGroup container) {
        this.inflater = inflater;
        this.container = container;
        view = inflater.inflate(R.layout.main_fragment, container, false);
    }


    private void set_view_pager() {

        this.get_refresh_view(this.all_questions.get("IT"), "IT");
        this.get_refresh_view(this.all_questions.get("Life"), "Life");
        this.get_refresh_view(this.all_questions.get("Study"), "Study");

        ViewPager view_pager = this.view.findViewById(R.id.view_pager);
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

    }// set_view_pager

    private void get_refresh_view(ArrayList<String[]> rows, String tag) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.question, null);
        final SwipeRefreshLayout refresh_view = layout.findViewById(R.id.refresh_view);

        final RecyclerView recycle_view = layout.findViewById(R.id.recycle_view);
        recycle_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        final recycle_view_adapter adapter = new recycle_view_adapter(rows);
        recycle_view.setAdapter(adapter);


        refresh_view.setTag(tag);
        refresh_view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (refresh_view.getTag().toString()) {
                    case "IT":
                        new update_table(IT_TABLE, "IT", adapter, refresh_view).execute();
                        break;
                    case "Life":
                        new update_table(LIFE_TABLE, "Life", adapter, refresh_view).execute();
                        break;
                    case "Study":
                        new update_table(STUDY_TABLE, "Study", adapter, refresh_view).execute();
                        break;
                }

            }
        });
        this.zones.add(layout);
    }


    class recycle_view_adapter extends RecyclerView.Adapter<question_item> {
        private ArrayList<String[]> rows;

        public recycle_view_adapter(ArrayList<String[]> rows) {
            super();
            this.rows = rows;
        }

        @Override
        public question_item onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new question_item(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(question_item item, int i) {
            if (it_swi) {
                this.rows = all_questions.get("IT");
                it_swi = false;
            } else if (life_swi) {
                this.rows = all_questions.get("Life");
                life_swi = false;
            } else if (study_swi) {
                this.rows = all_questions.get("Study");
                study_swi = false;
            }
            item.bind(rows.get(i));
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }// recycle_view_adapter


    class question_item extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nickname = null;
        private TextView date = null;
        private TextView title = null;
        private TextView contents = null;

        private String[] msg;

        public question_item(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.question_item, viewGroup, false));
            itemView.setOnClickListener(this);

            nickname = itemView.findViewById(R.id.item_id);
            date = itemView.findViewById(R.id.item_date);
            title = itemView.findViewById(R.id.item_title);
            contents = itemView.findViewById(R.id.item_contents);
        }

        public void bind(String[] row) {
            this.msg=row;
            nickname.setText(row[0]);
            title.setText(row[1]);
            contents.setText("          " + row[2]);
            date.setText(row[3]);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity().getApplicationContext(), problem_detail.class);
            i.putExtra("msg", this.msg);
            startActivity(i);
        }

    }// question_item


}// main_fragment
