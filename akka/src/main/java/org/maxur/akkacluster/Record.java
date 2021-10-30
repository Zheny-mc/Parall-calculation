package org.maxur.akkacluster;

public class Record {
    private Integer id;
    private String time;
    private Boolean view; //false - buy, true - sall
    private String description;

    public static Record create(Boolean view /* Integer id, String time, Boolean view, String description */) {
        return new Record(0, "00:00", view, "cat");
    }

    public Record() {}

    public Record(Integer id, String time, Boolean view, String description) {
        this.id = id;
        this.time = time;
        this.view = view;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getView() {
        return view;
    }
    public void setView(Boolean view) {
        this.view = view;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", view=" + view +
                ", description='" + description + '\'' +
                '}';
    }
}