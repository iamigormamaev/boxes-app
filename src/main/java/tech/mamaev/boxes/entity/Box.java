package tech.mamaev.boxes.entity;

import tech.mamaev.boxes.model.generated.Storage;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Box {

    @Id
    private Integer id;

    @OneToMany(mappedBy = "containedIn", cascade = CascadeType.ALL)
    private List<Box> boxes;

    @OneToMany(mappedBy = "containedIn", cascade = CascadeType.ALL)
    private List<Item> items;

    @Column
    private Integer containedIn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getContainedIn() {
        return containedIn;
    }

    public void setContainedIn(Integer containedIn) {
        this.containedIn = containedIn;
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + id +
                ", containedIn=" + containedIn +
                '}';
    }

    public static Box from(Storage.Box sBox, Integer containedIn) {
        var box = new Box();
        box.id = sBox.getId();
        box.containedIn = containedIn;
        box.items = sBox.getItem().stream().map(i -> Item.from(i, box.id)).collect(Collectors.toList());
        box.boxes = sBox.getBox().stream().map(b -> Box.from(b, box.id)).collect(Collectors.toList());
        return box;
    }
}
