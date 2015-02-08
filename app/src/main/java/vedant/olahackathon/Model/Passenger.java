package vedant.olahackathon.Model;

/**
 * Created by vedant on 2/7/15.
 */
public class Passenger {
//    private Integer id;
    private String name;
    private String mobile;
    private Position geo;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Position getGeo() {
        return geo;
    }

    public void setGeo(Position geo) {
        this.geo = geo;
    }
}
