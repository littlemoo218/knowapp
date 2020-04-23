package north.helper;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;

import java.util.HashMap;
import java.util.Random;

public class map_activity extends AppCompatActivity {

    private TextView my_pos;
    private TextView other_pos;

    private MapView mMapView = null;
    private AMap aMap = null;

    private Poi mylocation = new Poi("我的位置", new LatLng(39.178821, 117.226600), "");

    private Poi tjs = new Poi("天津站", new LatLng(39.142488, 117.216853), "");
    private Poi tjs_w = new Poi("天津西站", new LatLng(39.164265, 117.169980), "");
    private Poi tjs_s = new Poi("天津南站", new LatLng(39.062757, 117.067281), "");
    private Poi tjs_n = new Poi("天津北站", new LatLng(39.173165, 117.216107), "");

    private HashMap<Integer, Poi> places = new HashMap<>();

    private Poi end;
    private INaviInfoCallback naviInfoCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        places.put(0, tjs);
        places.put(1, tjs_w);
        places.put(2, tjs_s);
        places.put(3, tjs_n);

        this.my_pos = findViewById(R.id.my_pos);
        this.other_pos = findViewById(R.id.other_pos);

        Random r = new Random();
        int c = r.nextInt(4);
        this.end = places.get(c);

        this.my_pos.setText(String.format("%s%s%s%s%s", "您的位置：", String.valueOf(mylocation.getCoordinate().latitude),
                " N  ", String.valueOf(mylocation.getCoordinate().longitude), " S"));

        this.other_pos.setText(String.format("%s%s%s%s%s", "对方位置：", String.valueOf(end.getCoordinate().latitude),
                " N  ", String.valueOf(end.getCoordinate().longitude), " S"));




        mMapView = findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();

        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));


        LatLng latLng = new LatLng(end.getCoordinate().latitude, end.getCoordinate().longitude);
        Marker maker = aMap.addMarker(new MarkerOptions().position(latLng));


        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                AmapNaviPage.getInstance().showRouteActivity(map_activity.this,
                        new AmapNaviParams(map_activity.this.mylocation, null, map_activity.this.end,
                                AmapNaviType.DRIVER), naviInfoCallback);
                return false;
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
