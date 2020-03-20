package tech.mamaev.boxes.entity;

import tech.mamaev.boxes.model.generated.Storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {

    @Id
    private Integer id;

    @Column
    private String color;

    @Column(name = "contained_in")
    private Integer containedIn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getContainedIn() {
        return containedIn;
    }

    public void setContainedIn(Integer containedIn) {
        this.containedIn = containedIn;
    }

    public static Item from(Storage.Item sItem, Integer containedIn) {
        var item = new Item();
        item.id = sItem.getId();
        item.color = sItem.getColor();
        item.containedIn = containedIn;
        return item;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", containedIn=" + containedIn +
                '}';
    }
}
