package com.example.trainingconstructor.DataBase.Exercise;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_table")
public class Exercise {

    @PrimaryKey (autoGenerate = true)
    Integer id;
    private String name;
    private boolean press_type;
    private boolean hands_type;
    private boolean foot_type;
    private boolean back_type;
    private boolean breast_type;
    private boolean sholders_type;
    private int img_id;


    @Ignore
    public Exercise(String name, boolean press_type, boolean hands_type, boolean foot_type, boolean back_type, boolean breast_type, boolean sholders_type, int img_id) {
        this.name = name;
        this.press_type = press_type;
        this.hands_type = hands_type;
        this.foot_type = foot_type;
        this.back_type = back_type;
        this.breast_type = breast_type;
        this.sholders_type = sholders_type;
        this.img_id = img_id;
    }


    public Exercise(int id, String name, boolean press_type, boolean hands_type, boolean foot_type, boolean back_type, boolean breast_type, boolean sholders_type, int img_id) {
        this.id = id;
        this.name = name;
        this.press_type = press_type;
        this.hands_type = hands_type;
        this.foot_type = foot_type;
        this.back_type = back_type;
        this.breast_type = breast_type;
        this.sholders_type = sholders_type;
        this.img_id = img_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public boolean isPress_type() {
        return press_type;
    }

    public boolean isHands_type() {
        return hands_type;
    }

    public boolean isFoot_type() {
        return foot_type;
    }

    public boolean isBack_type() {
        return back_type;
    }

    public boolean isBreast_type() {
        return breast_type;
    }

    public boolean isSholders_type() {
        return sholders_type;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }
}
