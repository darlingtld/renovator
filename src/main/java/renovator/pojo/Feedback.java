package renovator.pojo;

import javax.persistence.*;

/**
 * Created by darlingtld on 2015/5/16.
 */
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    private int id;
    @Column(name = "content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
