package ar.com.service.tracking.mobile.mobiletrackingservice.backgroundservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;

import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import com.google.maps.model.TrafficModel;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ar.com.service.tracking.mobile.mobiletrackingservice.model.Business;
import ar.com.service.tracking.mobile.mobiletrackingservice.model.Order;
import ar.com.service.tracking.mobile.mobiletrackingservice.model.adapter.OrderAdapter;
import ar.com.service.tracking.mobile.mobiletrackingservice.utils.MessageHelper;

/**
 * Created by miglesias on 28/09/17.
 */

public class GoogleDirectionsAPI {

    private static final String TAG = "GoogleDirectionsAPI";

    private Context context;
    List<LatLng> waypoints = new LinkedList<LatLng>();
    GoogleDirectionsAPIObserver observer;

    public GoogleDirectionsAPI(Context appContext, GoogleDirectionsAPIObserver googleDirectionsAPIObserver){

        this.context = appContext;
        this.observer = googleDirectionsAPIObserver;

    }

    public void route(Business buesiness, OrderAdapter orderAdapter){

        LatLng origin;
        LatLng destination;
        LatLng[] ordersDestination;

        this.addWaypoints(buesiness, orderAdapter);

        origin = waypoints.get(0);
        destination = waypoints.get(0);

        ordersDestination = new LatLng[waypoints.size()-1];
        ordersDestination = waypoints.subList(1,waypoints.size()).toArray(ordersDestination);

        GeoApiContext apiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyAxz6J7bOQGvMEPr18VQlmm5ZjSNwUBMqU")
                .build();

        DirectionsApiRequest apiRequest = DirectionsApi.newRequest(apiContext);
        apiRequest.origin(origin);
        apiRequest.waypoints(ordersDestination);
        apiRequest.destination(destination);
        apiRequest.mode(TravelMode.DRIVING); //set travelling mode
//                apiRequest.units(Unit.METRIC);
//                apiRequest.region("ar");
//                apiRequest.avoid(DirectionsApi.RouteRestriction.HIGHWAYS,
//                        DirectionsApi.RouteRestriction.TOLLS,
//                        DirectionsApi.RouteRestriction.FERRIES);
//                apiRequest.trafficModel(TrafficModel.PESSIMISTIC);
//                apiRequest.departureTime(org.joda.time.Instant.now());
//                apiRequest.optimizeWaypoints(true);

//                apiRequest.awaitIgnoreError();

        apiRequest.setCallback(new com.google.maps.PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                //   MessageHelper.toast(context, "Ruteo Exitoso", Toast.LENGTH_SHORT);
                Log.i(TAG, "Ruteo Exitoso");

                DirectionsRoute[] routes = result.routes;
                final List<com.google.android.gms.maps.model.LatLng> route = new LinkedList<com.google.android.gms.maps.model.LatLng>();

                for (int i = 0 ; i < routes[0].legs.length; i++ ) {
                    for (int k = 0; k < routes[0].legs[i].steps.length; k++) {
//                    System.out.println(routes[0].legs[0].steps[i].polyline.decodePath());
                        for (com.google.maps.model.LatLng line : routes[0].legs[i].steps[k].polyline.decodePath()) {
                            route.add(new com.google.android.gms.maps.model.LatLng(line.lat, line.lng));
                        }
                        System.out.println("STEP: " + route);
                    }
                    System.out.println("LEG: " + route);
                }
                observer.notify(route);

            }

            @Override
            public void onFailure(Throwable e) {
                //   MessageHelper.toast(context, "Conexion fallida: " + e.toString(), Toast.LENGTH_SHORT);
                Log.e(TAG, "Conexion fallida: " + e.toString());
            }
        });

    }

    private void addWaypoints(Business buesiness, OrderAdapter orderAdapter) {
        waypoints.add(new LatLng(buesiness.getPosition().getLatitude(), buesiness.getPosition().getLongitude()));
        for (Order order: orderAdapter.getOrders()) {
            waypoints.add(new LatLng(order.getPosition().getLatitude(), order.getPosition().getLongitude()));
        }
    }

}