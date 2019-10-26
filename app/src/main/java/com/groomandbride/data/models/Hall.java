package com.groomandbride.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Hall implements Parcelable {

    /**
     * result : true
     * message : Halls Loaded Successfully
     * data : [{"formatedDate":"8/31/2019","hallsAverageRating":3.5,"hallsRatingCounter":1,"hallImage":["https://groomandbridetemp.s3.us-east-2.amazonaws.com/1567259839812"],"_id":"5d6a7cc0d177620004e5ed49","hallName":"test","hallAdress":"cairo","hallCategory":{"_id":"5d1214f4de675f000488d442","name":"Individual"},"hallDescription":"desc","hallPrice":12,"hallLocationLong":"12","hallLocationLat":"12","hallSpecialOffers":"12","hallPhoneNumber":"12","date":"2019-08-31T13:57:20.362Z","__v":0},{"formatedDate":null,"hallsAverageRating":3.5,"hallsRatingCounter":2,"hallImage":["https://groomandbride.s3.amazonaws.com/1563746961123"],"_id":"5d34e292a600980004b985db","hallName":"Four Seasons Hotel 4","hallAdress":"1089 CORNICHE EL NIL, Qasr El Nil, Cairo Governorate ","hallCategory":{"_id":"5d0cbfc9a758321414bf9875","name":"Open area","__v":0},"hallDescription":"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo.massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget.","hallPrice":180000,"hallLocationLong":"31.207320","hallLocationLat":"30.022765","hallSpecialOffers":"Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget.massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget.","hallPhoneNumber":"01158788066","__v":0},{"formatedDate":"8/29/2019","hallsAverageRating":3,"hallsRatingCounter":1,"hallImage":["https://groomandbridetemp.s3.us-east-2.amazonaws.com/1567079761052"],"_id":"5d67bd518861bb00042d8ad9","hallName":"Jezira CLub","hallAdress":"sdf","hallCategory":{"_id":"5d0cbfc9a758321414bf9873","name":"Yacht","__v":0},"hallDescription":"sdfsd","hallPrice":2000,"hallLocationLong":"1455","hallLocationLat":"424242","hallSpecialOffers":"fghghfgh","hallPhoneNumber":"2000","date":"2019-08-29T11:56:01.530Z","__v":0},{"formatedDate":null,"hallsAverageRating":2,"hallsRatingCounter":1,"hallImage":["https://groomandbride.s3.us-east-2.amazonaws.com/1563747000586"],"_id":"5d34e2baa600980004b985dc","hallName":"Four Seasons Hotel 4","hallAdress":"1089 CORNICHE EL NIL, Qasr El Nil, Cairo Governorate ","hallCategory":{"_id":"5d0cbfc9a758321414bf9875","name":"Open area","__v":0},"hallDescription":"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo.massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget.","hallPrice":180000,"hallLocationLong":"31.207320","hallLocationLat":"30.022765","hallSpecialOffers":"Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget.massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget.","hallPhoneNumber":"01158788066","__v":0},{"formatedDate":"8/30/2019","hallsAverageRating":2,"hallsRatingCounter":1,"hallImage":["https://groomandbridetemp.s3.amazonaws.com/1567128276743"],"_id":"5d687ad5215d4200047b37aa","hallName":"Jezira CLubb","hallAdress":"sdf","hallCategory":{"_id":"5d0cbfc9a758321414bf9874","name":"Villa","__v":0},"hallDescription":"sdfsd","hallPrice":2000,"hallLocationLong":"1455","hallLocationLat":"424242","hallSpecialOffers":"fghghfgh","hallPhoneNumber":"2000","date":"2019-08-30T01:24:37.498Z","__v":0},{"formatedDate":"9/7/2019","hallsAverageRating":1,"hallsRatingCounter":1,"hallImage":["[[\"1567871418071cibi-chakravarthi-kCsRDE-lbBo-unsplash.jpg\"],[\"1567871417985cash-vickers-xdzxgVYGJd8-unsplash.jpg\"]]"],"_id":"5d73d1c5e7a5470004785662","hallName":"Ramy's Hall","hallAdress":"23 4ar3 ahmed sayd ahmed-mdent nasr-cairo-egypt","hallCategory":{"_id":"5d0cbfc9a758321414bf9871","name":"Hotel","__v":0},"hallDescription":"Why do we use it? It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).","hallPrice":2600,"hallLocationLong":"43443","hallLocationLat":"434344","hallSpecialOffers":"Why do we use it? It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).","hallPhoneNumber":"01158788066","date":"2019-09-07T15:50:29.789Z","__v":0},{"formatedDate":null,"hallsAverageRating":0,"hallsRatingCounter":0,"hallImage":[],"_id":"5d24ea4be40cbf000422ea21","hallName":"Four Seasons Hotel 2","hallAdress":"1089 CORNICHE EL NIL, Qasr El Nil, Cairo Governorate ","hallCategory":{"_id":"5d0cbfc9a758321414bf9875","name":"Open area","__v":0},"hallDescription":"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo.massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget.","hallPrice":180000,"hallLocationLong":"31.207320","hallLocationLat":"30.022765","hallSpecialOffers":"Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget.massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget.","hallPhoneNumber":"01158788066","__v":0}]
     */

    private boolean result;
    private String message;
    private List<DataBean> data;

    protected Hall(Parcel in) {
        result = in.readByte() != 0;
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (result ? 1 : 0));
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Hall> CREATOR = new Creator<Hall>() {
        @Override
        public Hall createFromParcel(Parcel in) {
            return new Hall(in);
        }

        @Override
        public Hall[] newArray(int size) {
            return new Hall[size];
        }
    };

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable{
        /**
         * formatedDate : 8/31/2019
         * hallsAverageRating : 3.5
         * hallsRatingCounter : 1
         * hallImage : ["https://groomandbridetemp.s3.us-east-2.amazonaws.com/1567259839812"]
         * _id : 5d6a7cc0d177620004e5ed49
         * hallName : test
         * hallAdress : cairo
         * hallCategory : {"_id":"5d1214f4de675f000488d442","name":"Individual"}
         * hallDescription : desc
         * hallPrice : 12
         * hallLocationLong : 12
         * hallLocationLat : 12
         * hallSpecialOffers : 12
         * hallPhoneNumber : 12
         * date : 2019-08-31T13:57:20.362Z
         * __v : 0
         */

        private String formatedDate;
        private double hallsAverageRating;
        private int hallsRatingCounter;
        private String _id;
        private String hallName;
        private String hallAdress;
        private HallCategoryBean hallCategory;
        private String hallDescription;
        private int hallPrice;
        private String hallLocationLong;
        private String hallLocationLat;
        private String hallSpecialOffers;
        private String hallPhoneNumber;
        private String date;
        private int __v;
        private List<String> hallImage;

        protected DataBean(Parcel in) {
            formatedDate = in.readString();
            hallsAverageRating = in.readDouble();
            hallsRatingCounter = in.readInt();
            _id = in.readString();
            hallName = in.readString();
            hallAdress = in.readString();
            hallDescription = in.readString();
            hallPrice = in.readInt();
            hallLocationLong = in.readString();
            hallLocationLat = in.readString();
            hallSpecialOffers = in.readString();
            hallPhoneNumber = in.readString();
            date = in.readString();
            __v = in.readInt();
            hallImage = in.createStringArrayList();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getFormatedDate() {
            return formatedDate;
        }

        public void setFormatedDate(String formatedDate) {
            this.formatedDate = formatedDate;
        }

        public double getHallsAverageRating() {
            return hallsAverageRating;
        }

        public void setHallsAverageRating(double hallsAverageRating) {
            this.hallsAverageRating = hallsAverageRating;
        }

        public int getHallsRatingCounter() {
            return hallsRatingCounter;
        }

        public void setHallsRatingCounter(int hallsRatingCounter) {
            this.hallsRatingCounter = hallsRatingCounter;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getHallName() {
            return hallName;
        }

        public void setHallName(String hallName) {
            this.hallName = hallName;
        }

        public String getHallAdress() {
            return hallAdress;
        }

        public void setHallAdress(String hallAdress) {
            this.hallAdress = hallAdress;
        }

        public HallCategoryBean getHallCategory() {
            return hallCategory;
        }

        public void setHallCategory(HallCategoryBean hallCategory) {
            this.hallCategory = hallCategory;
        }

        public String getHallDescription() {
            return hallDescription;
        }

        public void setHallDescription(String hallDescription) {
            this.hallDescription = hallDescription;
        }

        public int getHallPrice() {
            return hallPrice;
        }

        public void setHallPrice(int hallPrice) {
            this.hallPrice = hallPrice;
        }

        public String getHallLocationLong() {
            return hallLocationLong;
        }

        public void setHallLocationLong(String hallLocationLong) {
            this.hallLocationLong = hallLocationLong;
        }

        public String getHallLocationLat() {
            return hallLocationLat;
        }

        public void setHallLocationLat(String hallLocationLat) {
            this.hallLocationLat = hallLocationLat;
        }

        public String getHallSpecialOffers() {
            return hallSpecialOffers;
        }

        public void setHallSpecialOffers(String hallSpecialOffers) {
            this.hallSpecialOffers = hallSpecialOffers;
        }

        public String getHallPhoneNumber() {
            return hallPhoneNumber;
        }

        public void setHallPhoneNumber(String hallPhoneNumber) {
            this.hallPhoneNumber = hallPhoneNumber;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public List<String> getHallImage() {
            return hallImage;
        }

        public void setHallImage(List<String> hallImage) {
            this.hallImage = hallImage;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(formatedDate);
            parcel.writeDouble(hallsAverageRating);
            parcel.writeInt(hallsRatingCounter);
            parcel.writeString(_id);
            parcel.writeString(hallName);
            parcel.writeString(hallAdress);
            parcel.writeString(hallDescription);
            parcel.writeInt(hallPrice);
            parcel.writeString(hallLocationLong);
            parcel.writeString(hallLocationLat);
            parcel.writeString(hallSpecialOffers);
            parcel.writeString(hallPhoneNumber);
            parcel.writeString(date);
            parcel.writeInt(__v);
            parcel.writeStringList(hallImage);
        }

        public static class HallCategoryBean implements Parcelable{
            /**
             * _id : 5d1214f4de675f000488d442
             * name : Individual
             */

            private String _id;
            private String name;

            protected HallCategoryBean(Parcel in) {
                _id = in.readString();
                name = in.readString();
            }

            public static final Creator<HallCategoryBean> CREATOR = new Creator<HallCategoryBean>() {
                @Override
                public HallCategoryBean createFromParcel(Parcel in) {
                    return new HallCategoryBean(in);
                }

                @Override
                public HallCategoryBean[] newArray(int size) {
                    return new HallCategoryBean[size];
                }
            };

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(_id);
                parcel.writeString(name);
            }
        }
    }
}
