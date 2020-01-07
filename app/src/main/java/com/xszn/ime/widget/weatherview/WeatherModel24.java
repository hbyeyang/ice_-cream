package com.xszn.ime.widget.weatherview;


public class WeatherModel24 {

    private int nightTemp; //temperature of night
    private String nightWeather; // weather of night
    private String date; //date
    private String week; //week
    private boolean isToday; //is today
    private int nightPic; // image res id if night
    private String windOrientation; //orientation of wind
    private String windLevel; //level of wind
    private AirLevel airLevel; // level of air quality
    private String time;

    public int getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(int nightTemp) {
        this.nightTemp = nightTemp;
    }

    public String getNightWeather() {
        return nightWeather;
    }

    public void setNightWeather(String nightWeather) {
        this.nightWeather = nightWeather;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public int getNightPic() {
        return nightPic;
    }

    public void setNightPic(int nightPic) {
        this.nightPic = nightPic;
    }

    public String getWindOrientation() {
        return windOrientation;
    }

    public void setWindOrientation(String windOrientation) {
        this.windOrientation = windOrientation;
    }

    public String getWindLevel() {
        return windLevel;
    }

    public void setWindLevel(String windLevel) {
        this.windLevel = windLevel;
    }

    public AirLevel getAirLevel() {
        return airLevel;
    }

    public void setAirLevel(AirLevel airLevel) {
        this.airLevel = airLevel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private WeatherModel24(WeatherModel24Builder weatherModel24Builder) {
        this.nightTemp = weatherModel24Builder.nightTemp;
        this.nightWeather = weatherModel24Builder.nightWeather;
        this.date = weatherModel24Builder.date;
        this.week = weatherModel24Builder.week;
        this.isToday = weatherModel24Builder.isToday;
        this.nightPic = weatherModel24Builder.nightPic;
        this.windOrientation = weatherModel24Builder.windOrientation;
        this.windLevel = weatherModel24Builder.windLevel;
        this.airLevel = weatherModel24Builder.airLevel;
        this.time = weatherModel24Builder.time;
    }

    public static class WeatherModel24Builder {
        private int nightTemp; //temperature of night
        private String nightWeather; // weather of night
        private String date; //date
        private String week; //week
        private boolean isToday; //is today
        private int nightPic; // image res id if night
        private String windOrientation; //orientation of wind
        private String windLevel; //level of wind
        private AirLevel airLevel; // level of air quality
        private String time;

        public WeatherModel24Builder nightTemp(int nightTemp) {
            this.nightTemp = nightTemp;
            return this;
        }

        public WeatherModel24Builder nightWeather(String nightWeather) {
            this.nightWeather = nightWeather;
            return this;
        }

        public WeatherModel24Builder date(String date) {
            this.date = date;
            return this;
        }

        public WeatherModel24Builder week(String week) {
            this.week = week;
            return this;
        }

        public WeatherModel24Builder isToday(boolean isToday) {
            this.isToday = isToday;
            return this;
        }

        public WeatherModel24Builder nightPic(int nightPic) {
            this.nightPic = nightPic;
            return this;
        }

        public WeatherModel24Builder windOrientation(String windOrientation) {
            this.windOrientation = windOrientation;
            return this;
        }

        public WeatherModel24Builder windLevel(String windLevel) {
            this.windLevel = windLevel;
            return this;
        }

        public WeatherModel24Builder airLevel(AirLevel airLevel) {
            this.airLevel = airLevel;
            return this;
        }

        public WeatherModel24Builder time(String time) {
            this.time = time;
            return this;
        }

        public WeatherModel24 build() {
            return new WeatherModel24(this);
        }
    }
}


