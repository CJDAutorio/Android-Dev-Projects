package edu.uncc.weather;

public class Forecast {
    String temp, maxTemp, minTemp, humidity, date, cloudiness;

    public Forecast(String temp, String maxTemp, String minTemp, String humidity, String date, String cloudiness) {
        this.temp = temp;
        this.maxTemp = maxTemp;
        this.maxTemp = minTemp;
        this.humidity = humidity;
        this.date = date;
        this.cloudiness = cloudiness;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(String cloudiness) {
        this.cloudiness = cloudiness;
    }

}
