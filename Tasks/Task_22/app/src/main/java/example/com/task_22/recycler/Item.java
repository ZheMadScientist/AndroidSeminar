package example.com.task_22.recycler;

/**
 * Created by makar on 3/15/18.
 */

public class Item {

    public String name;

    public String description;

    public String number;

    public Item(){}

    public Item(String name, String description, String number){
        this.name = name;
        this.description = description;
        this.number = number;
    }

    public Item(Item another){
        this.name = another.name;
        this.description = another.description;
        this.number = another.number;
    }

    @Override
    public boolean equals(Object obj) {
        Item another = (Item)obj;
        return another.name.equals(this.name) &&
                another.description.equals(this.description) &&
                another.number == this.number;

    }

}
