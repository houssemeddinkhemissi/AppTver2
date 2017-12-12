package com.khemissi.houssemeddin.apptver2;

import android.net.Network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lenovo on 20/11/2017.
 */

public class Chaine {

    @SerializedName("show")
    private Show show;


    //private String nom ;
   // @SerializedName("url")
   // private String Image ;


    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public class Show {

        @SerializedName("type")
        private String type;


        @SerializedName("image")
        private Imge image ;

        public Imge getImage() {
            return image;
        }

        public void setImage(Imge image) {
            this.image = image;
        }


        public class Imge {

            @SerializedName("original")
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }


        @SerializedName("rating")
        private Rating rating;

        public Rating getRating() {
            return rating;
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @SerializedName("network")
        private Netwrk netwrk;


        public Netwrk getNetwrk() {
            return netwrk;
        }

        public void setNetwrk(Netwrk netwrk) {
            this.netwrk = netwrk;
        }

        public class Rating {

            @SerializedName("average")
            private float average;

            public float getAverage() {
                return average;
            }

            public void setAverage(float average) {
                this.average = average;
            }
        }

        public class Netwrk {

            @SerializedName("id")
            private int id;

            @SerializedName("name")
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }



  /*  public String getNom() {
        return nom;
    }

  public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }*/
}
