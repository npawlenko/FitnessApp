package com.np.fitnessapp.database.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.np.fitnessapp.database.exception.InvalidGenderException;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    public long userId;
    public String name;
    public Sex sex;
    public float weight;
    public int height;
    public int age;


    public User() {
    }

    @Ignore
    public User(String name, Sex sex, int age, float weight, int height) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }


    @TypeConverter
    public Sex fromStringToGender(String value) throws InvalidGenderException {
        Sex sex = null;
        switch(value) {
            case "male":
                sex = Sex.MALE;
                break;
            case "female":
                sex = Sex.FEMALE;
                break;
        }

        if(sex == null) throw new InvalidGenderException();
        return sex;
    }
}
