package com.khemissi.houssemeddin.apptver2;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lenovo on 20/11/2017.
 */

public class Emission {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String nom;


    //private String nom ;
  //  @SerializedName("image")
  //  private Imge image ;

    @SerializedName("show")
    private Chaine.Show show;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



    public Chaine.Show getShow() {
        return show;
    }

    public void setShow(Chaine.Show show) {
        this.show = show;
    }

    public class Show {

        @SerializedName("type")
        private String type;

        @SerializedName("rating")
        private String rating;

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


        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @SerializedName("network")
        private Chaine.Show.Netwrk netwrk;


        public Chaine.Show.Netwrk getNetwrk() {
            return netwrk;
        }

        public void setNetwrk(Chaine.Show.Netwrk netwrk) {
            this.netwrk = netwrk;
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






}
