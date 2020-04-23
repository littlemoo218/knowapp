package north.helper;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class update_table extends AsyncTask {

    private final String host = "49.232.138.247";
    private final int sql_port = 9992;
    private Inet4Address ip = null;
    private InetSocketAddress sql_add = null;
    private Socket sql_socket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    private String type = "";
    private String tag = "";

    private main_fragment.recycle_view_adapter adapter = null;
    private SwipeRefreshLayout rv = null;

    public update_table(String type, String tag, main_fragment.recycle_view_adapter adapter, SwipeRefreshLayout rv) {
        super();
        this.type = type;
        this.tag = tag;
        this.adapter = adapter;
        this.rv = rv;

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        this.initialize_network();
        get_sqldate(type);
        return null;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        adapter.notifyDataSetChanged();
        rv.setRefreshing(false);

    }


    private void initialize_network() {
        try {
            this.ip = (Inet4Address) Inet4Address.getByName(this.host);
            this.sql_add = new InetSocketAddress(this.ip, this.sql_port);
            this.sql_socket = new Socket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void get_sqldate(String type) {
        try {
            this.sql_socket.connect(sql_add);
            this.out = new ObjectOutputStream(this.sql_socket.getOutputStream());
            this.out.flush();
            this.in = new ObjectInputStream(this.sql_socket.getInputStream());
            this.out.writeObject(type);

            HashMap<String, ArrayList<String[]>> temp = (HashMap<String, ArrayList<String[]>>) this.in.readObject();
            main_fragment.all_questions.remove(tag);
            main_fragment.all_questions.put(tag, temp.get(tag));
            switch (tag){
                case "IT":
                    main_fragment.it_swi = true;
                    break;
                case "Life":
                    main_fragment.life_swi = true;
                    break;
                case "Study":
                    main_fragment.study_swi = true;
                    break;

            }

            this.out.close();
            this.in.close();
            this.sql_socket.close();

        } catch (Exception e) {

        }

    }

}
