package renovator.pojo;

/**
 * Created by darlingtld on 2015/5/16.
 */
public enum Type {
    SHOE("鞋类"),
    BAG("包类"),
    COAT("皮衣"),
    LEATHER("皮具");

    private String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
