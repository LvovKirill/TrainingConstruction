package com.example.trainingconstructor.DataBase.Training;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "training_table")
public class Training {

    @PrimaryKey (autoGenerate = true)
        private int id;
        private String name;
        private boolean press_type;
        private boolean hands_type;
        private boolean foot_type;
        private boolean back_type;
        private boolean breast_type;
        private boolean breast_sholders;
        private String image_path;
        private int img_id;

    @Ignore
    public Training(String name, boolean press_type, boolean hands_type, boolean foot_type, boolean back_type, boolean breast_type, boolean breast_sholders, String image_path, int img_id) {
        this.name = name;
        this.press_type = press_type;
        this.hands_type = hands_type;
        this.foot_type = foot_type;
        this.back_type = back_type;
        this.breast_type = breast_type;
        this.breast_sholders = breast_sholders;
        this.image_path = image_path;
        this.img_id = img_id;
    }


    public Training(int id, String name, boolean press_type, boolean hands_type, boolean foot_type, boolean back_type, boolean breast_type, boolean breast_sholders, String image_path, int img_id) {
        this.id = id;
        this.name = name;
        this.press_type = press_type;
        this.hands_type = hands_type;
        this.foot_type = foot_type;
        this.back_type = back_type;
        this.breast_type = breast_type;
        this.breast_sholders = breast_sholders;
        this.image_path = image_path;
        this.img_id = img_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isPress_type() {
        return press_type;
    }

    public void setPress_type(boolean press_type) {
        this.press_type = press_type;
    }

    public boolean isHands_type() {
        return hands_type;
    }

    public void setHands_type(boolean hands_type) {
        this.hands_type = hands_type;
    }

    public boolean isFoot_type() {
        return foot_type;
    }

    public void setFoot_type(boolean foot_type) {
        this.foot_type = foot_type;
    }

    public boolean isBack_type() {
        return back_type;
    }

    public void setBack_type(boolean back_type) {
        this.back_type = back_type;
    }

    public boolean isBreast_type() {
        return breast_type;
    }

    public void setBreast_type(boolean breast_type) {
        this.breast_type = breast_type;
    }

    public boolean isSholders_type() {
        return breast_sholders;
    }

    public int getId() {
        return id;
    }

    public void setBreast_sholders(boolean breast_sholders) {
        this.breast_sholders = breast_sholders;
    }

    public boolean isBreast_sholders() {
        return breast_sholders;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }
}
