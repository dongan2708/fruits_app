package com.android.appfruit.entity;

public class CartItem {
    private int orderId;
    private int productId;
    private String productName;
    private String thumbnailProduct;
    private double unitPrice;
    private int quantity;

    public CartItem() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getThumbnailProduct() {
        return thumbnailProduct;
    }

    public void setThumbnailProduct(String thumbnailProduct) {
        this.thumbnailProduct = thumbnailProduct;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
