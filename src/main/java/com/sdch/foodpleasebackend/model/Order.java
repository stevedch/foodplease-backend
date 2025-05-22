package com.sdch.foodpleasebackend.model;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("order")
public class Order {

  @Id private Integer id;
  private LocalDateTime orderDate;
  private String status;
  private Double totalAmount;
  private Integer quantity;
  private Integer userId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return Objects.equals(id, order.id)
        && Objects.equals(orderDate, order.orderDate)
        && Objects.equals(status, order.status)
        && Objects.equals(totalAmount, order.totalAmount)
        && Objects.equals(quantity, order.quantity)
        && Objects.equals(userId, order.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orderDate, status, totalAmount, quantity, userId);
  }

  @Override
  public String toString() {
    return "Order{"
        + "id="
        + id
        + ", orderDate="
        + orderDate
        + ", status='"
        + status
        + '\''
        + ", totalAmount="
        + totalAmount
        + ", quantity="
        + quantity
        + ", userId="
        + userId
        + '}';
  }
}
