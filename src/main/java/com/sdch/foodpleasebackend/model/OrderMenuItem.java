package com.sdch.foodpleasebackend.model;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("order_menu_items")
public class OrderMenuItem {

  @Id private Integer id;
  private Integer orderId;
  private Integer menuItemId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public Integer getMenuItemId() {
    return menuItemId;
  }

  public void setMenuItemId(Integer menuItemId) {
    this.menuItemId = menuItemId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    OrderMenuItem that = (OrderMenuItem) o;
    return Objects.equals(id, that.id)
        && Objects.equals(orderId, that.orderId)
        && Objects.equals(menuItemId, that.menuItemId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orderId, menuItemId);
  }

  @Override
  public String toString() {
    return "OrderMenuItem{"
        + "id="
        + id
        + ", orderId="
        + orderId
        + ", menuItemId="
        + menuItemId
        + '}';
  }
}
