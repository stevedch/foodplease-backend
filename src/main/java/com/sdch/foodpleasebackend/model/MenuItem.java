package com.sdch.foodpleasebackend.model;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("menu_item")
public class MenuItem {

  @Id private Integer id;
  private String name;
  private String description;
  private Double price;
  private String imageUrl;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    MenuItem menuItem = (MenuItem) o;
    return Objects.equals(id, menuItem.id)
        && Objects.equals(name, menuItem.name)
        && Objects.equals(description, menuItem.description)
        && Objects.equals(price, menuItem.price)
        && Objects.equals(imageUrl, menuItem.imageUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, price, imageUrl);
  }

  @Override
  public String toString() {
    return "MenuItem{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", price="
        + price
        + ", imageUrl='"
        + imageUrl
        + '\''
        + '}';
  }
}
