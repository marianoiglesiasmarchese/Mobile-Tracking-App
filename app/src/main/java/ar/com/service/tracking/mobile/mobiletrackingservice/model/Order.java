package ar.com.service.tracking.mobile.mobiletrackingservice.model;

import com.gustavofao.jsonapi.Annotations.Excluded;
import com.gustavofao.jsonapi.Annotations.Type;
import com.gustavofao.jsonapi.Models.Resource;

import java.util.Date;
import java.util.List;

import ar.com.service.tracking.mobile.mobiletrackingservice.model.enums.StatusEnum;

/**
 * Created by miglesias on 08/07/17.
 */
@Type("orders")
public class Order extends Resource {

    @Excluded
    private String direccion = "";
    @Excluded
    private String destinatario = "";
    @Excluded
    private String producto = "";
    @Excluded
    private Float valor ;


    private Date start_date;
    private Date end_date;
    private String address;
    private String status;
    private String customer_full_name;

    private Business business;
    private Position position;
    private Delivery delivery;

    private List<OrderProduct> ordered_products;

    public Order(){}

    public Order(String dir, String dest, String prod, Float val){
        direccion = dir;
        destinatario = dest;
        producto = prod;
        valor = val;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StatusEnum getStatusAsEnum(){
        switch (this.getStatus()) {
            case "sended":
                return StatusEnum.Enviado;
            case "suspended":
                return StatusEnum.Suspendido;
            case "finalized":
                return StatusEnum.Finalizado;
            case "canceled":
                return StatusEnum.Cancelado;
            default:
                return null;
        }
    }

    public List<OrderProduct> getOrdered_products() {
        return ordered_products;
    }

    public void setOrdered_products(List<OrderProduct> ordered_products) {
        this.ordered_products = ordered_products;
    }

    public String printOrdered_products(){
        String ordered_products = "";
        for ( OrderProduct order_product : this.getOrdered_products() ) {
            ordered_products += order_product.getProduct().getName() + ", ";
        }
        return ordered_products.subSequence(0, ordered_products.length()-2).toString();
    }

    @Override
    public String toString(){
        return direccion + "\n" + destinatario + "\n" + producto + "         " + valor;
    }

    @Override
    public boolean equals(Object o) {

        Boolean result = true;

        if (o.getClass() != this.getClass()) {
            result = false;
        }

        if (((Order)o).getAddress().compareTo(this.getAddress()) != 0) {
            result = false;
        }

        return result;
    }

    public String getCustomer_full_name() {
        return customer_full_name;
    }

    public void setCustomer_full_name(String customer_full_name) {
        this.customer_full_name = customer_full_name;
    }

//    // Constructor to convert JSON object into a Java class instance
//    public User(JSONObject object){
//        try {
//            this.name = object.getString("name");
//            this.hometown = object.getString("hometown");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Factory method to convert an array of JSON objects into a list of objects
//    // User.fromJson(jsonArray);
//    public static ArrayList<User> fromJson(JSONArray jsonObjects) {
//        ArrayList<User> users = new ArrayList<User>();
//        for (int i = 0; i < jsonObjects.length(); i++) {
//            try {
//                users.add(new User(jsonObjects.getJSONObject(i)));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return users;
//    }


}
