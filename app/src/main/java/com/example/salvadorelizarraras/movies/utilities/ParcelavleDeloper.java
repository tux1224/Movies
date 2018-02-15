package com.example.salvadorelizarraras.movies.utilities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salvador Elizarraras on 09/02/2018.
 */
public class ParcelavleDeloper implements Parcelable {
    private String name;
    private int yearsOfExperience;
    private List<Skill> skillSet;
    private float favoriteFloat;

    public ParcelavleDeloper(Parcel in) {
        this.name = in.readString();
        this.yearsOfExperience = in.readInt();
        this.skillSet = new ArrayList<Skill>();
        in.readTypedList(skillSet, Skill.CREATOR);
        this.favoriteFloat = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(yearsOfExperience);
        dest.writeTypedList(skillSet);
        dest.writeFloat(favoriteFloat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    static final Parcelable.Creator<ParcelavleDeloper> CREATOR
            = new Parcelable.Creator<ParcelavleDeloper>() {

        public ParcelavleDeloper createFromParcel(Parcel in) {
            return new ParcelavleDeloper(in);
        }

        public ParcelavleDeloper[] newArray(int size) {
            return new ParcelavleDeloper[size];
        }
    };

    static class Skill implements Parcelable {
        String name;
        boolean programmingRelated;

        Skill(Parcel in) {
            this.name = in.readString();
            this.programmingRelated = (in.readInt() == 1);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeInt(programmingRelated ? 1 : 0);
        }

        static final Parcelable.Creator<Skill> CREATOR
                = new Parcelable.Creator<Skill>() {

            public Skill createFromParcel(Parcel in) {
                return new Skill(in);
            }

            public Skill[] newArray(int size) {
                return new Skill[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }
    }
}