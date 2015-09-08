package renovator.pojo;

/**
 * Created by darlingtld on 2015/5/16.
 */
public enum Category {

    SHOE("鞋类"),
    BAG("包类"),
    COAT("皮衣"),
    LEATHER("皮具");


    private String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
